package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

/*
 * This controller shuts the application.
 */
public class QuitButtonController implements EventHandler<ActionEvent>{
	
	private Stage dialogBox;
	
	public QuitButtonController(Stage dialogBox) {
		this.dialogBox=dialogBox;
	}

	@Override
	public void handle(ActionEvent event) {
		System.exit(0);
		dialogBox.close();		
	}

}
