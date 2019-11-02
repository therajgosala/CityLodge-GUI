package view;

import controller.DialogCancelController;
import controller.ReturnRoomController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ReturnRoomView {
	public static void returnRoom(String roomID) {
		Stage dialogBox = new Stage();
		dialogBox.initModality(Modality.APPLICATION_MODAL);
		dialogBox.setTitle("Return room window");

		// dialog components
		Label ridLabel = new Label("Room ID");
		TextField roomIDField = new TextField();
		roomIDField.setDisable(true);
		roomIDField.setStyle("-fx-opacity: 1.0;");
		roomIDField.setPromptText(roomID);
		roomIDField.setId("room_id");

		Label returnLabel = new Label("Return date");
		TextField returnField = new TextField();
		returnField.setPromptText("DD/MM/YYYY");
		returnField.setId("returnDate");

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
		dialogVBox.getChildren().add(returnLabel);
		dialogVBox.getChildren().add(returnField);
		dialogVBox.getChildren().add(dialogButtons);

		Scene dialogScene = new Scene(dialogVBox, 200, 300);
		dialogBox.setScene(dialogScene);
		dialogConfirmButton.setOnAction(new ReturnRoomController(dialogBox, roomID));
		dialogCancelButton.setOnAction(new DialogCancelController(dialogBox));
		dialogBox.showAndWait();

	}
}
