package model;

public class DisciplineAverage {

	private int id;
	private Student student;
	private Discipline discipline;
	private double averageMark;

	public DisciplineAverage() {
	}

	public DisciplineAverage(int id, Student student, Discipline discipline, double averageMark) {
		this.id = id;
		this.student = student;
		this.discipline = discipline;
		this.averageMark = averageMark;
	}

	public DisciplineAverage(int id, Student student, Discipline discipline) {
		this.id = id;
		this.student = student;
		this.discipline = discipline;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Discipline getDiscipline() {
		return discipline;
	}

	public void setDiscipline(Discipline discipline) {
		this.discipline = discipline;
	}

	public double getAverageMark() {
		return averageMark;
	}

	public void setAverageMark(double averageMark) {
		this.averageMark = averageMark;
	}

}