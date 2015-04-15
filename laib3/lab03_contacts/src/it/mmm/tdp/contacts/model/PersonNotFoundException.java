package it.mmm.tdp.contacts.model;

@SuppressWarnings("serial")
public class PersonNotFoundException extends Exception {

	public PersonNotFoundException(){
		super("Person not found!");
	}
}
