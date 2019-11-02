package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bean.HiringRecordBean;
import exceptions.HiringDBException;
import util.DBUtil;
import util.DateTime;

public class HiringDB {
	/*
	 * This method is used for creating a new hiring record in the database
	 * and returns true or false depending on the DB transaction
	 */
	@SuppressWarnings("finally")
	public boolean createHiringRecord(HiringRecordBean rBean) throws HiringDBException{
		boolean flag=false;
		Connection connection = DBUtil.getDBConnection();
		Long rentDate=rBean.getRentDate().getTime();
		Long estimatedReturnDate=rBean.getEstimatedReturnDate().getTime();
		String query = "insert into hiring_record(recordid,rentdate,estimatedreturndate) values(?,?,?)";
		try {
			PreparedStatement prepare = connection.prepareStatement(query);
			prepare.setString(1, rBean.getRecordID());
			prepare.setLong(2, rentDate);
			prepare.setLong(3, estimatedReturnDate);
			int count = prepare.executeUpdate();
			if (count != 0) {
				flag=true;
			}
		} finally {
		return flag;
		}
	}
	
	/*
	 * This method deletes all the hiring record entries before loading the data
	 */
	public void deleteAllHiring() throws SQLException {
		Connection connection = DBUtil.getDBConnection();
		String query = "delete from hiring_record";
		try {
			Statement stmt = connection.createStatement();
			 stmt.executeUpdate(query);	
		}finally {
		}
	}
	
	/*
	 * This method is used for importing data in to database
	 * 
	 */
	public void importHiringToDB(List<HiringRecordBean> rList) throws SQLException {
		Connection connection = DBUtil.getDBConnection();
		int counter=0;
		int count[]=null;
		String query = "insert into hiring_record(recordid,rentdate,actualreturndate,estimatedreturndate,rentalfee,latefee) values(?,?,?,?,?,?)";
		try {
			PreparedStatement prepare = connection.prepareStatement(query);
			for(HiringRecordBean rBean : rList) {
			prepare.setString(1, rBean.getRecordID());
			Long rentDate=rBean.getRentDate().getTime();
			Long estimatedReturnDate=rBean.getEstimatedReturnDate().getTime();
			Long actualDate=rBean.getActualReturnDate().getTime();
			prepare.setLong(3, actualDate);
			prepare.setLong(2, rentDate);
			prepare.setLong(4, estimatedReturnDate);	
			prepare.setDouble(5, rBean.getRentalFee());
			prepare.setDouble(6, rBean.getLateFee());
			prepare.addBatch();
			}
			count = prepare.executeBatch();
			for(Integer i : count) {
				if(i!=0)
				counter++;
			}
			
		} finally {
			if(counter<count.length) {
				throw new SQLException(counter + " failed to be added in rental records.");
			}
		}
	}
	
	/*
	 * This methods updates the hiring record after the room has been returned
	 * 
	 */
	@SuppressWarnings("finally")
	public boolean updateHiringRecord(HiringRecordBean rBean) throws HiringDBException{
		boolean flag=false;
		Long actualReturnDate=rBean.getActualReturnDate().getTime();
		Connection connection = DBUtil.getDBConnection();
		String query = "update hiring_record set actualReturnDate=?,rentalFee=?,lateFee=? where recordid=?";
		try {
			PreparedStatement prepare = connection.prepareStatement(query);
			prepare.setLong(1, actualReturnDate);
			prepare.setDouble(2, rBean.getRentalFee());
			prepare.setDouble(3, rBean.getLateFee());
			prepare.setString(4, rBean.getRecordID());
			int count = prepare.executeUpdate();
			if (count == 0) {
				throw new HiringDBException("Hiring record was updated in the database.");
			}
		} finally {
		return flag;
		}
	}
	
	/*
	 * This method returns the latest recordID based on room id
	 */
	@SuppressWarnings("finally")
	public HiringRecordBean getHiringRecord(String id) throws HiringDBException{
		HiringRecordBean rBean=null;
		Connection connection = DBUtil.getDBConnection();
		String query = "select recordid,rentDate,actualReturnDate,estimatedReturnDate from hiring_record";
		try {
			Statement prepare = connection.createStatement();
			ResultSet res = prepare.executeQuery(query);
			while (res.next()) {
				String rid=res.getString(1);
				if(rid.contains(id)) {
					rBean=new HiringRecordBean();
					rBean.setRecordID(rid);
					DateTime actualReturnDate=new DateTime(res.getLong(3));
					DateTime estimatedReturnDate=new DateTime(res.getLong(4));
					DateTime rentDate=new DateTime(res.getLong(2));
					rBean.setActualReturnDate(actualReturnDate);
					rBean.setEstimatedReturnDate(estimatedReturnDate);
					rBean.setRentDate(rentDate);
				}
			}
		}finally {
		return rBean;
		}
	}
	
	
	/*
	 * This method retrieves all the hiring records and returns it as a list
	 * 
	 */
	@SuppressWarnings("finally")
	public List<HiringRecordBean> getAllHiringRecords() throws HiringDBException{
		List<HiringRecordBean> rList=new ArrayList<>();
		HiringRecordBean rBean=null;
		Connection connection = DBUtil.getDBConnection();
		String query = "select * from hiring_record order by rentdate desc";
		try {
			Statement prepare = connection.createStatement();

			ResultSet res = prepare.executeQuery(query);

			while (res.next()) {
				rBean=new HiringRecordBean();
				rBean.setRecordID(res.getString("recordid"));
				DateTime rentDate=new DateTime(res.getLong(2));
				DateTime actualReturnDate=new DateTime(res.getLong(3));
				DateTime estimatedReturnDate=new DateTime(res.getLong(4));
				rBean.setRentDate(rentDate);
				rBean.setEstimatedReturnDate(estimatedReturnDate);
				rBean.setActualReturnDate(actualReturnDate);
				rBean.setRentalFee(res.getDouble("rentalfee"));
				rBean.setLateFee(res.getDouble("latefee"));						
				rList.add(rBean);
			}
		} finally {
		return rList;
		}
	}
}
