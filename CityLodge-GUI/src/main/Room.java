package main;

import java.sql.SQLException;
import java.util.ArrayList;

import bean.HiringRecordBean;
import bean.RoomBean;
import exceptions.HiringDBException;
import exceptions.HiringException;
import util.DateTime;

public abstract class Room {
	static ArrayList<RoomBean> roomArray = new ArrayList<RoomBean>(50);
	static ArrayList<HiringRecordBean> hiringRecordArray = new ArrayList<HiringRecordBean>();
	RoomBean rBean = new RoomBean();
	HiringRecordBean hBean = new HiringRecordBean();

	public void rent(String roomID, String customerId, DateTime rentDate, int numOfRentDay)
			throws HiringException, HiringDBException, SQLException, HiringException {
	}

	public void returnRoom(String roomID, DateTime returnDate) throws HiringDBException, SQLException {
	}

	public void performMaintenance(String roomID) throws SQLException {
	}

	public void completeMaintenance(String roomID, DateTime completionDate) throws SQLException {
	}

	/*
	 * 
	 * This method performs validation for current date
	 */
	public boolean validateCurrentDate(DateTime cDate) {
		boolean flag = false;
		DateTime tDate = new DateTime();
		int count = DateTime.diffDays(tDate, cDate);
		if (count >= 0) {
			flag = true;
		}
		return flag;
	}
}
