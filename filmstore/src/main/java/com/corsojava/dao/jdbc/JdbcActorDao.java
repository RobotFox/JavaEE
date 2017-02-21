package com.corsojava.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.corsojava.dao.ActorDao;
import com.corsojava.dao.SessionManager;
import com.corsojava.model.Actor;
import com.corsojava.model.Film;

@Component
public class JdbcActorDao implements ActorDao {
	private JdbcSessionManager sessionManager;
	private PreparedStatement prepareStatement;
	private ResultSet rs;
	private int result;

	@Autowired
	public JdbcActorDao(SessionManager sessionManager) {
		super();
		this.sessionManager = (JdbcSessionManager) sessionManager;
		prepareStatement = null;
		rs = null;
		result = 0;
	}

	public Actor getActor(int id) {
		Actor result = null;

		try {
			prepareStatement = getConnection().prepareStatement("SELECT * FROM actor WHERE actor_id = ?");
			prepareStatement.setInt(1, id);
			rs = prepareStatement.executeQuery();
			if (rs.next()) {
				result = new Actor().withId(rs.getInt("actor_id")).withFirstName(rs.getString("first_name"))
						.withLastName(rs.getString("last_name"));
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

	public List<Actor> getAllActors() {
		List<Actor> listActors = new LinkedList<Actor>();

		try {
			prepareStatement = getConnection().prepareStatement("SELECT * FROM actor");
			rs = prepareStatement.executeQuery();
			while (rs.next()) {
				listActors.add(new Actor().withId(rs.getInt("actor_id")).withFirstName(rs.getString("first_name"))
						.withLastName(rs.getString("last_name")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(prepareStatement);
		}

		return listActors;
	}

	public int addActor(Actor actor) {
		result = 0;
		try {
			prepareStatement = getConnection()
					.prepareStatement("INSERT INTO actor (first_name, last_name) VALUES (?,?)");
			prepareStatement.setString(2, actor.getFirstName());
			prepareStatement.setString(3, actor.getLastName());
			rs = prepareStatement.executeQuery();
			if (rs.next()) {
				result++;
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

	public int deleteActor(int id) {
		result = 0;
		try {
			prepareStatement = getConnection().prepareStatement("DELETE from actor where actor_id = ?");
			prepareStatement.setInt(1, id);
			rs = prepareStatement.executeQuery();
			if (rs.next()) {
				result++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public int updateActor(int id, Actor actor) {
		result = 0;
		try {
			prepareStatement = getConnection().prepareStatement("SELECT * FROM actor WHERE actor_id = ?",
					ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			prepareStatement.setInt(1, id);
			rs = prepareStatement.executeQuery();
			if (rs.next()) {
				String value = null;
				boolean updated = false;
				if ((value = actor.getFirstName()) != null) {
					rs.updateString("first_name", value);
					updated = true;
				}
				if ((value = actor.getLastName()) != null) {
					rs.updateString("last_name", value);
					updated = true;
				}
				rs.updateRow();
				if (updated) {
					result++;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public List<Actor> findActors(Actor actor) {
		List<Actor> result = new LinkedList<Actor>();
		try {
			prepareStatement = getConnection()
					.prepareStatement("SELECT * FROM actor WHERE fist_name ILIKE ? AND last_name ILIKE ?");
			String value = null;
			if ((value = actor.getFirstName()) != null) {
				prepareStatement.setString(1, value);
			} else {
				prepareStatement.setString(1, "%");
			}
			if ((value = actor.getLastName()) != null) {
				prepareStatement.setString(2, value);
			} else {
				prepareStatement.setString(2, "%");
			}
			rs = prepareStatement.executeQuery();
			if (rs.next()) {
				result.add(new Actor().withId(rs.getInt("actor_id")).withFirstName(rs.getString("first_name"))
						.withLastName(rs.getString("last_name")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	private Connection getConnection() {
		return this.sessionManager.getConnection();
	}

	public List<Actor> findFilmActors(int filmId) {
		List<Actor> result = new LinkedList<Actor>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = getConnection().prepareStatement(
					"SELECT * FROM actor a, film_actor fa WHERE  fa.actor_id = a.actor_id and fa.film_id = ?");
			pstmt.setInt(1, filmId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				result.add(new Actor().withId(rs.getInt("actor_id")).withFirstName(rs.getString("first_name"))
						.withLastName(rs.getString("last_name")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(pstmt);
		}
		return result;
	}

}
