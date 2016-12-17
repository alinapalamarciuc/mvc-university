package model;

public class Profesor extends Person {
	private int id;
	private double salary;

	public Profesor() {
		super();
	}

	public Profesor(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}
}