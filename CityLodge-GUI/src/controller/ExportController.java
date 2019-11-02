package controller;

import java.io.File;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import services.ExportData;

/*
 * This is the controller when the user selects Export option from the menu.
 */
public class ExportController implements  EventHandler<ActionEvent>{

	@Override
	public void handle(ActionEvent event) {
		Stage stg=new Stage();
		
		try {
		
		stg.setTitle("Exports");
		DirectoryChooser directoryChooser = new DirectoryChooser();
	
		 File selectedDirectory = directoryChooser.showDialog(stg);
		 ExportData.writeToFile(selectedDirectory);
	        
	        stg.show();
	        Platform.runLater(() -> {
		        Alert dialog = new Alert(AlertType.INFORMATION, "Export to file is complete", ButtonType.OK);
		        dialog.show();
		    });
			stg.close();
		}
		catch(Exception e) {
			Platform.runLater(() -> {
		        Alert dialog = new Alert(AlertType.INFORMATION, "No path was selected.", ButtonType.OK);
		        dialog.show();
		    });
			stg.close();
		}
		
	}
	
}
