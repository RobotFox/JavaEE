package com.corsojava.dao;

public interface SessionManager {

	void startConnection();

	void releaseConnection();

	void startTransaction();

	void commitTransaction();

	void rollbackTransaction();

}
