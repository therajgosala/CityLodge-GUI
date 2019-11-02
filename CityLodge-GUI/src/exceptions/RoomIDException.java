package exceptions;

/*
 * This method is for handling the exceptions pertaining to invalid room Id's
 * 
 */
@SuppressWarnings("serial")
public class RoomIDException extends Exception {

	public RoomIDException(String errMsg) {
		super(errMsg);
		System.out.println(errMsg);
	}

}
