package controller;

import java.io.File;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.ImportData;

/*
 * This controller will be called when the user selects import option from the menu
 */
public class ImportController implements EventHandler<ActionEvent>{

	@Override
	public void handle(ActionEvent event) {
		Stage stg=new Stage();
		stg.initModality(Modality.APPLICATION_MODAL);
		try {
		
		stg.setTitle("Imports");
		
		FileChooser fileChooser = new FileChooser();
		


		fileChooser.getExtensionFilters().addAll(
		     new FileChooser.ExtensionFilter("Text Files", "*.txt")
		);
		
		File fileChoice = fileChooser.showOpenDialog(stg);

       
           // File selectedFile = fileChooser.showOpenDialog(stg);
            ImportData.consumeFromFile(fileChoice);
            
            
            Platform.runLater(() -> {
    	        Alert dialog = new Alert(AlertType.INFORMATION, "Import to Database is complete", ButtonType.OK);
    	        dialog.show();
    	    });
            stg.show();
    		stg.close();
		}
		
		catch(Exception e) {
			Platform.runLater(() -> {
		        Alert dialog = new Alert(AlertType.INFORMATION, "No file was selected", ButtonType.OK);
		        dialog.show();
		    });
			stg.close();
		}
		
	}

}
