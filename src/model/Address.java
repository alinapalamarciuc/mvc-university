package model;

public class Address {

	private int id;
	private String country;
	private String city;
	private String address;

	public Address(String country, String city, String address) {
		this.country = country;
		this.city = city;
		this.address = address;
	}

	public Address() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
}