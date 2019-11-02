package util;

import java.text.SimpleDateFormat;

public class RoomUtil {
	/*
	 * This static method is to generate roomID for new standard room or suite.
	 */
	public static String generateID(String roomType) {
		String ID = null;
		Long var = null;
		if (roomType == "Standard") {
			ID = "R_";
		} else {
			ID = "S_";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("hmsS");
		var = System.currentTimeMillis();
		String addVar = sdf.format(var);
		ID = ID.concat(addVar);
		return ID;
	}
	
}
