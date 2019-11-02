package view;

import controller.AddRoomController;
import controller.DialogCancelController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/*
 * This class is used to accept the details of a new room
 * 
 */
public class AddRoomView implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {

		Stage dialogBox = new Stage();
		dialogBox.initModality(Modality.APPLICATION_MODAL);
		dialogBox.setTitle("Room entry dialog box");

		// dialog components
		Label ridLabel = new Label("Room ID");
		TextField roomIDField = new TextField();
		roomIDField.setPromptText("Room ID");
		roomIDField.setId("room_id");
		
		Label numBedsLabel = new Label("Number of Beds");
		TextField numBedsField = new TextField();
		numBedsField.setPromptText("1, 2 or 4 for standard room | 6 for suite");
		numBedsField.setId("no_of_beds");
		
		Label rTypeLabel = new Label("Room Type");
		TextField rTypeField = new TextField();
		rTypeField.setPromptText("Standard or Suite");
		rTypeField.setId("room_type");

		Label rStatusLabel = new Label("Room Status");
		TextField rStatusField = new TextField();
		rStatusField.setPromptText("Available, Rented or Maintenance");
		rStatusField.setId("room_status");
		
		Label featuresLabel = new Label("Features");
		TextField featuresField = new TextField();
		featuresField.setPromptText("Features");
		featuresField.setId("features");

		Label lastLabel = new Label("Last Maintenance date");
		TextField lastMaintDateField = new TextField();
		lastMaintDateField.setPromptText("DD/MM/YYYY");
		lastMaintDateField.setId("lastMaintDate");
		//lastMaintDateField.setPromptText("Optional");

		Button dialogOKButton = new Button("OK");
		Button dialogCancelButton = new Button("Cancel");

		HBox dialogButtons = new HBox();
		dialogButtons.getChildren().add(dialogCancelButton);
		dialogButtons.getChildren().add(dialogOKButton);
		dialogButtons.setAlignment(Pos.CENTER);

		// layout the dialog components
		VBox dialogVBox = new VBox();
		dialogVBox.getChildren().add(ridLabel);
		dialogVBox.getChildren().add(roomIDField);
		dialogVBox.getChildren().add(numBedsLabel);
		dialogVBox.getChildren().add(numBedsField);
		dialogVBox.getChildren().add(rTypeLabel);
		dialogVBox.getChildren().add(rTypeField);
		dialogVBox.getChildren().add(rStatusLabel);
		dialogVBox.getChildren().add(rStatusField);
		dialogVBox.getChildren().add(featuresLabel);
		dialogVBox.getChildren().add(featuresField);
		dialogVBox.getChildren().add(lastLabel);
		dialogVBox.getChildren().add(lastMaintDateField);
		dialogVBox.getChildren().add(dialogButtons);

		Scene dialogScene = new Scene(dialogVBox, 400, 600);
		dialogBox.setScene(dialogScene);
		dialogOKButton.setOnAction(new AddRoomController(dialogBox));
		dialogCancelButton.setOnAction(new DialogCancelController(dialogBox));
		dialogBox.showAndWait();

	}
}