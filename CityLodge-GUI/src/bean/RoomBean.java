package bean;

import util.DateTime;

/*
 * This class contains room details as an object
 */
public class RoomBean {

	protected String room_id;
	private int no_of_beds;
	private String room_type;
	private String room_status;
	private String features;
	protected DateTime lastMaintDate;
	private String imageName;

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getRoom_id() {
		return room_id;
	}

	public void setRoom_id(String room_id) {
		this.room_id = room_id;
	}

	public int getNo_of_beds() {
		return no_of_beds;
	}

	public void setNo_of_beds(int no_of_beds) {
		this.no_of_beds = no_of_beds;
	}

	public String getFeatures() {
		return features;
	}

	public void setFeatures(String features) {
		this.features = features;
	}

	public String getRoom_type() {
		return room_type;
	}

	public void setRoom_type(String room_type) {
		this.room_type = room_type;
	}

	public String getRoom_status() {
		return room_status;
	}

	public void setRoom_status(String room_status) {
		this.room_status = room_status;
	}

	public DateTime getLastMaintDate() {
		return lastMaintDate;
	}

	public void setLastMaintDate(DateTime lastMaintDate) {
		this.lastMaintDate = lastMaintDate;
	}

	/*
	 * toString method to print details of a room when the object is printed
	 */
	public String toString() {
		return returnString();
	}

	private String returnString() {
		String string = getRoom_id() + ":" + getNo_of_beds() + ":" + getFeatures() + ":" + getRoom_type() + ":"
				+ getRoom_status();
		if (getRoom_type().equalsIgnoreCase("suite")) {
			string = string.concat(":" + getLastMaintDate());
		}
		string = string.concat(":" + getImageName());
		return string;
	}

	public String getDetails() {
		String string = "Room ID:\t\t" + getRoom_id() + "\nNumber of beds:\t\t\t" + getNo_of_beds()
				+ "\nFeatures:\t\t\t" + getFeatures() + "\nRoom Type:\t\t\t" + getRoom_type() + "\nRoom Status:\t\t\t"
				+ getRoom_status();
		if (getRoom_type().equals("suite")) {
			string = string.concat("\nLast Maintenance Date:\t" + getLastMaintDate());
		}
		return string;
	}

}
