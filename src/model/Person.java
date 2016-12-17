package model;

import java.util.Date;
import java.util.List;

public abstract class Person {

	private int id;
	private String firstName;
	private String lastName;
	private Date dob;
	private char gender;
	private Address addres;
	private LibraryAbonament abonament;
	private List<Phone> phone;
	private byte[] picture;

	public Person() {
	}

	public Person(int id, String firstName, String lastName, Date dob, char gender, Address addres,
			LibraryAbonament abonament, List<Phone> phone, byte[] picture) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dob = dob;
		this.gender = gender;
		this.addres = addres;
		this.abonament = abonament;
		this.phone = phone;
		this.picture = picture;
	}

	public Person(String firstName, String lastName, Date dob, char c, byte[] picture) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.dob = dob;
		this.gender = c;
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

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LibraryAbonament getAbonament() {
		return abonament;
	}

	public void setAbonament(LibraryAbonament abonament) {
		this.abonament = abonament;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char string) {
		this.gender = string;
	}

	public List<Phone> getPhone() {
		return phone;
	}

	public void setPhone(List<Phone> phone) {
		this.phone = phone;
	}

	public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

	public Address getAddres() {
		return addres;
	}

	public void setAddres(Address addres) {
		this.addres = addres;
	}

}