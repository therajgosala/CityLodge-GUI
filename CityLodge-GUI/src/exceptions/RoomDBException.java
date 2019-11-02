package exceptions;

import java.sql.SQLException;

/*
 * This method is for handling the exceptions pertaining to room DB operations
 * 
 */
@SuppressWarnings("serial")
public class RoomDBException extends SQLException {
	public RoomDBException(String errMsg) {
		super(errMsg);
		System.out.println(errMsg);
	}
}
