package model;

import java.util.List;

public class Discipline {

	private int id;
	private String title;
	private double scholarship_threshold;
	private List<Profesor> profesor;

	public Discipline(int id, String title) {
		this.id = id;
		this.title = title;
	}

	public Discipline() {
	}

	public Discipline(int id, String title, double scholarship_threshold, List<Profesor> profesor) {
		this.id = id;
		this.title = title;
		this.scholarship_threshold = scholarship_threshold;
		this.profesor = profesor;
	}

	public Discipline(Integer disciplineId) {
		this.id = disciplineId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getScholarship_threshold() {
		return scholarship_threshold;
	}

	public void setScholarship_threshold(double scholarship_threshold) {
		this.scholarship_threshold = scholarship_threshold;
	}

	public List<Profesor> getProfesor() {
		return profesor;
	}

	public void setProfesor(List<Profesor> profesor) {
		this.profesor = profesor;
	}
}
