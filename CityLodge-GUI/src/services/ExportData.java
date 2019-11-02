package services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import bean.HiringRecordBean;
import bean.RoomBean;
import dao.HiringDB;
import dao.RoomDB;

public class ExportData {
	public static void writeToFile(File file) throws IOException, Exception {
		RoomDB rdb = new RoomDB();
		HiringDB hdb = new HiringDB();
		List<RoomBean> rList = rdb.getRooms();
		List<HiringRecordBean> vList = hdb.getAllHiringRecords();
		String fileName = "/export_data.txt";
		String filePath = file.getPath().concat(fileName);

		BufferedWriter bw = null;
		FileWriter fw = null;

		fw = new FileWriter(filePath, false);
		bw = new BufferedWriter(fw);
		try {
			for (RoomBean rBean : rList) {
				String line = rBean.toString();
				bw.write(line + "\n");
				for (HiringRecordBean hBean : vList) {
					String rLine = hBean.toString();
					if (hBean.getRecordID().startsWith(rBean.getRoom_id())) {
						bw.write(rLine + "\n");
					}
				}
				bw.flush();
			}
		} finally {
			if (bw != null)
				bw.close();

			if (fw != null)
				fw.close();
		}

	}
}
