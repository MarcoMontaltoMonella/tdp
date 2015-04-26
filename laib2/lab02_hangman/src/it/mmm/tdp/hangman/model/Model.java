/**
 * 18 Mar 2015, 08:53:12
 */
package it.mmm.tdp.hangman.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marco_Montalto_Monella
 *
 */
public class Model {

	private List<String> insertedChars;
	private StringBuilder secretWord, hiddenSecretWord;
	private String superSecretWord;
	private int numberOfErrors;
	private boolean playing;
	private final int MAX_NUM_OF_ERRORS = 8;
	private String finalWord;
	
	public Model() {
		super();
		this.insertedChars = new ArrayList<>();
		this.numberOfErrors = 0;
		this.playing = false;
		this.reset();
	}

	public List<String> getInsertedChars() {
		return insertedChars;
	}

	public String guessChar(String c) throws RepeatedCharException {
		boolean flag = true;
		if(!insertedChars.contains(c)){
			this.insertedChars.add(c);
			while(this.checkChar(c)>=0){
				flag = false;
				this.revealChar(c);
				this.checkChar(c);
			}
			if(flag){
				numberOfErrors++;
				if(numberOfErrors >= MAX_NUM_OF_ERRORS)
					this.playing = false;
			}
			return c;
		}
		else throw new RepeatedCharException();
	}

	public String getSecretWord() {
		return secretWord.toString();
	}

	public void setSecretWord(String secretWord) {
		this.secretWord = new StringBuilder(secretWord);
		this.hideSecretWord(secretWord);
		this.superSecretWord = secretWord;
		this.setFinalWord();
	}
	
	public int checkChar(String c){
		String secr = secretWord.toString();
		if(secr.toLowerCase().contains(c)){
			return secretWord.indexOf(c);
		}
		return -1;
	}
	
	public String getFinalWord(){
		return this.finalWord;
	}
	
	public String getSuperSecretWord(){
		return this.superSecretWord;
	}
	
	public void revealChar(String c){
		int charIndex = checkChar(c);
		if(charIndex>=0){
			hiddenSecretWord.setCharAt(charIndex, c.charAt(0));
			secretWord.setCharAt(charIndex, '*');
			
			if(secretWord.toString().compareTo(finalWord(secretWord.length())) == 0)
				playing = false;
		}
	}
	
	private String finalWord(int x){
		String result= "";
		for(int i=0; i< x; i++)
			result +='*';
		return result;
	}
	
	private void hideSecretWord(String secretWord){
		for(int i=0; i< secretWord.length(); i++)
			hiddenSecretWord.append("_");
	}
	
	public boolean isPlaying(){
		return playing;
	}
	
	public void startGame(){
		this.playing = true;
	}
	
	public String getHiddenSecretWord(){
		return hiddenSecretWord.toString();
	}
	
	public void reset(){
		this.secretWord = new StringBuilder("");
		this.hiddenSecretWord = new StringBuilder("");
		this.numberOfErrors = 0;
		this.insertedChars.clear();
	}

	public int getNumberOfErrors() {
		return numberOfErrors;
	}

	public void setNumberOfErrors(int numberOfErrors) {
		this.numberOfErrors = numberOfErrors;
	}
	
	public void setFinalWord(){
		for(int i=0; i<this.secretWord.toString().length(); i++)
			this.finalWord += "*";
	}
	
	public boolean hasWin(){
		String result = "";
		for(int i=0; i<this.secretWord.toString().length(); i++)
			result += "*";
		if(secretWord.toString().compareTo(result)==0)
			return true;
		else
			return false;
	}
	
	
	public static void main(String[] args) {
		Model m = new Model();
		m.setSecretWord("paratroop");
		System.out.println("s: "+m.getSecretWord());
		System.out.println("h: "+m.getHiddenSecretWord()+"\ne: "+m.getNumberOfErrors());
		try {
			System.out.println("guess: a");
			m.guessChar("a");
			System.out.println("guess: b");
			m.guessChar("b"); //err1
			System.out.println("guess: o");
			m.guessChar("o");
			System.out.println("guess: p");
			m.guessChar("p");
			
			
			System.out.println("guess: x");
			m.guessChar("x"); //err2
			System.out.println("guess: r");
			m.guessChar("r");
			System.out.println("guess: t");
			m.guessChar("t");
			
			
			if(m.isPlaying())
				System.out.println("IN-GAME");
			else
				System.out.println("OFF-GAME");
			
		} catch (RepeatedCharException e) {
			e.printStackTrace();
		}
		System.out.println("h: "+m.getHiddenSecretWord()+"\ne: "+m.getNumberOfErrors());
		
		
	}

	/**
	 * 
	 */
	public void endGame() {
		this.playing = false;
	}
	
	/*
	 * guessChar("a") -> "a" or exception
	 * checkChar("a") -> true/false
	 * if(checkChar)
	 * 		reveal("a");
	 * else numberOfErrors++;
	 * 
	 * 
	 */
	
}
