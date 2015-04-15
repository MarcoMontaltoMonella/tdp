package it.mmm.tdp.contacts.model;

import it.mmm.tdp.contacts.model.Person.Gender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.sun.xml.internal.ws.util.StringUtils;

public class ContactsDB{
	private Map<Integer,Person> contacts;

	//private String tmpPicAbsolutePath;
	
	public ContactsDB(){
		this.contacts = new TreeMap<>();
		try {
			this.addPerson(new Person("Jack", "Smith", "01/02/1950",
					"+33 3395238167", Person.Gender.MALE));
			this.addPerson(new Person("Jhonny", "Black", "01/12/1950",
					"+33 3391252896", Person.Gender.MALE));
			this.addPerson(new Person("Albert", "Zack", "21/07/1955",
					"+39 3391850816", Person.Gender.MALE));
			this.addPerson(new Person("Lara", "Croft", "14/09/1975",
					"+44 3847162856", Person.Gender.FEMALE, "userLaraCroft.jpg"));
			this.addPerson(new Person("Jasmine", "Stone", "17/04/1993",
					"+44 3643182913", Person.Gender.FEMALE));
			
		} catch (DuplicateContactException e) {}
	}

	
	
	public Map<Integer,Person> getContacts(){
		 Map<Integer,Person> copy = contacts;
		return copy;
	}

	public void addPerson(Person p) throws DuplicateContactException{
		if(!contacts.containsValue(p)){
			cicle: 
				for(int i=0; i<= contacts.size(); i++){
					if(contacts.containsKey(i))
						continue;
					else{
						p.setId(i);
						contacts.put(i, p);
						break cicle;
					}
				}
		}else
			throw new DuplicateContactException();
	}

	public boolean removePerson(int id){
		if(contacts.containsKey(id)){
			contacts.remove(id);
			return true;
		}
		return false;
	}
	
	//sortedByNameByDefault
	public ArrayList<Person> searchPersons(String name, String surname, String birth, String phone){
		return contacts.values().stream().
				filter(p -> ((p.getName().contains(name)) && !name.equals("") ) ||
							(p.getSurname().contains(surname) && !surname.equals("")) ||
							(p.getBirth().equals(birth)  && !birth.equals("") ||
							p.getPhone().contains(phone) && !phone.equals(""))).sorted()
				.collect(Collectors.toCollection(ArrayList::new));
		
		
	}
	
	public boolean searchSinglePerson(String name, String surname, String birth, String phone, Gender sex){
		Person p = new Person(name, surname, birth, phone, sex);
		if(contacts.containsValue(p))
			return true;
		return false;
	}
	
	public Person getPerson(int id){
		if(contacts.containsKey(id))
			return contacts.get(id);
		else
			return null;
	}
	
	public ArrayList<Person> getPersonsNameStartingWith(String letter){
		return contacts.values().stream().
				filter(p -> p.getName().substring(0, 1).compareToIgnoreCase(letter) == 0 ).sorted()
				.collect(Collectors.toCollection(ArrayList::new));
	}
	
	public ArrayList<Person> getPersonsSurnameStartingWith(String letter){
		return contacts.values().stream().
				filter(p -> p.getSurname().substring(0, 1).compareToIgnoreCase(letter) == 0 ).sorted()
				.collect(Collectors.toCollection(ArrayList::new));
	}

	
	public boolean isEmptyDB() {
		return contacts.isEmpty();
	}



	public boolean editPerson(int id, Person p_edited) {
		if(contacts.containsKey(id)){
			contacts.put(id,p_edited);
			return true;
		}else{
			return false;
		}
	}
	
	public ArrayList<String> getCurrentLettersNames(){
		ArrayList<String> result = new ArrayList<>();
		for(Person p : contacts.values()){
			String letter = p.getName().substring(0, 1);
			if(!result.contains(letter))
				result.add(letter.toUpperCase());
		}
		return result;
	}
	
	public ArrayList<String> getCurrentLettersSurnames(){
		ArrayList<String> result = new ArrayList<>();
		for(Person p : contacts.values()){
			String letter = p.getSurname().substring(0, 1);
			if(!result.contains(letter))
				result.add(letter.toUpperCase());
		}
		return result;
	}
	
	public ArrayList<String> getCurrentLettersNames(ArrayList<Person> list){
		ArrayList<String> result = new ArrayList<>();
		for(Person p : list){
			String letter = p.getName().substring(0, 1);
			if(!result.contains(letter))
				result.add(letter.toUpperCase());
		}
		return result;
	}
	
	public ArrayList<String> getCurrentLettersSurnames(ArrayList<Person> list){
		ArrayList<String> result = new ArrayList<>();
		for(Person p : list){
			String letter = p.getSurname().substring(0, 1);
			if(!result.contains(letter))
				result.add(letter.toUpperCase());
		}
		return result;
	}
	
	
	
	
	//MAIN
	public static void main(String[] args) {
		ContactsDB cdb = new ContactsDB();
		try {
			cdb.addPerson(new Person("Jack", "Smith", "01/02/1950",
					"+33 3395238167", Person.Gender.MALE));
			cdb.addPerson(new Person("Jhonny", "Black", "01/12/1950",
					"+33 3391252896", Person.Gender.MALE));
			cdb.addPerson(new Person("Albert", "Zack", "21/07/1955",
					"+33 3391850816", Person.Gender.MALE));
		} catch (DuplicateContactException e) {
			e.printStackTrace(); //PRINT EXCEPTION MESSAGE
		}
		
		cdb.getContacts().values().stream().forEach(p -> System.out.println(p));
//		Person p = new Person("a", "b", "01/04/1994", "235321523461", Gender.FEMALE);
		
		System.out.println(cdb.getPersonsNameStartingWith("j"));
		System.out.println(cdb.getCurrentLettersNames());
		System.out.println(cdb.getCurrentLettersSurnames());
		ArrayList<Person> list = cdb.searchPersons("J", "", "", ""); //CASE SENSITIVE
		Collections.sort(list, new Comparator<Person>(){

			@Override
			public int compare(Person o1, Person o2) {
				return o1.getSurname().compareTo(o2.getSurname());
			}
			
		});
		System.out.println(list);
	}
}



