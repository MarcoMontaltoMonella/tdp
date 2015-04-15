/**
 * 15 Apr 2015, 11:55:12
 */
package it.polito.tdp.mmm.anagrams.model;


public class Word {

	private int id;
	private String nome;
	
	public Word(int id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	public int getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}
	
	
}
