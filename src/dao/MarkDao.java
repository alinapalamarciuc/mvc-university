package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.Mark;

public class MarkDao extends AbstractDao {

	public MarkDao(Connection conn) {
		super(conn);
	}

	// insert mark
	public void addMark(Mark mark) throws SQLException {
		PreparedStatement statement = this.getConnection()
				.prepareStatement("INSERT INTO mark (student_id, discipline_id, professor_id, mark, create_data) VALUES"
						+ " (?, (SELECT id FROM discipline WHERE discipline.title like ?), ?, ?, ?)");
		statement.setInt(1, mark.getStudent().getId());
		statement.setString(2, mark.getDiscipline().getTitle());
		statement.setInt(3, mark.getProfesor().getId());
		statement.setDouble(4, mark.getMark());
		statement.setDate(5, new java.sql.Date(System.currentTimeMillis()));
		statement.executeUpdate();
		if (statement != null) {
			statement.close();
		}
	}
}
