package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import model.AbonamentStatus;
import model.Address;
import model.Group;
import model.LibraryAbonament;
import model.NewStudent;
import model.Student;
import model.StudentSearchDTO;

public class StudentDao extends AbstractDao {

	public StudentDao(Connection conn) {
		super(conn);

	}

	public List<Student> getAllStudents() throws SQLException {
		List<Student> students = new ArrayList<>();
		PreparedStatement statement = this.getConnection()
				.prepareStatement("with num AS (SELECT student.id as stud, avg(mark) as discipline_avg "
						+ "FROM student inner join person on person.id=student.id "
						+ "left join mark on mark.student_id=student.id group by stud), "
						+ "fin AS (SELECT *  From person INNER JOIN student ON person.id = student.id "
						+ "LEFT JOIN grup ON grup.id=student.grup_id "
						+ "LEFT JOIN library_abonament ON library_abonament.person_id=person.id "
						+ "LEFT JOIN person_address ON person_address.person_id = person.id "
						+ "LEFT JOIN address ON person_address.address_id = address.address_id "
						+ "Left join num on student.id = num.stud) SELECT * from fin");
		ResultSet rs = statement.executeQuery();
		while (rs.next()) {
			Student student = new Student();
			Group grupa = new Group();
			LibraryAbonament abonament = new LibraryAbonament();
			Address adresa = new Address();
			student.setId(rs.getInt("stud"));
			student.setFirstName(rs.getString("first_name"));
			student.setLastName(rs.getString("last_name"));
			student.setGender(rs.getString("gender").charAt(0));
			student.setDob(rs.getDate("date_of_birth"));

			grupa.setId(rs.getInt("grup_id"));
			grupa.setName(rs.getString("name"));
			student.setGroup(grupa);
			adresa.setId(rs.getInt("address_id"));
			adresa.setCountry(rs.getString("country"));
			adresa.setCity(rs.getString("city"));
			adresa.setAddress(rs.getString("address"));
			student.setAddres(adresa);
			if (rs.getString("status") == null) {
				abonament.setAbonamentStatus(AbonamentStatus.valueOf("NONE"));
			} else {
				abonament.setId(rs.getInt("lib_id"));
				abonament.setAbonamentStatus(AbonamentStatus.valueOf(rs.getString("status")));
				abonament.setStartDate(rs.getDate("start_date"));
				abonament.setEndDate(rs.getDate("end_date"));
			}
			student.setAbonament(abonament);
			student.setPicture(rs.getBytes("picture"));
			students.add(student);
		}
		if (statement != null) {
			statement.close();
		}
		return students;
	}

	public void updatePerson(Student student) throws SQLException {
		PreparedStatement stm = null;
		stm = super.getConnection().prepareStatement(
				"UPDATE person SET first_name=?, last_name=?, date_of_birth=?, gender=?, picture=? WHERE id=?");
		stm.setString(1, student.getFirstName());
		stm.setString(2, student.getLastName());
		stm.setDate(3, new Date(student.getDob().getTime()));
		stm.setString(4, student.getGender() + "");
		stm.setBytes(5, student.getPicture());
		stm.setInt(6, student.getId());
		stm.executeUpdate();
		if (stm != null) {
			stm.close();
		}
	}

	public void updateStudent(Student student) throws SQLException {
		PreparedStatement stm = null;
		stm = super.getConnection().prepareStatement("UPDATE student SET grup_id=? WHERE id=?");
		stm.setInt(1, student.getGroup().getId());
		stm.setInt(2, student.getId());
		stm.executeUpdate();
		if (stm != null) {
			stm.close();
		}
	}

	// add new student
	public void insertPerson(Student student) throws SQLException {
		PreparedStatement stm = null;
		stm = super.getConnection().prepareStatement(
				"INSERT INTO person (first_name, last_name, date_of_birth, gender, picture) VALUES (?, ?, ?, ?, ?)");
		stm.setString(1, student.getFirstName());
		stm.setString(2, student.getLastName());
		stm.setDate(3, new Date(student.getDob().getTime()));
		stm.setString(4, student.getGender() + "");
		stm.setBytes(5, student.getPicture());
		stm.executeUpdate();
		if (stm != null) {
			stm.close();
		}
	}

