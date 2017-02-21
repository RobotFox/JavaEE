package com.corsojava.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.corsojava.dao.FilmDao;
import com.corsojava.dao.SessionManager;
import com.corsojava.model.Actor;
import com.corsojava.model.Film;

@Component
public class JdbcFilmDao implements FilmDao {

	private JdbcSessionManager sessionManager;
	private PreparedStatement prepareStatement;
	private ResultSet rs;
	private int result;
	private JdbcDaoFactory daoFactory;

	@Autowired
	public JdbcFilmDao(SessionManager sessionManager) {
		this.sessionManager = (JdbcSessionManager) sessionManager;
		this.daoFactory = new JdbcDaoFactory(this.sessionManager);
		prepareStatement = null;
		rs = null;
		result = 0;
	}

	public Film getFilm(int film_id) {
		Film result = null;
		try {
			prepareStatement = getConnection().prepareStatement(
					"select * from film JOIN language ON film.language_id = language.language_id where film_id = ?");
			prepareStatement.setInt(1, film_id);
			rs = prepareStatement.executeQuery();
			if (rs.next()) {
				result = new Film().withId(rs.getInt("film_id")).withTitle(rs.getString("title"))
						.withDescription(rs.getString("description")).withRelease_Year(rs.getString("release_year"))
						.withLanguage(rs.getInt("language_id"), rs.getString("name"))
						.withOriginal_Language(rs.getInt("original_language_id")).withLength(rs.getInt("length"))
						.withActors(actorsInFilm(film_id));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(prepareStatement);
		}

		return result;
	}

	public int addFilm(Film film) {
		int result = 0;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		try {
			pstmt = getConnection().prepareStatement(
					"INSERT INTO film (title, description, release_year, length,language_id) values (?, ?, ?, ?,1)",
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, film.getTitle());
			pstmt.setString(2, film.getDescription());
			pstmt.setString(3, film.getRelease_year());
			pstmt.setInt(4, film.getLength());
			result += pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				film.setFilm_id(rs.getInt(1));
				System.out.println(film.getActors() != null);
				System.out.println(film.getActors().isEmpty());
			}
			if (film.getActors() != null && !film.getActors().isEmpty()) {
				pstmt2 = getConnection().prepareStatement("INSERT INTO film_actor (film_id, actor_id) VALUES (?, ?)");
				for (Actor actor : film.getActors()) {
					if (actor.getId() == 0) {
						daoFactory.getActorDao().addActor(actor);
					}
					pstmt2.setInt(1, film.getFilm_id());
					pstmt2.setInt(2, actor.getId());
					result += pstmt2.executeUpdate();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(pstmt2);
			DbUtils.closeQuietly(pstmt);
		}
		return result;
	}

	public int updateFilm(int film_id, Film film) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int deleteFilm(int film_id) {
		// TODO Auto-generated method stub
		return 0;
	}

	private Film buildFilmFromRs(ResultSet rs) throws SQLException {
		Film result;
		result = new Film();
		result.setFilm_id(rs.getInt("film_id"));
		result.setTitle(rs.getString("title"));
		result.setDescription(rs.getString("description"));
		result.setRelease_year(rs.getString("release_year"));
		result.setLanguage_id(daoFactory.getLanguageDao().getLanguage(rs.getInt("language_id")));
		result.setOriginal_language_id(daoFactory.getLanguageDao().getLanguage(rs.getInt("original_language_id")));
		result.setLength(rs.getInt("length"));
		result.setActors(daoFactory.getActorDao().findFilmActors(result.getFilm_id()));
		return result;
	}

	public List<Film> getAllFilms() {
		List<Film> result = new LinkedList<Film>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = getConnection().prepareStatement("SELECT * FROM film");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				result.add(buildFilmFromRs(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(pstmt);
		}
		return result;
	}

	public List<Film> findFilms(Film film) {
		List<Film> filmsWithName = new LinkedList<Film>();
		try {
			prepareStatement = getConnection().prepareStatement("select * from film where title LIKE ?");
			prepareStatement.setString(1, "%" + film.getTitle() + "%");
			rs = prepareStatement.executeQuery();
			while (rs.next()) {
				filmsWithName.add(new Film().withTitle(rs.getString("title")));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return filmsWithName;
	}

	private Connection getConnection() {
		return this.sessionManager.getConnection();
	}

	public List<Actor> actorsInFilm(int film_id) {
		List<Actor> actors = new LinkedList<Actor>();
		try {
			prepareStatement = getConnection().prepareStatement(
					"select film_actor.actor_id, first_name, last_name from film_actor JOIN actor ON film_actor.actor_id = actor.actor_id where film_id = ?");
			prepareStatement.setInt(1, film_id);
			rs = prepareStatement.executeQuery();
			while (rs.next()) {
				actors.add(new Actor().withId(rs.getInt("actor_id")).withFirstName(rs.getString("first_name"))
						.withLastName(rs.getString("last_name")));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		finally {
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(prepareStatement);
		}
		return actors;
	}

}
