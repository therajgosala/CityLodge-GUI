package view;

import controller.DialogCancelController;
import controller.PerformMaintenanceController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PerformMaintenanceView {
	public static void perfMaintVehicle(String roomID) {
		Stage dialogBox = new Stage();
		dialogBox.initModality(Modality.APPLICATION_MODAL);
		dialogBox.setTitle("Room Maintenance window");

		// dialog components
		Label ridLabel = new Label("Room ID");
		TextField roomIDField = new TextField();
		roomIDField.setDisable(true);
		roomIDField.setStyle("-fx-opacity: 1.0;");
		roomIDField.setPromptText(roomID);
		roomIDField.setId("roomID");

		Button dialogConfirmButton = new Button("Confirm");
		Button dialogCancelButton = new Button("Cancel");

		HBox dialogButtons = new HBox();
		dialogButtons.setPadding(new Insets(10, 10, 10, 10));
		dialogButtons.getChildren().add(dialogCancelButton);
		dialogButtons.getChildren().add(dialogConfirmButton);
		dialogButtons.setAlignment(Pos.CENTER);

		// layout the dialog components
		VBox dialogVBox = new VBox();
		dialogVBox.getChildren().add(ridLabel);
		dialogVBox.getChildren().add(roomIDField);
		dialogVBox.getChildren().add(dialogButtons);

		Scene dialogScene = new Scene(dialogVBox, 200, 300);
		dialogBox.setScene(dialogScene);
		dialogConfirmButton.setOnAction(new PerformMaintenanceController(dialogBox, roomID));
		dialogCancelButton.setOnAction(new DialogCancelController(dialogBox));
		dialogBox.showAndWait();

	}
}
