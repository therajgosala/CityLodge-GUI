package services;

import java.sql.SQLException;

import bean.HiringRecordBean;
import bean.RoomBean;
import dao.HiringDB;
import dao.RoomDB;
import exceptions.AddRoomException;
import exceptions.HiringException;
import exceptions.RoomIDException;
import main.Room;
import util.DateTime;
import util.RoomUtil;

public class Suite extends Room {

	/*
	 * This method validates the user input for new room addition and adds the suite
	 * to array list if all the qualifying conditions are met
	 */

	RoomBean rBean = new RoomBean();
	HiringRecordBean hBean = new HiringRecordBean();

	public void validateAndAddSuite(RoomBean bean) throws AddRoomException, RoomIDException, Exception {
		boolean flag = false;
		String roomID = null;

		RoomDB rdb = new RoomDB();
		try {
			roomID = bean.getRoom_id().toUpperCase();
			roomID = validateSuiteID(roomID);
			if (roomID == null) {
				throw new RoomIDException("Invalid Room ID for suite....suiteId should start with S_");
			}
			bean.setRoom_id(roomID);
			int bed = bean.getNo_of_beds();
			if (bed != 6) {
				throw new AddRoomException("Suite should have 6 beds.");
			}
			bean.setRoom_status("Available");
			flag = true;
		} finally {
			if (flag == true) {
				rdb.addRoom(bean);
			}

		}
	}

	/*
	 * This method validates roomID for the new suite addition and also generates
	 * new roomID if id is not provided, returns roomId for the new suite
	 */
	public String validateSuiteID(String IdToCheck) throws RoomIDException {
		String id = null;
		String generatedID = null;
		boolean check = false;
		RoomDB rdb = new RoomDB();
		if (!(IdToCheck.trim().isEmpty()) && (IdToCheck.startsWith("S_"))) {
			check = rdb.checkRoomID(IdToCheck);
			if (check == true) {
				throw new RoomIDException("This Room ID already exsists...please try with another ID\n");
			} else {
				id = IdToCheck;
			}
		} else if (IdToCheck.trim().isEmpty()) {
			generatedID = RoomUtil.generateID("suite");
			id = generatedID;
		}
		return id;
	}

	/*
	 * This method checks for the conditions for the suite to be rented If the
	 * conditions are met, hiring record will be created and status of suite will be
	 * changed to 'Rented'
	 */
	@Override
	public void rent(String roomID, String customerId, DateTime rentDate, int numOfRentDay)
			throws HiringException, SQLException {

		try {

			HiringDB hdb = new HiringDB();
			RoomDB rdb = new RoomDB();
			DateTime lastMaintDate = rdb.getLastMaintenanceDate(roomID);
			DateTime returnDate = new DateTime(rentDate, numOfRentDay);
			DateTime nextMaintDate = new DateTime(lastMaintDate, 12);
			int dDays = DateTime.diffDays(nextMaintDate, returnDate);
			boolean check = ((dDays >= 1) && (dDays < 12));
			if (check) {
				HiringRecordBean hBean = new HiringRecordBean();
				String rentalId = roomID + "_" + customerId + "_" + rentDate.getEightDigitDate();
				hBean.setRecordID(rentalId);
				hBean.setRentDate(rentDate);
				hBean.setEstimatedReturnDate(returnDate);
				hdb.createHiringRecord(hBean);
				rdb.changeStatus(roomID, "Rented");

			} else {
				throw new HiringException("Suite cannot be rented due to the clashes with its maintenance.");
			}
		} finally {

		}
	}

	/*
	 * This method performs all the required validation while returning a suite
	 * 
	 */
	public void returnRoom(String roomID, DateTime returnDate) throws SQLException {
		int rate = 999;
		int lateRate = 1099;
		String id = roomID;
		HiringDB hdb = new HiringDB();
		HiringRecordBean hBean = hdb.getHiringRecord(id);
		double lateFee = 0.0;
		double rentFee = 0.0;
		int dateDif = DateTime.diffDays(returnDate, hBean.getRentDate());
		int actual = DateTime.diffDays(hBean.getEstimatedReturnDate(), hBean.getRentDate());
		rentFee = dateDif * rate;
		if (dateDif != actual) {
			int extraDays = DateTime.diffDays(returnDate, hBean.getEstimatedReturnDate());
			lateFee = extraDays * lateRate;
			hBean.setLateFee(lateFee);
		}
		hBean.setActualReturnDate(returnDate);
		hBean.setRentalFee(rentFee);
		hdb.updateHiringRecord(hBean);
		(new RoomDB()).changeStatus(id, "Available");
	}

	/*
	 * This method is used when maintenance option is choose for a suite
	 */
	public void performMaintenance(String roomID) throws SQLException {
		RoomDB rdb = new RoomDB();
		rdb.changeStatus(roomID, "Maintenance");
	}

	/*
	 * This method is for completing the Maintenance for suite
	 */
	@Override
	public void completeMaintenance(String roomID, DateTime completionDate) throws SQLException {
		RoomDB rdb = new RoomDB();
		rdb.changeStatus(roomID, "Available");
		rdb.updateMaintenanceDate(roomID, completionDate);
	}

}
