package ca.ualberta.CMPUT301F13T02.chooseyouradventure;

/**
 * This exception is an exception that will be thrown by Handler implementations. 
 * 
 * Catching a Handler exception indicates that the Handler was unable to complete your
 * storage request. Having a single exception classed shared across implementations
 * of Handler helps to create a uniform interaction with Handlers.
 */

public class HandlerException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * This class facilitates our custom Exception
	 * @param Which exception
	 */
	public HandlerException(String what) {
		super(what);
	}
}
