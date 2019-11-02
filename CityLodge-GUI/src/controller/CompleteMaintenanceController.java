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

public class CompleteMaintenanceController implements EventHandler<ActionEvent>{
	Stage dialogBox;
	String room_iD;
	
	public CompleteMaintenanceController(Stage dialogBox, String room_iD) {
		this.dialogBox=dialogBox;
		this.room_iD=room_iD;
	}

	@Override
	public void handle(ActionEvent event) {
		Scene scene = dialogBox.getScene();
		Stage stg=(Stage)scene.getWindow();
	
		try {
		TextField CompleteDateField = (TextField) scene.lookup("#completionDate");		
		if(CompleteDateField.getText().trim().isEmpty()) {
			throw new HiringException("Please provide the completion date");
		}
		String completeDate=CompleteDateField.getText().trim();
		DateTime cDate = new DateTime(completeDate);
		
		CityLodgeApp city = new CityLodgeApp();
		city.completeMaint(room_iD,cDate);
		
		Platform.runLater(() -> {
	        Alert dialog = new Alert(AlertType.INFORMATION, "Room maintenance is complete", ButtonType.OK);
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