	// get last id student
	public int selectLastIdStudent() throws SQLException {
		ResultSet res = super.getConnection().createStatement()
				.executeQuery("SELECT * FROM person ORDER BY id DESC LIMIT 1");
		res.next();
		int id = res.getInt("id");
		if (res != null) {
			res.close();
		}
		return id;
	}

	public void insertStudent(Student student, int id) throws SQLException {
		PreparedStatement stm = null;
		stm = super.getConnection().prepareStatement("INSERT INTO student (id, grup_id) VALUES (?,?)");
		stm.setInt(1, id);
		stm.setInt(2, student.getGroup().getId());
		stm.executeUpdate();
		if (stm != null) {
			stm.close();
		}
	}

	public NewStudent getStudentById(int id) throws SQLException {
		NewStudent nStudent = new NewStudent();
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		PreparedStatement stm = super.getConnection()
				.prepareStatement("SELECT * From person INNER JOIN student ON person.id = student.id "
						+ "LEFT JOIN grup ON grup.id=student.grup_id LEFT JOIN person_address ON person_address.person_id = person.id "
						+ "LEFT JOIN address ON person_address.address_id = address.address_id WHERE student.id=?");
		stm.setInt(1, id);
		ResultSet rs = stm.executeQuery();
		rs.next();
		nStudent.setId(id);
		nStudent.setFirstName(rs.getString("first_name"));
		nStudent.setLastName(rs.getString("last_name"));
		nStudent.setDob(df.format(rs.getDate("date_of_birth")));
		nStudent.setGender(rs.getString("gender"));
		nStudent.setCountry(rs.getString("country"));
		nStudent.setCity(rs.getString("city"));
		nStudent.setAddress(rs.getString("address"));
		nStudent.setGroup(rs.getInt("grup_id"));
		nStudent.setPicture(rs.getBytes("picture"));
		if (stm != null) {
			stm.close();
		}
		return nStudent;
	}

	public boolean deleteStudent(int idSt) throws SQLException {
		PreparedStatement stm = super.getConnection().prepareStatement("DELETE FROM person WHERE id=?");
		stm.setInt(1, idSt);

		Boolean confirmDelete = false;
		if (stm.executeUpdate() >= 0) {
			confirmDelete = true;
		}
		if (stm != null) {
			stm.close();
		}
		return confirmDelete;
	}

