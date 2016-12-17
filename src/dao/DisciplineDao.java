package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.Discipline;
import model.Profesor;

public class DisciplineDao extends AbstractDao {

	public DisciplineDao(Connection conn) {
		super(conn);
	}

	// get list of all distinct discipline
	public List<Discipline> getDistinctDisciplines() throws SQLException {
		List<Discipline> disciplines = new ArrayList<>();
		PreparedStatement statement = this.getConnection()
				.prepareStatement("SELECT DISTINCT id, title FROM discipline");
		ResultSet rs = statement.executeQuery();
		while (rs.next()) {
			Discipline discipline = new Discipline();
			discipline.setId(rs.getInt("id"));
			discipline.setTitle(rs.getString("title"));
			disciplines.add(discipline);
		}
		if (statement != null) {
			statement.close();
		}
		return disciplines;
	}

	// get list of disciplines studied by this student
	public Set<Discipline> getAllDisciplineOfStudent(int idStud) throws SQLException {
		Set<Discipline> disciplines = new HashSet<Discipline>();
		PreparedStatement stm = this.getConnection()
				.prepareStatement("SELECT * FROM discipline INNER JOIN discipline_student "
						+ "ON discipline.id=discipline_student.discipline_id " + "where student_id=?");
		stm.setInt(1, idStud);
		ResultSet rs = stm.executeQuery();

		while (rs.next()) {
			Discipline discipline = new Discipline();
			discipline.setId(rs.getInt("id"));
			discipline.setTitle(rs.getString("title"));
			Profesor profesor = new Profesor();
			disciplines.add(discipline);
		}
		if (stm != null) {
			stm.close();
		}
		return disciplines;
	}
}
