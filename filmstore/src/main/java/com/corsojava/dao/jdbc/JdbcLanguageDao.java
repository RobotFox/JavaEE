package com.corsojava.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.dbutils.DbUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.corsojava.dao.LanguageDao;
import com.corsojava.dao.SessionManager;
import com.corsojava.model.Language;

@Component
public class JdbcLanguageDao implements LanguageDao {

	private JdbcSessionManager sessionManager;

	@Autowired
	public JdbcLanguageDao(SessionManager sessionManager) {
		super();
		this.sessionManager = (JdbcSessionManager) sessionManager;
	}

	public Language getLanguage(int id) {
		Language result = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = getConnection().prepareStatement("SELECT * FROM language WHERE language_id = ?");
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = new Language();
				int intValue = rs.getInt("language_id");
				result.setLanguage_id(rs.wasNull() ? null : intValue);
				result.setName(rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(pstmt);
		}
		return result;
	}

	private Connection getConnection() {
		return this.sessionManager.getConnection();
	}

	public int addLanguage(Language language) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = getConnection().prepareStatement("INSERT INTO language (name) VALUES (?)",
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, language.getName());
			result = pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				language.setLanguage_id(rs.getInt(1));
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
