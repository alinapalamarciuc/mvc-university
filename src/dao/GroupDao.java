package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Group;

public class GroupDao extends AbstractDao {

	public GroupDao(Connection conn) {
		super(conn);
		if (conn == null) {
			System.out.println("Null connection");
		}
	}

	public List<Group> getAllGroups() throws SQLException {
		List<Group> groups = new ArrayList<>();
		PreparedStatement statement = this.getConnection().prepareStatement("SELECT * FROM grup");
		ResultSet rs = statement.executeQuery();
		while (rs.next()) {
			Group group = new Group();
			group.setId(rs.getInt("id"));
			group.setName(rs.getString("name"));
			groups.add(group);
		}
		if (statement != null) {
			statement.close();
		}
		return groups;
	}

}
