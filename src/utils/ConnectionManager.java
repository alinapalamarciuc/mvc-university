package utils;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;

public class ConnectionManager {
	private BasicDataSource dataSource;
	private static ConnectionManager connectionManager;

	private ConnectionManager() throws ClassNotFoundException, SQLException {

		dataSource = new BasicDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl("jdbc:postgresql://127.0.0.1:5432/university");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres");
	}

	public static ConnectionManager getInstance() throws ClassNotFoundException, SQLException {
		if (connectionManager == null) {
			connectionManager = new ConnectionManager();
		}
		return connectionManager;
	}

	public Connection getConnection() throws SQLException {

		return dataSource.getConnection();
	}

	public void closConnection() throws SQLException {
		dataSource.close();
	}
}