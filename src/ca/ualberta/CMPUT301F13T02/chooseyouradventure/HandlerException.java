package ca.ualberta.CMPUT301F13T02.chooseyouradventure;
/**
 * This is the custom exception handler for our project
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
