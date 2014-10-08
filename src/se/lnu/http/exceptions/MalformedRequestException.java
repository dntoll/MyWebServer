package se.lnu.http.exceptions;

public class MalformedRequestException extends Exception {

	public MalformedRequestException(String requestString) {
		super(requestString);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 347832735672941517L;

}
