package model;

import java.util.Date;

public class LibraryAbonament {

	private int id;
	private AbonamentStatus abonamentStatus;
	private Date startDate;
	private Date endDate;

	public LibraryAbonament() {
	}

	public LibraryAbonament(int id, AbonamentStatus abonamentStatus, Date startDate, Date endDate) {
		this.id = id;
		this.abonamentStatus = abonamentStatus;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public AbonamentStatus getAbonamentStatus() {
		return abonamentStatus;
	}

	public void setAbonamentStatus(AbonamentStatus abonamentStatus) {
		this.abonamentStatus = abonamentStatus;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
