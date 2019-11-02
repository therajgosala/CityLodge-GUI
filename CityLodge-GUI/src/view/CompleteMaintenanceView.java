package view;

import controller.CompleteMaintenanceController;
import controller.DialogCancelController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CompleteMaintenanceView {
	public static void completeMaintVehicle(String roomID) {
		Stage dialogBox = new Stage();
		dialogBox.setTitle("Complete amintenance window");

		// dialog components
		Label ridLabel = new Label("Room ID");
		TextField roomIDField = new TextField();
		roomIDField.setDisable(true);
		roomIDField.setStyle("-fx-opacity: 1.0;");
		roomIDField.setPromptText(roomID);
		roomIDField.setId("roomID");

		Label completionLabel = new Label("Completion date");
		TextField completionDateField = new TextField();
		completionDateField.setPromptText("DD/MM/YYYY");
		completionDateField.setId("completionDate");

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
		dialogVBox.getChildren().add(completionLabel);
		dialogVBox.getChildren().add(completionDateField);
		dialogVBox.getChildren().add(dialogButtons);

		Scene dialogScene = new Scene(dialogVBox, 200, 300);
		dialogBox.setScene(dialogScene);
		dialogConfirmButton.setOnAction(new CompleteMaintenanceController(dialogBox, roomID));
		dialogCancelButton.setOnAction(new DialogCancelController(dialogBox));
		dialogBox.show();

	}
}
