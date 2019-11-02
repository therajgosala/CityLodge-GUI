package services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import bean.HiringRecordBean;
import bean.RoomBean;
import dao.HiringDB;
import dao.RoomDB;
import util.DateTime;

/*
 * 
 * This class is used for adding the data in to the data base
 */
public class ImportData {

	public static void consumeFromFile(File file) throws Exception {
		HiringDB hdb;
		RoomDB rdb;
		DateTime date = null;
		HiringRecordBean hBean;
		RoomBean rBean;
		rdb = new RoomDB();
		hdb = new HiringDB();
		List<RoomBean> roomList = rdb.getRooms();

		if (roomList.size() > 1) {
			rdb.deleteAllRooms();
			hdb.deleteAllHiring();
		}

		Double fee = 0.0;
		Double lFee = 0.0;
		DateTime arDate = null;
		List<RoomBean> vList = new ArrayList<>();
		List<HiringRecordBean> rList = new ArrayList<>();
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
			String line = bufferedReader.readLine();
			while (line != null) {
				String temp[] = line.split(":");
				String temp1[] = temp[0].split("_");
				if (temp1.length == 4) {
					hBean = new HiringRecordBean();
					hBean.setRecordID(temp[0]);
					DateTime rDate = new DateTime(temp[1]);
					hBean.setRentDate(rDate);
					DateTime eDate = new DateTime(temp[2]);
					hBean.setEstimatedReturnDate(eDate);
					if (!temp[3].equals("none")) {
						arDate = new DateTime(temp[3]);
						hBean.setActualReturnDate(arDate);
						fee = Double.parseDouble(temp[4]);
						lFee = Double.parseDouble(temp[5]);
						hBean.setRentalFee(fee);
						hBean.setLateFee(lFee);
					} else {
						hBean.setActualReturnDate(new DateTime(0L));
						hBean.setRentalFee(fee);
						hBean.setLateFee(lFee);
					}
					rList.add(hBean);
				} else {
					rBean = new RoomBean();
					rBean.setRoom_id(temp[0]);
					int bed = Integer.parseInt(temp[1]);
					rBean.setNo_of_beds(bed);
					rBean.setFeatures(temp[2]);
					rBean.setRoom_type(temp[3]);
					rBean.setRoom_status(temp[4]);
					if (temp.length == 7) {
						rBean.setRoom_type("Suite");
						date = new DateTime(temp[5]);
						rBean.setLastMaintDate(date);
						rBean.setImageName(temp[6]);
					} else {
						rBean.setRoom_type("Standard");
						rBean.setImageName(temp[5]);
						date = new DateTime(0L);
						rBean.setLastMaintDate(date);
					}
					vList.add(rBean);
				}
				line = bufferedReader.readLine();
			}
			rdb.importTODB(vList);
			hdb.importHiringToDB(rList);

		}
	}

}
