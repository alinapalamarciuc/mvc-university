package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Discipline;
import model.DisciplineAverage;

public class DisciplineAverageDao extends AbstractDao {

	public DisciplineAverageDao(Connection conn) {
		super(conn);
	}

	public List<DisciplineAverage> getDisciplineAverage(int idStud) throws SQLException {
		List<DisciplineAverage> disciplineAvg = new ArrayList<>();
		PreparedStatement statement = this.getConnection()
				.prepareStatement("SELECT * FROM discipline_average INNER JOIN discipline "
						+ "ON discipline_average.discipline_id = discipline.id " + "WHERE student_id=? "
						+ "ORDER BY discipline.title");
		statement.setInt(1, idStud);
		ResultSet rs = statement.executeQuery();
		while (rs.next()) {
			DisciplineAverage discAvg = new DisciplineAverage();
			Discipline discipline = new Discipline();
			discipline.setId(rs.getInt("discipline_id"));
			discipline.setTitle(rs.getString("title"));
			discAvg.setDiscipline(discipline);
			discAvg.setAverageMark(rs.getDouble("average_mark"));
			disciplineAvg.add(discAvg);
		}
		if (statement != null) {
			statement.close();
		}
		return disciplineAvg;
	}
}
