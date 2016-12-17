package model;

public class StudentSearchDTO {
	private String name;
	private String address;
	private int group;
	private String gender;
	private String dataOfBirthStart;
	private String dataOfBirthEnd;
	private int discipline;
	private String averageDiscipline;
	private String totalAverage;

	public StudentSearchDTO() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getGroup() {
		return group;
	}

	public void setGroup(int group) {
		this.group = group;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDataOfBirthStart() {
		return dataOfBirthStart;
	}

	public void setDataOfBirthStart(String dataOfBirthStart) {
		this.dataOfBirthStart = dataOfBirthStart;
	}

	public String getDataOfBirthEnd() {
		return dataOfBirthEnd;
	}

	public void setDataOfBirthEnd(String dataOfBirthEnd) {
		this.dataOfBirthEnd = dataOfBirthEnd;
	}

	public int getDiscipline() {
		return discipline;
	}

	public void setDiscipline(int discipline) {
		this.discipline = discipline;
	}

	public String getAverageDiscipline() {
		return averageDiscipline;
	}

	public void setAverageDiscipline(String averageDiscipline) {
		this.averageDiscipline = averageDiscipline;
	}

	public String getTotalAverage() {
		return totalAverage;
	}

	public void setTotalAverage(String totalAverage) {
		this.totalAverage = totalAverage;
	}

}
