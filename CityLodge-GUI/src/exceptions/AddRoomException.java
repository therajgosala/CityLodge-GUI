package exceptions;

/*
 * This method is for handling the exceptions incurred during adding a new room to the system
 * 
 */
@SuppressWarnings("serial")
public class AddRoomException extends Exception {
	public AddRoomException(String msg) {
		super(msg);
	}
}
