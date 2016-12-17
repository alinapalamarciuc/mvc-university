package model;

public class PhoneType {

	private int id;
	private String phoneNameType;

	public PhoneType() {
	}

	public PhoneType(int id, String phoneNameType) {
		this.id = id;
		this.phoneNameType = phoneNameType;
	}

	public PhoneType(Integer id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPhoneNameType() {
		return phoneNameType;
	}

	public void setPhoneNameType(String phoneNameType) {
		this.phoneNameType = phoneNameType;
	}

}
