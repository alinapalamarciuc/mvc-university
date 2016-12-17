package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Phone;
import model.PhoneType;

public class PhoneDao extends AbstractDao {

	public PhoneDao(Connection conn) {
		super(conn);
	}

	// gel all phones
	public List<Phone> getAllPhones(int id) throws SQLException {
		List<Phone> phones = new ArrayList<>();
		PhoneType type = new PhoneType();
		PreparedStatement statement = this.getConnection()
				.prepareStatement("SELECT * from phone LEFT JOIN phone_type ON phone.id_type=phone_type.type_id"
						+ " LEFT JOIN person_phone ON person_phone.phone_id=phone.id"
						+ " where person_phone.person_id=?");
		statement.setInt(1, id);
		ResultSet rs = statement.executeQuery();
		while (rs.next()) {
			Phone phone = new Phone();
			phone.setId(rs.getInt("id"));
			phone.setNumber(rs.getString("number"));
			type.setId(rs.getInt("type_id"));
			type.setPhoneNameType(rs.getString("name"));
			phone.setType((type));
			phones.add(phone);
		}
		if (statement != null) {
			statement.close();
		}
		return phones;
	}

	// get all phone types
	public List<PhoneType> getAllTypes() throws SQLException {
		List<PhoneType> types = new ArrayList<>();
		PreparedStatement statement = this.getConnection().prepareStatement("SELECT * FROM phone_type");
		ResultSet rs = statement.executeQuery();
		while (rs.next()) {
			PhoneType type = new PhoneType();
			type.setId(rs.getInt("type_id"));
			type.setPhoneNameType(rs.getString("name"));
			types.add(type);
		}
		if (statement != null) {
			statement.close();
		}
		return types;
	}

	// search if exist phone with values introduced
	public int getPhone(Phone phone) throws SQLException {
		int idPhone = 0;
		PreparedStatement stm = this.getConnection()
				.prepareStatement("SELECT * FROM phone WHERE number=? AND id_type=?");
		ResultSet res = null;
		stm.setString(1, phone.getNumber());
		stm.setInt(2, phone.getType().getId());
		res = stm.executeQuery();
		if (res.next()) {
			idPhone = res.getInt("id");
		}
		if (stm != null) {
			stm.close();
		}
		return idPhone;
	}

	// add new phone
	public void insertPhone(Phone phone) throws SQLException {
		PreparedStatement stm = this.getConnection()
				.prepareStatement("INSERT INTO phone (number, id_type) VALUES (?,?)");
		stm.setString(1, phone.getNumber());
		stm.setInt(2, phone.getType().getId());
		stm.executeUpdate();
		if (stm != null) {
			stm.close();
		}
	}

	// find id latest phone
	public int getLastIdPhone() throws SQLException {
		ResultSet res = null;
		int idPhone;
		res = this.getConnection().createStatement().executeQuery("SELECT id from phone ORDER BY id DESC LIMIT 1");
		res.next();
		idPhone = res.getInt("id");
		if (res != null) {
			res.close();
		}
		return idPhone;
	}

	// create link between person and phone tables
	public void insertPhoneToStudent(int studentId, int phoneId) throws SQLException {
		PreparedStatement stm = this.getConnection()
				.prepareStatement("INSERT INTO person_phone (person_id, phone_id) VALUES (?,?)");
		stm.setInt(1, studentId);
		stm.setInt(2, phoneId);
		stm.executeUpdate();
		if (stm != null) {
			stm.close();
		}
	}

	// delete all phones by this student
	public void deleteAllPhonesStudentById(int idStud) throws SQLException {
		PreparedStatement stm = this.getConnection().prepareStatement("DELETE FROM person_phone WHERE person_id = ?");
		stm.setInt(1, idStud);
		stm.executeUpdate();
		if (stm != null) {
			stm.close();
		}
	}

	// delete not used phones
	public void rewiewPhones() throws SQLException {
		PreparedStatement stm = this.getConnection()
				.prepareStatement("DELETE FROM phone WHERE id NOT IN (SELECT phone_id FROM person_phone)");
		stm.executeUpdate();
		if (stm != null) {
			stm.close();
		}
	}
}
