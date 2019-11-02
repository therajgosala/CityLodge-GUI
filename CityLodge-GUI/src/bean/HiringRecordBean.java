package bean;

import util.DateTime;

/*
 * This class contains the Hiring records as a object
 */
public class HiringRecordBean {

	private String recordID;
	private DateTime rentDate;
	private DateTime estimatedReturnDate;
	private DateTime actualReturnDate;
	private double rentalFee;
	private double lateFee;

	public String getRecordID() {
		return recordID;
	}

	public void setRecordID(String recordID) {
		this.recordID = recordID;
	}

	public DateTime getRentDate() {
		return rentDate;
	}

	public void setRentDate(DateTime rentDate) {
		this.rentDate = rentDate;
	}

	public DateTime getEstimatedReturnDate() {
		return estimatedReturnDate;
	}

	public void setEstimatedReturnDate(DateTime estimatedReturnDate) {
		this.estimatedReturnDate = estimatedReturnDate;
	}

	public DateTime getActualReturnDate() {
		return actualReturnDate;
	}

	public void setActualReturnDate(DateTime actualReturnDate) {
		this.actualReturnDate = actualReturnDate;
	}

	public double getRentalFee() {
		return rentalFee;
	}

	public void setRentalFee(double rentalFee) {
		this.rentalFee = rentalFee;
	}

	public double getLateFee() {
		return lateFee;
	}

	public void setLateFee(double lateFee) {
		this.lateFee = lateFee;
	}

	public String toString() {
		return returnString();
	}

	public String returnString() {
		String string = null;
		if (getRentalFee() == 0.0) {
			string = getRecordID() + ":" + getRentDate() + ":" + getEstimatedReturnDate() + ":none:none:none";
		} else {
			string = getRecordID() + ":" + getRentDate() + ":" + getEstimatedReturnDate() + ":" + getActualReturnDate()
					+ ":" + getRentalFee() + ":" + getLateFee();
		}
		return string;
	}

	public String getDetails() {
		String string = "\nRecord ID:\t\t" + getRecordID() + "\nRent date:\t\t" + getRentDate()
				+ "\nEstimated return date:\t" + getEstimatedReturnDate();
		if (getRentalFee() != 0.0) {
			string = string.concat("\nActual return date:\t" + getActualReturnDate() + "\nRent fee:\t\t"
					+ getRentalFee() + "\nLate fee:\t\t" + getLateFee());
		}
		return string;
	}
}