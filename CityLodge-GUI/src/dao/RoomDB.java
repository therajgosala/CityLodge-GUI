package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bean.RoomBean;
import util.DBUtil;
import util.DateTime;

public class RoomDB {
	/*
	 * This method is used for adding a single room in to the database and returns
	 * true or false depending on the DB transaction
	 */
	@SuppressWarnings("finally")
	public boolean addRoom(RoomBean sBean) throws SQLException {
		boolean flag = false;
		Connection connection = DBUtil.getDBConnection();
		String query = "insert into room(room_id,no_of_beds,room_type,room_status,features,imageName,lastmaintdate) values(?,?,?,?,?,?,?)";
		try {
			PreparedStatement prepare = connection.prepareStatement(query);
			prepare.setString(1, sBean.getRoom_id());
			prepare.setInt(2, sBean.getNo_of_beds());
			prepare.setString(5, sBean.getFeatures());
			prepare.setString(3, sBean.getRoom_type());
			prepare.setString(4, sBean.getRoom_status());
			Long date = sBean.getLastMaintDate().getTime();
			prepare.setLong(6, date);
			prepare.setString(7, sBean.getImageName());
			int count = prepare.executeUpdate();
			if (count != 0) {
				flag = true;
			}
		} finally {
			return flag;
		}
	}

	/*
	 * This method is used to check the roomID in DB returns true or false based on
	 * the availability
	 * 
	 */
	public boolean checkRoomID(String id) {
		boolean flag = false;
		Connection connection = DBUtil.getDBConnection();
		String query = "select * from room where room_id=?";
		try {
			PreparedStatement prepare = connection.prepareStatement(query);
			prepare.setString(1, id);
			ResultSet result = prepare.executeQuery();
			while (result.next()) {
				if ((result.getString("room_id")).equalsIgnoreCase(id)) {
					flag = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

	/*
	 * This method returns the current status of the room
	 */
	public String getRoomStatus(String id) {
		String status = null;
		Connection connection = DBUtil.getDBConnection();
		String query = "select room_status from room where room_id=?";
		try {
			PreparedStatement prepare = connection.prepareStatement(query);
			prepare.setString(1, id);
			ResultSet result = prepare.executeQuery();
			if (result.next()) {
				status = result.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return status;
	}

	/*
	 * This method is used to change the status of particular room ID in room array
	 */

	public void changeStatus(String id, String status) throws SQLException {

		Connection connection = DBUtil.getDBConnection();
		String query = "update room set room_status=? where room_id=?";
		try {
			PreparedStatement prepare = connection.prepareStatement(query);
			prepare.setString(1, status);
			prepare.setString(2, id);
			prepare.executeUpdate();

		} finally {
		}
	}

	/*
	 * This method deletes all the room entries before loading the data
	 */
	public void deleteAllRooms() throws SQLException {
		Connection connection = DBUtil.getDBConnection();
		String query = "delete from room";
		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(query);
		} finally {
		}
	}

	/*
	 * 
	 * This method returns the number of beds for room
	 */
	public int getBeds(String id) {
		int beds = 0;
		Connection connection = DBUtil.getDBConnection();
		String query = "select no_of_beds from room  where room_id=?";
		try {
			PreparedStatement prepare = connection.prepareStatement(query);
			prepare.setString(1, id);
			ResultSet result = prepare.executeQuery();
			if (result.next()) {
				beds = result.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return beds;
	}

	/*
	 * This method is used to update the last maintenance day during
	 * completeMaintenance
	 */
	public boolean updateMaintenanceDate(String id, DateTime date) {
		boolean flag = false;
		Connection connection = DBUtil.getDBConnection();
		String query = "update room set lastMaintDate=? where room_id=?";
		try {
			PreparedStatement prepare = connection.prepareStatement(query);
			Long mDate = date.getTime();
			prepare.setLong(1, mDate);
			prepare.setString(2, id);
			int count = prepare.executeUpdate();
			if (count != 0) {
				flag = true;
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return flag;
	}

	/*
	 * This method is used to return the list of all rooms in the database
	 */
	@SuppressWarnings("finally")
	public List<RoomBean> getRooms() throws Exception {
		List<RoomBean> aList = new ArrayList<>();
		Connection connection = DBUtil.getDBConnection();
		RoomBean sBean = null;
		String query = "select * from room";
		try {
			Statement prepare = connection.createStatement();

			ResultSet res = prepare.executeQuery(query);

			while (res.next()) {
				sBean = new RoomBean();
				sBean.setRoom_id(res.getString("room_id"));
				sBean.setNo_of_beds(res.getInt("no_of_beds"));
				sBean.setFeatures(res.getString("features"));
				sBean.setRoom_type(res.getString("room_type"));
				sBean.setRoom_status(res.getString("room_status"));
				DateTime mDate = new DateTime(res.getLong("lastmaintdate"));
				sBean.setLastMaintDate(mDate);
				sBean.setImageName(res.getString("imageName"));
				aList.add(sBean);
			}
		} finally {
			return aList;
		}
	}

	public DateTime getLastMaintenanceDate(String id) {
		DateTime date = null;
		Connection connection = DBUtil.getDBConnection();
		String query = "select lastmaintdate from room  where room_id=?";
		try {
			PreparedStatement prepare = connection.prepareStatement(query);
			prepare.setString(1, id);
			ResultSet result = prepare.executeQuery();
			if (result.next()) {
				date = new DateTime(result.getLong(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return date;
	}

	public void importTODB(List<RoomBean> list) throws Exception {
		int counter = 0;
		Connection connection = DBUtil.getDBConnection();
		int count[] = null;
		String query = "insert into room(room_id,no_of_beds,room_type,room_status,features,imageName,lastMaintDate) values(?,?,?,?,?,?,?)";
		try {
			PreparedStatement prepare = connection.prepareStatement(query);
			for (RoomBean room : list) {
				prepare.setString(1, room.getRoom_id());
				prepare.setInt(2, room.getNo_of_beds());
				prepare.setString(5, room.getFeatures());
				prepare.setString(3, room.getRoom_type());
				prepare.setString(4, room.getRoom_status());
				Long date = room.getLastMaintDate().getTime();
				prepare.setLong(7, date);
				prepare.setString(6, room.getImageName());
				prepare.addBatch();
			}
			count = prepare.executeBatch();
			for (Integer i : count) {
				if (i != 0) {
					counter++;
				}
			}

		}

		finally {
			if (counter < count.length)
				throw new Exception(counter + " records failed to be added in room");
		}

	}
}