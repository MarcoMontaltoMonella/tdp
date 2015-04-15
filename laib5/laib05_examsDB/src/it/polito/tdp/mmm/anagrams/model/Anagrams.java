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

	public static void main(String[] args) {
		Anagrams anagram = new Anagrams();
		DAOword dao = new DAOword();
		System.out.println(anagram.anagramOf("dog"));
		
		System.out.println(dao.countWords());
		System.out.println(dao.getWordsOfLenght(28));
		System.out.println(dao.contains("casa"));
		System.out.println(dao.containsWordStartingWith("solar"));
		System.out.println(dao.getWordsStartingWith("solar"));
		
	}
	
	
}
