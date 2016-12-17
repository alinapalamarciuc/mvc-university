package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Address;

public class AddressDao extends AbstractDao {

	public AddressDao(Connection conn) {
		super(conn);
	}

	// search if exist address with values introduced
	public int getAddress(Address address) throws SQLException {
		int idAddres = 0;
		PreparedStatement stm = this.getConnection()
				.prepareStatement("SELECT * FROM address WHERE country=? AND city=? AND address=?");
		ResultSet res = null;
		stm.setString(1, address.getCountry());
		stm.setString(2, address.getCity());
		stm.setString(3, address.getAddress());
		res = stm.executeQuery();
		if (res.next()) {
			idAddres = res.getInt("address_id");
		}
		if (stm != null) {
			stm.close();
		}
		return idAddres;
	}

	// edit existent address
	public void insertAddress(Address address) throws SQLException {
		PreparedStatement stm = this.getConnection()
				.prepareStatement("INSERT INTO address (country, city, address) VALUES (?, ?, ?)");
		stm.setString(1, address.getCountry());
		stm.setString(2, address.getCity());
		stm.setString(3, address.getAddress());
		stm.executeUpdate();
		if (stm != null) {
			stm.close();
		}
	}

	// get last id_address
	public int getLastIdAddres() throws SQLException {
		ResultSet res = null;
		int idAddres;
		res = this.getConnection().createStatement()
				.executeQuery("SELECT address_id from address ORDER BY address_id DESC LIMIT 1");
		res.next();
		idAddres = res.getInt("address_id");
		if (res != null) {
			res.close();
		}
		return idAddres;
	}

	// create link between person and address tables
	public void insertAddressToStudent(int studentId, int addresId) throws SQLException {
		PreparedStatement stm = this.getConnection()
				.prepareStatement("INSERT INTO person_address (person_id, address_id) VALUES (?,?)");
		stm.setInt(1, studentId);
		stm.setInt(2, addresId);
		stm.executeUpdate();
		if (stm != null) {
			stm.close();
		}
	}

	// modify link between person and address tables
	public void updateAddressToStudent(int studentId, int addresId) throws SQLException {
		PreparedStatement stm = this.getConnection()
				.prepareStatement("UPDATE person_address SET address_id=? WHERE person_id=?");
		stm.setInt(1, addresId);
		stm.setInt(2, studentId);
		stm.executeUpdate();
		if (stm != null) {
			stm.close();
		}
	}

	// delete not used address
	public void rewiewAddress() throws SQLException {
		PreparedStatement stm = this.getConnection().prepareStatement(
				"DELETE FROM address WHERE address_id NOT IN " + "(SELECT address_id FROM person_address)");
		stm.executeUpdate();
		if (stm != null) {
			stm.close();
		}
	}

}
