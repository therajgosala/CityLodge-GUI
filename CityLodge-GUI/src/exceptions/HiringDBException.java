package exceptions;

import java.sql.SQLException; 
	/*
	 * This method is for handling the exceptions during the database operations of hiring record 
	 * 
	 */
	@SuppressWarnings("serial")
	public class HiringDBException extends SQLException{
		public HiringDBException(String msg) {
			super(msg);
		}
	}

