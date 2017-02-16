package com.corsojava.dao.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;

import com.corsojava.dao.SessionManager;

public class JdbcSessionManager implements SessionManager {
	
	

	private Connection connection;
	private String connectionUrl;
	private String connectionUser;
	private String connectionPassword;

	public JdbcSessionManager(String connectionUrl, String connectionUser, String connectionPassword) {
		super();
		this.connectionUrl = connectionUrl;
		this.connectionUser = connectionUser;
		this.connectionPassword = connectionPassword;
	}

	public void startConnection() {
		try {
			if (this.connection == null) {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				this.connection = DriverManager.getConnection(connectionUrl, connectionUser, connectionPassword);

			} else {
				if (!this.connection.isValid(1000)) {
					DbUtils.closeQuietly(connection);
					this.connection = DriverManager.getConnection(connectionUrl, connectionUser, connectionPassword);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error starting connection", e);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void releaseConnection() {
		if (this.connection != null) {
			DbUtils.closeQuietly(this.connection);
		}
		this.connection = null;
	}

	public void startTransaction() {
		try {
			this.connection.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error starting transaction", e);
		}

	}

	public void commitTransaction() {
		try {
			this.connection.commit();
			this.connection.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error commiting transaction", e);
		}

	}

	public void rollbackTransaction() {
		try {
			this.connection.rollback();
			this.connection.setAutoCommit(true);
		} catch (SQLException e) {
			throw new RuntimeException("Error rollbacking transaction", e);
		}

	}

	public Connection getConnection() {
		return connection;
	}

}
