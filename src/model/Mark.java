package model;

import java.util.Date;

public class Mark {

	private int id;
	private Student student;
	private Discipline discipline;
	private Profesor profesor;
	private double mark;
	private Date createDate;

	public Mark() {
	}

	public Mark(int id, Student student, Discipline discipline, Profesor profesor, double mark) {
		this.id = id;
		this.student = student;
		this.discipline = discipline;
		this.profesor = profesor;
		this.mark = mark;
	}

	public Mark(Student student, Discipline discipline, Profesor profesor, double mark) {
		this.student = student;
		this.discipline = discipline;
		this.profesor = profesor;
		this.mark = mark;
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

	public Profesor getProfesor() {
		return profesor;
	}

	public void setProfesor(Profesor profesor) {
		this.profesor = profesor;
	}

	public double getMark() {
		return mark;
	}

	public void setMark(double mark) {
		this.mark = mark;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
