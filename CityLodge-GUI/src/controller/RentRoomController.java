package controller;

import exceptions.HiringException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.CityLodgeApp;
import util.DateTime;


/*
 * This controller is called when the user selects rent room
 */
public class RentRoomController implements EventHandler<ActionEvent>{

	private Stage dialogBox;
	private String roomID;
	
	
	public RentRoomController(Stage dialogBox,String room_id) {
		this.dialogBox=dialogBox;
		this.roomID=room_id;
		
	}
	@Override
	public void handle(ActionEvent event) {
		Scene scene = dialogBox.getScene();
		Stage stg=(Stage)scene.getWindow();
	
		try {
		TextField customerIDField = (TextField) scene.lookup("#customerID");
		TextField rentDateField = (TextField) scene.lookup("#rentDate");
		TextField days = (TextField) scene.lookup("#days");
		
		if(days.getText().trim().isEmpty() || customerIDField.getText().trim().isEmpty()) {
			throw new HiringException("Missing values....please enter all the required fields");
		}
		
		int noOfDays=Integer.parseInt(days.getText());
		String roomID=this.roomID;
		String customerID=customerIDField.getText().toUpperCase();
		String rentDate=rentDateField.getText().trim();
		DateTime rDate = new DateTime(rentDate);
		
		CityLodgeApp city = new CityLodgeApp();
		city.rentRoom(roomID,customerID, rDate, noOfDays);
		
		Platform.runLater(() -> {
	        Alert dialog = new Alert(AlertType.INFORMATION, "Room has been Rented", ButtonType.OK);
	        dialog.show();
	    });
		stg.close();
		}
		catch(HiringException e) {
			e.printStackTrace();
			Platform.runLater(() -> {
		        Alert dialog = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
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
