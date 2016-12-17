package model;

import java.util.List;

public class NewStudent {
	private int id;
	private String firstName;
	private int group;
	private String lastName;
	private String dob;
	private String gender;
	private String country;
	private String city;
	private String address;
	private List<Phone> phones;
	private byte picture[];

	public NewStudent() {

	}

	public NewStudent(int id, String firstName, int group, String lastName, String dob, String gender, String country,
			String city, String address, List<Phone> phones, byte[] picture) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.group = group;
		this.lastName = lastName;
		this.dob = dob;
		this.gender = gender;
		this.country = country;
		this.city = city;
		this.address = address;
		this.phones = phones;
		this.picture = picture;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public int getGroup() {
		return group;
	}

	public void setGroup(int group) {
		this.group = group;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<Phone> getPhones() {
		return phones;
	}

	public void setPhones(List<Phone> phones) {
		this.phones = phones;
	}

	public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

}
