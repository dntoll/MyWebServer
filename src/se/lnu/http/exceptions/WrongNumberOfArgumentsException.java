package se.lnu.http.exceptions;

public class WrongNumberOfArgumentsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7327975009047813601L;

	public WrongNumberOfArgumentsException() {
		super("This program should only have one or two arguments");
	}

	

}
