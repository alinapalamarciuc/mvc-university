package model;

public class Group {

	private int id;
	private String name;

	public Group() {
	}

	public Group(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public Group(int group) {
		this.id = group;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
