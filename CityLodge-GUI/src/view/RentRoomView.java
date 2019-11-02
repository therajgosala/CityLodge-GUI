package view;

import controller.DialogCancelController;
import controller.RentRoomController;
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

public class RentRoomView implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {

	}

	public static void rentRoom(String roomID) {
		Stage dialogBox = new Stage();
		dialogBox.initModality(Modality.APPLICATION_MODAL);
		dialogBox.setTitle("Rent room window");

		// dialog components
		Label ridLabel = new Label("Room ID");
		TextField roomIDField = new TextField();
		roomIDField.setDisable(true);
		roomIDField.setStyle("-fx-opacity: 1.0;");
		roomIDField.setPromptText(roomID);
		roomIDField.setId("roomID");

		Label cIdLabel = new Label("Customer ID");
		TextField customerIDField = new TextField();
		customerIDField.setPromptText("Required");
		customerIDField.setId("customerID");

		Label rentLabel = new Label("Rent date");
		TextField rentDateField = new TextField();
		rentDateField.setPromptText("DD/MM/YYYY");
		rentDateField.setId("rentDate");

		Label daysLabel = new Label("Number of days");
		TextField daysField = new TextField();
		daysField.setId("days");

		Button dialogConfirmButton = new Button("Confirm");
		Button dialogCancelButton = new Button("Cancel");

		HBox dialogButtons = new HBox();
		dialogButtons.getChildren().add(dialogCancelButton);
		dialogButtons.getChildren().add(dialogConfirmButton);
		dialogButtons.setAlignment(Pos.CENTER);

		// layout the dialog components
		VBox dialogVBox = new VBox();
		dialogVBox.getChildren().add(ridLabel);
		dialogVBox.getChildren().add(roomIDField);
		dialogVBox.getChildren().add(cIdLabel);
		dialogVBox.getChildren().add(customerIDField);
		dialogVBox.getChildren().add(rentLabel);
		dialogVBox.getChildren().add(rentDateField);
		dialogVBox.getChildren().add(daysLabel);
		dialogVBox.getChildren().add(daysField);
		dialogVBox.getChildren().add(dialogButtons);

		Scene dialogScene = new Scene(dialogVBox, 200, 300);
		dialogBox.setScene(dialogScene);
		dialogConfirmButton.setOnAction(new RentRoomController(dialogBox, roomID));
		dialogCancelButton.setOnAction(new DialogCancelController(dialogBox));
		dialogBox.showAndWait();

	}

}
