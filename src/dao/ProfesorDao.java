package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Profesor;

public class ProfesorDao extends AbstractDao {

	public ProfesorDao(Connection conn) {
		super(conn);
	}

	// get list professors who teach this discipline
	public List<Profesor> getProfesorForDisciplines(int idDisc) throws SQLException {
		PreparedStatement stm = null;
		List<Profesor> listProf = new ArrayList<Profesor>();
		stm = this.getConnection().prepareStatement(
				"SELECT professor_discipline.profesor_id, last_name, first_name FROM discipline inner join professor_discipline "
						+ "on professor_discipline.discipline_id = discipline.id inner join person on person.id=professor_discipline.profesor_id "
						+ "where discipline_id =?");
		stm.setInt(1, idDisc);
		ResultSet rs = stm.executeQuery();
		while (rs.next()) {
			Profesor profesor = new Profesor();
			profesor.setId(rs.getInt("profesor_id"));
			profesor.setFirstName(rs.getString("first_name"));
			profesor.setLastName(rs.getString("last_name"));
			listProf.add(profesor);
		}
		if (stm != null) {
			stm.close();
		}
		return listProf;

	}
}
