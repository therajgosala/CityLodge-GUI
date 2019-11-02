package main;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

import bean.RoomBean;
import dao.RoomDB;
import exceptions.HiringDBException;
import exceptions.HiringException;
import services.ImportData;
import services.StandaradRoom;
import services.Suite;
import util.DateTime;

public class CityLodgeApp {

	public void startUp() throws Exception {
		RoomDB rdb = new RoomDB();
		List<RoomBean> rList = rdb.getRooms();
		if (rList.size() == 0) {
			ImportData.consumeFromFile(new File("resources/export_data.txt"));
		}
	}

	/*
	 * This method accepts input from user for rent functionality
	 * 
	 * 
	 */
	public void rentRoom(String roomID, String customerID, DateTime rentDate, int noOfDays)
			throws HiringException, SQLException {
		StandaradRoom sroom;
		Suite suite;
		RoomDB rdb = new RoomDB();
		try {
			boolean check = rdb.checkRoomID(roomID);
			if (check == false) {
				throw new HiringException(roomID + " does not exsist!!\n\nPlease enter a valid Room ID");
			}
			boolean statusCheck = ((rdb.getRoomStatus(roomID)).equals("Available"));
			if (check && statusCheck) {
				if (customerID.trim().equals(null)) {
					throw new HiringException("Customer ID cannot be empty.");
				}
				if (roomID.contains("R_")) {
					sroom = new StandaradRoom();
					sroom.rent(roomID, customerID, rentDate, noOfDays);
				} else {
					suite = new Suite();
					suite.rent(roomID, customerID, rentDate, noOfDays);
				}
			}
		} finally {

		}
	}

	/*
	 * 
	 * This method accepts user input for returning a room
	 */
	public void returnRoom(String roomID, DateTime returnDate) throws SQLException {

		boolean flag = false;
		Suite suite;
		StandaradRoom sroom;
		String id = roomID;
		RoomDB rdb = new RoomDB();
		try {
			flag = rdb.checkRoomID(roomID);
			if (flag == false) {
				throw new HiringDBException("Invalid room ID entered for performing return");
			}
			String status = rdb.getRoomStatus(id);
			if ((status.equals("Rented"))) {
				if (roomID.contains("R_")) {
					sroom = new StandaradRoom();
					sroom.returnRoom(roomID, returnDate);
				} else if (roomID.contains("S_")) {
					suite = new Suite();
					suite.returnRoom(roomID, returnDate);
				}
			} else {
				throw new HiringDBException(roomID + " return did not happen\n");
			}
		} finally {
		}
	}

	/*
	 * This method accepts user input for performing maintenance and validates the
	 * id
	 */
	public void performMaint(String roomID) throws Exception {
		String id = roomID;
		StandaradRoom sroom;
		Suite suite;
		RoomDB rdb = new RoomDB();
		try {
			boolean flag = rdb.checkRoomID(id);
			if (flag == false) {
				throw new Exception("Invalid room ID entered for performing maintenance");
			}
			if ((rdb.getRoomStatus(id).equals("Available"))) {
				if (id.contains("R_")) {
					sroom = new StandaradRoom();
					sroom.performMaintenance(id);
				} else {
					suite = new Suite();
					suite.performMaintenance(id);
				}
			}
		} finally {
		}
	}

	/*
	 * This method used to accept input for complete maintenance functionality It
	 * also validates for invalid room id provided by user
	 * 
	 */
	public void completeMaint(String roomID, DateTime cDate) throws Exception {
		StandaradRoom sroom;
		Suite suite;
		String id = roomID;
		RoomDB rdb = new RoomDB();
		try {
			boolean flag = rdb.checkRoomID(id);
			if (flag == false) {
				throw new Exception("Invalid room ID entered for completing maintenance");
			}
			if ((rdb.getRoomStatus(id).equals("Maintenance"))) {
				if (id.contains("R_")) {
					sroom = new StandaradRoom();
					sroom.completeMaintenance(roomID, cDate);
				} else {
					suite = new Suite();
					suite.completeMaintenance(roomID, cDate);
				}
			}
		} finally {

		}
	}

}
