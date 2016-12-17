package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.AbonamentStatus;
import model.LibraryAbonament;
import model.Student;

public class LibraryDao extends AbstractDao {

	public LibraryDao(Connection conn) {
		super(conn);
	}

	public Student getLibrary(Integer id) throws SQLException {

		Student student = new Student();
		LibraryAbonament library = new LibraryAbonament();

		PreparedStatement statement = this.getConnection().prepareStatement(
				"SELECT * FROM library_abonament INNER JOIN person ON (library_abonament.person_id = person.id) "
						+ "WHERE library_abonament.lib_id = ?");
		statement.setInt(1, id);
		ResultSet rs = statement.executeQuery();
		while (rs.next()) {
			library.setId(rs.getInt("lib_id"));
			student.setId(rs.getInt("id"));
			student.setFirstName(rs.getString("first_name"));
			student.setLastName(rs.getString("last_name"));
			// library.setPerson(student);
			library.setAbonamentStatus(AbonamentStatus.valueOf(rs.getString("status")));
			library.setStartDate(rs.getDate("start_date"));
			library.setEndDate(rs.getDate("end_date"));
			student.setAbonament(library);
		}
		if (statement != null) {
			statement.close();
		}
		return student;
	}

	public void modifyLibrary(LibraryAbonament library) throws SQLException {
		PreparedStatement statement = this.getConnection().prepareStatement(
				"UPDATE library_abonament SET status = ?, start_date = ?, end_date = ? WHERE lib_id = ?");
		statement.setString(1, library.getAbonamentStatus().toString());
		if (library.getAbonamentStatus() == (AbonamentStatus.ACTIVE)) {
			statement.setDate(2, new java.sql.Date(library.getStartDate().getTime()));
			statement.setDate(3, new java.sql.Date(library.getEndDate().getTime()));
		} else {
			statement.setDate(2, null);
			statement.setDate(3, null);
		}
		statement.setInt(4, library.getId());
		statement.executeUpdate();
		if (statement != null) {
			statement.close();
		}
	}

	public void insertAbonamentStudent(int id) throws SQLException {
		PreparedStatement stm = null;
		stm = super.getConnection()
				.prepareStatement("INSERT INTO library_abonament (person_id, status) VALUES (?, 'NONE')");
		stm.setInt(1, id);
		stm.executeUpdate();
		if (stm != null) {
			stm.close();
		}
	}

}
