package controller;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.stage.Stage;



/*
 * This controller closes the the current window(stage)
 * 
 */
public class DialogCancelController implements EventHandler<ActionEvent>{
		
	private Stage window;
	
	public DialogCancelController(Stage window) {
		this.window = window;
	}
	
	@Override 
	public void handle(ActionEvent e) {
		window.close();
	}
	
}