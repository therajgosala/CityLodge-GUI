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

public class ReturnRoomController implements EventHandler<ActionEvent>{
	Stage dialogBox;
	String roomID;
	
	public ReturnRoomController(Stage dialogBox, String room_id) {
		this.dialogBox=dialogBox;
		this.roomID=room_id;
	}

	@Override
	public void handle(ActionEvent event) {
		Scene scene = dialogBox.getScene();
		Stage stg=(Stage)scene.getWindow();
	
		try {
		TextField returnDateField = (TextField) scene.lookup("#returnDate");		
		if(returnDateField.getText().trim().isEmpty()) {
			throw new HiringException("Please enter the return date");
		}
		String returnDate=returnDateField.getText().trim();
		DateTime rDate = new DateTime(returnDate);
		
		CityLodgeApp city=new CityLodgeApp();
		city.returnRoom(roomID,rDate);
		
		Platform.runLater(() -> {
	        Alert dialog = new Alert(AlertType.INFORMATION, "Room has been returned sucessfully", ButtonType.OK);
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
