package ese05_iscritticorsiDB.model;

import java.util.List;

/**
 * This is a student bin.
 * @author MMM
 * 
 */
public class Student {
	
	private String surname,name,cds;
	private int id;
	private List<String> courses;
	
	/**
	 * Student class
	 * 
	 * @param surname
	 * @param name
	 * @param cds
	 * @param id
	 */
	public Student(String surname, String name, String cds, int id) {
		this.surname = surname;
		this.name = name;
		this.cds = cds;
		this.id = id;
	}

	public List<String> getCourses() {
		return courses;
	}

	public String getSurname() {
		return surname;
	}

	public String getName() {
		return name;
	}

	public String getCds() {
		return cds;
	}

	public int getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
}
