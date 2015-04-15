package it.mmm.tdp.contacts.model;


@SuppressWarnings("serial")
public class DuplicateContactException extends Exception{

	public DuplicateContactException(){
		super("Person already present in the database!");
	}
}