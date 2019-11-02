package exceptions;

/*
 * 
 * This method is for the exceptions during the room maintenance operation
 */
@SuppressWarnings("serial")
public class RoomMaintenanceException extends Exception {
	public RoomMaintenanceException(String errMsg) {
		super(errMsg);
	}
}
