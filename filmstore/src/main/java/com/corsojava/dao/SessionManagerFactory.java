package com.corsojava.dao;

import com.corsojava.dao.jdbc.JdbcSessionManager;

public class SessionManagerFactory {

	public static SessionManager getSessionManager(int type) {
		if (type == DaoFactory.JDBC_DAO_FACTORY_TYPE) {
			return new JdbcSessionManager("jdbc:mysql://localhost/sakila", "root", "root");
		}
		return null;
	}

}
