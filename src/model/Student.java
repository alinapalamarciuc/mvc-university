package model;

import java.util.Date;
import java.util.List;

public class Student extends Person {
	private int id;
	private List<DisciplineAverage> average;
	private List<Discipline> discipline;
	private Group group;

	public Student() {
		super();
	}

	public Student(int id, Group group, String firstName, String lastName, Date dob, char c, byte[] picture) {
		super(firstName, lastName, dob, c, picture);
		this.id = id;
		this.group = group;
	}

	public Student(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<DisciplineAverage> getAverage() {
		return average;
	}

	public void setAverage(List<DisciplineAverage> average) {
		this.average = average;
	}

	public List<Discipline> getDiscipline() {
		return discipline;
	}

	public void setDiscipline(List<Discipline> discipline) {
		this.discipline = discipline;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public int calculateScholarship() {

		return 1;

	}

}