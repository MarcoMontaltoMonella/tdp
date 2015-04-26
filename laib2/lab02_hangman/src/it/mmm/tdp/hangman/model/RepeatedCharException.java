/**
 * 18 Mar 2015, 09:01:53
 */
package it.mmm.tdp.hangman.model;

/**
 * @author Marco_Montalto_Monella
 *
 */
@SuppressWarnings("serial")
public class RepeatedCharException extends Exception{
	
	public RepeatedCharException(){
		super("Char already guessed!");
	}
	
}
