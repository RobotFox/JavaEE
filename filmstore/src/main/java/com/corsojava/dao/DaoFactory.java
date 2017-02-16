package com.corsojava.dao;

import com.corsojava.dao.jdbc.JdbcDaoFactory;
import com.corsojava.dao.jdbc.JdbcSessionManager;

public class DaoFactory {
	public static final int JDBC_DAO_FACTORY_TYPE = 1;

	public static DaoFactoryInterface getDaoFactory(int type) {
		switch (type) {
		case JDBC_DAO_FACTORY_TYPE:
			return new JdbcDaoFactory(
					(JdbcSessionManager) SessionManagerFactory.getSessionManager(JDBC_DAO_FACTORY_TYPE));

		default:
			return null;
		}
	}

}
