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

public class StandaradRoom extends Room {
	
	private static final int ONE_BED_PRICE = 59;
	private static final int TWO_BED_PRICE = 99;
	private static final int FOUR_BED_PRICE = 199;
	
	private static final double ONE_BED_LATEPRICE = 79.65;
	private static final double TWO_BED_LATEPRICE = 133.65;
	private static final double FOUR_BED_LATEPRICE = 268.65;
	
	/*
	 * This method validates the user input for new room addition and adds the room
	 * to array list if all the qualifying conditions are met
	 */

	public void validateAndAddStandardRoom(RoomBean bean) throws Exception {
		boolean flag = false;
		String roomID = null;
		RoomDB rdb = new RoomDB();

		int noOfBeds = 0;
		try {
			roomID = bean.getRoom_id().toUpperCase();
			roomID = validateRoomID(roomID);
			if (roomID == null) {
				throw new RoomIDException("Room ID is not valid...Room ID should start with R_");
			}
			bean.setRoom_id(roomID);

			noOfBeds = bean.getNo_of_beds();
			if (!(noOfBeds == 1 || noOfBeds == 2 || noOfBeds == 4)) {
				throw new AddRoomException("Room should have 1, 2 or 4 beds");
			}

			bean.setRoom_status("Available");
			if (bean.getRoom_type().equalsIgnoreCase("suite")) {
				throw new AddRoomException(bean.getRoom_type() + " is incorrect room type for a standard room.");
			}
			bean.setRoom_type("standard");
			flag = true;
		} finally {
			if (flag == true) {
				rdb.addRoom(bean);
			}
		}
	}

	/*
	 * This method validates roomID for the new standard addition and also generates
	 * new roomID if id is not provided Returns roomId for the new standard
	 */
	public String validateRoomID(String IdToCheck) throws RoomIDException {

		String id = null;
		String generatedID = null;
		RoomDB vdb = new RoomDB();
		boolean check = false;
		if (!(IdToCheck.trim().isEmpty()) && (IdToCheck.startsWith("R_"))) {
			check = vdb.checkRoomID(IdToCheck);
			if (check == true) {
				throw new RoomIDException("This Room ID already exsists...please try with another ID\n");
			} else {
				id = IdToCheck;
			}
		} else if (IdToCheck.trim().isEmpty()) {
			generatedID = RoomUtil.generateID("standard");
			id = generatedID;
		}
		return id;
	}

	/*
	 * This method checks for the conditions for the standard to be rented If the
	 * conditions are met, hiring record will be created and status of standard will
	 * be changed to 'Rented'
	 */
	public void rent(String roomID, String customerId, DateTime rentDate, int rentalDays)
			throws SQLException, HiringException {
		boolean flag = false;
		HiringDB hdb = new HiringDB();
		RoomDB rdb = new RoomDB();
		String id = roomID;
		if ((rentalDays <= 10)) {
			String day = rentDate.getNameOfDay();
			if ((day.equalsIgnoreCase("sunday")) || (day.equalsIgnoreCase("saturday"))) {
				if (rentalDays >= 3) {
					flag = true;
				} else {
					throw new HiringException("Rental starting on saturday or sunday should have minimum 3 days.");
				}
			} else if (rentalDays >= 2) {
				flag = true;
			} else {
				throw new HiringException("Minimum 2 days is required for room rental.");
			}
		}
		if (flag == true) {
			HiringRecordBean hBean = new HiringRecordBean();
			String rentalId = id + "_" + customerId.toUpperCase() + "_" + rentDate.getEightDigitDate();
			hBean.setRecordID(rentalId);
			hBean.setRentDate(rentDate);
			DateTime returnDate = new DateTime(rentDate, rentalDays);
			hBean.setEstimatedReturnDate(returnDate);
			hdb.createHiringRecord(hBean);
			rdb.changeStatus(id, "Rented");
		}
	}

	/*
	 * This method performs all the required validation while returning a standard
	 * 
	 * 
	 * NOTE : If the standard is returned after 10 days which is maximum, late fee
	 * would be calculated on extra days as needed.
	 */
	public void returnRoom(String roomID, DateTime returnDate) throws SQLException {
		String id = roomID;
		HiringDB hdb = new HiringDB();
		RoomDB rdb = new RoomDB();
		HiringRecordBean hBean = hdb.getHiringRecord(id);
		int noOfBeds = rdb.getBeds(id);
		double lateFee = 0.0;
		double rentFee = 0.0;
		int dateDif = DateTime.diffDays(returnDate, hBean.getRentDate());
		int actual = DateTime.diffDays(hBean.getEstimatedReturnDate(), hBean.getRentDate());
		int diff = actual - dateDif;
		if ((diff >= 0) && (dateDif <= 10)) {
			if (noOfBeds == 1) {
				rentFee = dateDif * ONE_BED_PRICE;
			} else if (noOfBeds == 2) {
				rentFee = dateDif * TWO_BED_PRICE;
			} else if (noOfBeds == 4) {
				rentFee = dateDif * FOUR_BED_PRICE;
			}
		} else {
			int extraDays = DateTime.diffDays(returnDate, hBean.getEstimatedReturnDate());
			if (noOfBeds == 1) {
				rentFee = dateDif * ONE_BED_PRICE;
				lateFee = extraDays * ONE_BED_LATEPRICE;
			} else if (noOfBeds == 2) {
				rentFee = dateDif * TWO_BED_PRICE;
				lateFee = extraDays * TWO_BED_LATEPRICE;
			} else if (noOfBeds == 4) {
				rentFee = dateDif * FOUR_BED_PRICE;
				lateFee = extraDays * FOUR_BED_LATEPRICE;
			}
		}
		hBean.setActualReturnDate(returnDate);
		hBean.setRentalFee(rentFee);
		hBean.setLateFee(lateFee);
		hdb.updateHiringRecord(hBean);
		(new RoomDB()).changeStatus(id, "Available");
	}

	/*
	 * 
	 * This method is used when maintenance option is chosen for a standard room
	 */
	public void performMaintenance(String roomID) throws SQLException {
		RoomDB rdb = new RoomDB();
		rdb.changeStatus(roomID, "Maintenance");
	}

	/*
	 * 
	 * This method is for completing the Maintenance
	 */
	public void completeMaintenance(String roomID, DateTime completionDate) throws SQLException {
		RoomDB rdb = new RoomDB();
		rdb.changeStatus(roomID, "Available");
		rdb.updateMaintenanceDate(roomID, completionDate);
	}
}