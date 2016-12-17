package dao;

import java.sql.Connection;

public abstract class AbstractDao {

	private Connection connection;

	public AbstractDao(Connection conn) {
		this.setConnection(conn);
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

}
