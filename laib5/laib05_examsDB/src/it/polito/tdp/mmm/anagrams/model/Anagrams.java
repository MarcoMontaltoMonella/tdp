/**
 * 15 Apr 2015, 10:41:48
 */
package it.polito.tdp.mmm.anagrams.model;

import it.polito.tdp.mmm.anagrams.db.DAOword;

import java.util.LinkedList;
import java.util.List;

public class Anagrams {

	List<String> result;

	public List<String> anagramOf(String word) {
		if (word.length() >= 6) {
			System.err.println("Max length exceeded!");
			return null;
		}
		result = new LinkedList<>();

		List<String> letters = new LinkedList<>();
		for (int i = 0; i < word.length(); i++) {
			letters.add(word.substring(i, i + 1));
		}

		this.recurse("", letters);

		return result;
	}
	
	private void recurse(String wordAlreadyBuilt, List<String> missingLetters) {
		
		
		
		if (missingLetters.isEmpty()) {
			this.result.add(wordAlreadyBuilt);
		} else {
			for (String singleLetter : missingLetters) {
				List<String> subset = new LinkedList<>(missingLetters);
				subset.remove(singleLetter);
				recurse(wordAlreadyBuilt+singleLetter,subset);
			}
		}
	}
	
	
	public List<String> anagramUsingDatabaseOf(String word) {
		if (word.length() >= 6) {
			System.err.println("Max length exceeded!");
			return null;
			
			//1. word.length()
			//2. words with the same length
			//3. words with the same letters
		}
		
		result = new LinkedList<>();

		List<String> letters = new LinkedList<>();
		for (int i = 0; i < word.length(); i++) {
			letters.add(word.substring(i, i + 1));
		}

		this.recurseWithDB("", letters);
		
		return result;
	}
	
	
	private void recurseWithDB(String wordAlreadyBuilt, List<String> missingLetters) {
		DAOword dao = new DAOword();
		if (missingLetters.isEmpty()) {
			this.result.add(wordAlreadyBuilt);
		} else {
			for (String singleLetter : missingLetters) {
				List<String> subset = new LinkedList<>(missingLetters);
				subset.remove(singleLetter);		
				//BRANCH & BOUND
				if(dao.containsWordStartingWith(wordAlreadyBuilt+singleLetter)){
					recurseWithDB(wordAlreadyBuilt+singleLetter,subset);
				}
			}
		}
	}

	public static void main(String[] args) {
		Anagrams anagram = new Anagrams();
		DAOword dao = new DAOword();
		System.out.println(anagram.anagramOf("pesce"));
		
		System.out.println(dao.countWords());
		System.out.println(dao.getWordsOfLenght(31));
		System.out.println(dao.contains("casa"));
		System.out.println(dao.containsWordStartingWith("solar"));
		System.out.println(dao.getWordsStartingWith("solar"));
		
		System.out.println();
		
		System.out.println(anagram.anagramUsingDatabaseOf("pesce"));
		
		
		
	}
	
	
}
