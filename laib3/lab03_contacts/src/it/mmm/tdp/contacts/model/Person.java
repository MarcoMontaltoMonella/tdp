package it.mmm.tdp.contacts.model;

import java.util.Comparator;



public class Person implements Comparable<Person> {
	public enum Gender{ MALE, FEMALE }
	private String name, surname, birth, phone;
	private int id;
	private String photoPath;
	private Gender sex;

	/**
	 * 
	 * @param name
	 * @param surname
	 * @param birth
	 * @param phone
	 * @param sex
	 * @param photoPath
	 */
	public Person(String name, String surname,
	 String birth, String phone, Gender sex, String photoPath){
		this.name = name;
		this.surname = surname;
		this.birth = birth;
		this.phone = phone;
		this.photoPath = photoPath;
		this.sex = sex;
	}

	/**
	 * 
	 * @param name
	 * @param surname
	 * @param birth
	 * @param phone
	 * @param sex
	 */
	public Person(String name, String surname,
	 String birth, String phone, Gender sex){
		this.name = name;
		this.surname = surname;
		this.birth = birth;
		this.phone = phone;
		this.sex = sex;
		this.photoPath = null;
	}

	public int getId(){
		return this.id;
	}

	public String getName(){
		return this.name;
	}

	public String getSurname(){
		return this.surname;
	}

	public String getBirth(){
		return this.birth;
	}

	public String getPhone(){
		return this.phone;
	}

	public Gender getSex(){
		return this.sex;
	}

	public String getPhotoPath(){
		return this.photoPath;
	}

	public void setName(String name){
		this.name = name;
	}

	public void setSurname(String surname){
		this.surname = surname;
	}

	public void setBirth(String birth){
		this.birth = birth;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}

	public void setPhotoPath(String photoPath){
		this.photoPath = photoPath;
	}


	public void setId(int id){
		this.id = id;
	}
	
	public boolean hasPhoto(){
		if(this.photoPath==null)
			return false;
		return true;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
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
		Person other = (Person) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (surname == null) {
			if (other.surname != null)
				return false;
		} else if (!surname.equals(other.surname))
			return false;
		return true;
	}

	@Override
 	public int compareTo(Person p) {
		if(this.getName().compareTo(p.getName())!=0)
			return this.getName().compareTo(p.getName());
		if(this.getSurname().compareTo(p.getSurname())!=0)
			return this.getSurname().compareTo(p.getSurname());
		return 0;
	}

	@Override
	public String toString(){
		return "["+id+"]"+name+" "+surname;
	}
	
	public class SortByName implements Comparator<Person>{
		public int compare(Person p1, Person p2){
			return p1.getName().compareToIgnoreCase(p2.getName());
		}
	}
	
	public class SortBySurname implements Comparator<Person>{
		public int compare(Person p1, Person p2){
			return p1.getSurname().compareToIgnoreCase(p2.getSurname());
		}
	}
}