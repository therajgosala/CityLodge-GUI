package controller;

import bean.RoomBean;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javafx.scene.control.ButtonType;

import javafx.scene.control.TextField;

import javafx.stage.Stage;
import services.StandaradRoom;
import services.Suite;
import util.DateTime;


/*
 * This class is called upon when user clicks confirm for adding a room
 * 
 */
public class AddRoomController implements EventHandler<ActionEvent>{
	private Stage dialogBox;
	
	public AddRoomController(Stage dialogBox) {
		this.dialogBox=dialogBox;
	}

	@Override
	public void handle(ActionEvent event) {
		Scene scene = dialogBox.getScene();
		Stage stg=(Stage)scene.getWindow();
		RoomBean bean = new RoomBean();
		StandaradRoom sroom;
		Suite suite;
		
		TextField room_id = (TextField) scene.lookup("#room_id");
		TextField no_of_beds = (TextField) scene.lookup("#no_of_beds");
		TextField room_type = (TextField) scene.lookup("#room_type");
		TextField room_status = (TextField) scene.lookup("#room_status");
		TextField features = (TextField) scene.lookup("#features");
		TextField lastMaintDate = (TextField) scene.lookup("#lastMaintDate");
		
		try {
		
		bean.setRoom_id(room_id.getText().toUpperCase());
		int no_of_beds1 = Integer.parseInt(no_of_beds.getText());
		bean.setNo_of_beds(no_of_beds1);
		bean.setRoom_type(room_type.getText());
		bean.setRoom_status(room_status.getText());
		bean.setFeatures(features.getText());
		DateTime date = new DateTime(lastMaintDate.getText());
		bean.setLastMaintDate(date);
		
		
		if(features.getText().trim().isEmpty() || room_type.getText().trim().isEmpty() || room_id.getText().trim().isEmpty()|| no_of_beds.getText().trim().isEmpty()
						|| room_status.getText().trim().isEmpty() ) {
			throw new Exception("Missing values....please enter all the values");
		}
		else if(room_type.getText().equalsIgnoreCase("standard")){
			sroom=new StandaradRoom();
			sroom.validateAndAddStandardRoom(bean);
		}
		else if(room_type.getText().equalsIgnoreCase("suite")) {
			if(lastMaintDate.getText().trim().isEmpty()) {
				throw new Exception("Last Maintenance date is missing");
			}
			suite=new Suite();
			suite.validateAndAddSuite(bean);
		}else {
			throw new Exception("Invalid values entered..");
		}
		
		Platform.runLater(() -> {
	        Alert dialog = new Alert(AlertType.INFORMATION, "Room has been succesfully created", ButtonType.OK);
	        dialog.show();
	    });
		stg.close();
		}
		catch(NumberFormatException e) {
			e.printStackTrace();
			Platform.runLater(() -> {
		        Alert dialog = new Alert(AlertType.ERROR, "Required fields are missing", ButtonType.OK);
		        dialog.show();
		    });
			dialogBox.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			Platform.runLater(() -> {
		        Alert dialog = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
		        dialog.show();
		    });
			dialogBox.close();
		}
	}

}
