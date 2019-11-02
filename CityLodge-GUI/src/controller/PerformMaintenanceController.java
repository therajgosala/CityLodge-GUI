package controller;

import exceptions.HiringException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import main.CityLodgeApp;

public class PerformMaintenanceController implements EventHandler<ActionEvent>{
	Stage dialogBox;
	private String roomID;
	
	public PerformMaintenanceController(Stage dialogBox, String roomID) {
		this.dialogBox=dialogBox;
		this.roomID=roomID;
	}

	@Override
	public void handle(ActionEvent event) {
		Scene scene = dialogBox.getScene();
		Stage stg=(Stage)scene.getWindow();
	
		try {
		
		
		CityLodgeApp city=new CityLodgeApp();
		city.performMaint(roomID);
		
		Platform.runLater(() -> {
	        Alert dialog = new Alert(AlertType.INFORMATION, "Room is in maintenance now", ButtonType.OK);
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