	public List<Student> searchStudent(StudentSearchDTO search) throws SQLException {
		List<Student> students = new ArrayList<>();
		List<Object> params = new ArrayList<>();
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		java.util.Date parsed = null;
		StringBuilder sqlBuilder = new StringBuilder()
				.append("with media AS(" + "SELECT student_id, avg(average_mark) as total_average "
						+ "from discipline_average " + "group by student_id), "
						+ "fin AS (SELECT *  From person INNER JOIN student ON person.id = student.id "
						+ "LEFT JOIN grup ON grup.id=student.grup_id "
						+ "LEFT JOIN library_abonament ON library_abonament.person_id=person.id "
						+ "LEFT JOIN person_address ON person_address.person_id = person.id "
						+ "LEFT JOIN address ON person_address.address_id = address.address_id ");
		if ((search.getDiscipline() != 0) || (!search.getAverageDiscipline().equals(""))) {
			sqlBuilder.append("LEFT JOIN discipline_average on discipline_average.student_id=person.id "
					+ "INNER JOIN discipline ON discipline.id=discipline_average.discipline_id ");
		}
		sqlBuilder.append("LEFT JOIN media ON media.student_id=student.id ) SELECT * from fin ");
		sqlBuilder.append("where 1=1 ");
		if (search.getName() != null && !search.getName().equals("")) {
			sqlBuilder.append(" AND ((last_name  ||' '|| first_name like ?) OR (first_name ||' '|| last_name like ?))");
			params.add(search.getName() + "%");
			params.add(search.getName() + "%");
		}

		if (search.getAddress() != null && !search.getAddress().equals("")) {
			sqlBuilder
					.append(" AND ((country ||' '|| city ||' '|| address LIKE ?) OR (country ||' '|| address ||' '|| city LIKE ?)"
							+ " OR (city ||' '|| country ||' '|| address LIKE ?) OR (city ||' '|| address ||' '|| country LIKE ?) "
							+ " OR (address ||' '|| country ||' '|| city LIKE ?) OR (address ||' '|| city ||' '|| country LIKE ?))");
			params.add(search.getAddress() + "%");
			params.add(search.getAddress() + "%");
			params.add(search.getAddress() + "%");
			params.add(search.getAddress() + "%");
			params.add(search.getAddress() + "%");
			params.add(search.getAddress() + "%");
		}

		if (search.getGender() != null && (search.getGender().equals("M") || search.getGender().equals("F"))) {
			sqlBuilder.append(" AND (gender = ?)");
			params.add(search.getGender());
		}

		if ((search.getDataOfBirthStart() != null) && (!search.getDataOfBirthStart().equals(""))) {
			sqlBuilder.append("AND (date_of_birth >=?)");
			try {
				parsed = format.parse(search.getDataOfBirthStart());

			} catch (ParseException e) {
				e.printStackTrace();
			}
			params.add(new java.sql.Date(parsed.getTime()));
		}

		if ((search.getDataOfBirthEnd() != null) && (!search.getDataOfBirthEnd().equals(""))) {
			sqlBuilder.append("AND (date_of_birth <=?)");
			try {
				parsed = format.parse(search.getDataOfBirthEnd());

			} catch (ParseException e) {
				e.printStackTrace();
			}
			params.add(new java.sql.Date(parsed.getTime()));
		}

		if (search.getGroup() != 0) {
			sqlBuilder.append(" AND grup_id=? ");
			params.add(search.getGroup());
		}

		if ((!search.getTotalAverage().isEmpty())) {
			sqlBuilder.append(" AND total_average = ? ");
			params.add(new Double(search.getTotalAverage()));
		}
		if (search.getDiscipline() != 0) {
			sqlBuilder.append(" AND discipline_id = ? ");
			params.add(search.getDiscipline());
		}

		if ((!search.getAverageDiscipline().isEmpty())) {
			sqlBuilder.append(" AND average_mark = ? ");
			params.add(new Double(search.getAverageDiscipline()));
		}

		String sql = sqlBuilder.toString();
		PreparedStatement statement = this.getConnection().prepareStatement(sql);

		for (int i = 1; i <= params.size(); i++) {
			Object s = params.get(i - 1);
			if (s instanceof String)
				statement.setString(i, (String) s);
			if (s instanceof Integer)
				statement.setInt(i, (Integer) s);
			if (s instanceof Double)
				statement.setDouble(i, (Double) s);
			if (s instanceof java.util.Date)
				statement.setDate(i, new Date(((java.util.Date) s).getTime()));
		}
		System.out.println(statement);
		ResultSet rs = statement.executeQuery();
		while (rs.next()) {
			Student student = new Student();
			Group grupa = new Group();
			LibraryAbonament abonament = new LibraryAbonament();
			Address adresa = new Address();
			student.setId(rs.getInt("id"));
			student.setFirstName(rs.getString("first_name"));
			student.setLastName(rs.getString("last_name"));
			student.setGender(rs.getString("gender").charAt(0));
			student.setDob(rs.getDate("date_of_birth"));

			grupa.setId(rs.getInt("grup_id"));
			grupa.setName(rs.getString("name"));
			student.setGroup(grupa);
			adresa.setId(rs.getInt("address_id"));
			adresa.setCountry(rs.getString("country"));
			adresa.setCity(rs.getString("city"));
			adresa.setAddress(rs.getString("address"));
			student.setAddres(adresa);
			if (rs.getString("status") == null) {
				abonament.setAbonamentStatus(AbonamentStatus.valueOf("NONE"));
			} else {
				abonament.setId(rs.getInt("lib_id"));
				abonament.setAbonamentStatus(AbonamentStatus.valueOf(rs.getString("status")));
				abonament.setStartDate(rs.getDate("start_date"));
				abonament.setEndDate(rs.getDate("end_date"));
			}
			student.setAbonament(abonament);
			student.setPicture(rs.getBytes("picture"));
			students.add(student);
		}
		if (statement != null) {
			statement.close();
		}
		return students;
	}
}
