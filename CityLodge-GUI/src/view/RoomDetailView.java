package view;

import java.util.ArrayList;
import java.util.List;

import bean.HiringRecordBean;
import bean.RoomBean;
import controller.DialogCancelController;
import controller.ExportController;
import controller.ImportController;
import controller.QuitButtonController;
import dao.HiringDB;
import exceptions.HiringDBException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import util.DateTime;

public class RoomDetailView {

	@SuppressWarnings("unchecked")
	public static void detailView(RoomBean rBean) {
		Stage stg = new Stage();
		Menu m = new Menu("Menu");

		// create menuitems

		MenuItem m1 = new MenuItem("Import to DB");
		MenuItem m2 = new MenuItem("Export to File");
		MenuItem m3 = new MenuItem("Quit");

		// add menu items to menu
		m.getItems().add(m1);
		m.getItems().add(m2);
		m.getItems().add(m3);

		// add event
		m1.setOnAction(new ImportController());
		m2.setOnAction(new ExportController());
		m3.setOnAction(new QuitButtonController(stg));

		// create a menubar
		MenuBar mb = new MenuBar();

		// add menu to menubar
		mb.getMenus().add(m);
		VBox vb = new VBox(mb);

		HiringDB hdb = new HiringDB();
		List<HiringRecordBean> hList = null;
		try {
			hList = hdb.getAllHiringRecords();
		} catch (HiringDBException e) {
			e.printStackTrace();
		}
		List<HiringRecordBean> rTempList = new ArrayList<>();
		for (HiringRecordBean r : hList) {
			if (r.getRecordID().startsWith(rBean.getRoom_id())) {
				rTempList.add(r);
			}
		}
		ObservableList<HiringRecordBean> list = FXCollections.observableArrayList(rTempList);

		// Rental Record column
		TableColumn<HiringRecordBean, String> recordIDColumn = new TableColumn<>("Hiring Record Number");
		recordIDColumn.setMinWidth(200);
		recordIDColumn.setCellValueFactory(new PropertyValueFactory<>("recordID"));

		// Rent Date column
		TableColumn<HiringRecordBean, DateTime> rentDateColumn = new TableColumn<>("Rent Date");
		rentDateColumn.setMinWidth(100);
		rentDateColumn.setCellValueFactory(new PropertyValueFactory<>("rentDate"));

		// Estimated return date column
		TableColumn<HiringRecordBean, DateTime> estimatedReturnDateColumn = new TableColumn<>("Estimated Return Date");
		estimatedReturnDateColumn.setMinWidth(100);
		estimatedReturnDateColumn.setCellValueFactory(new PropertyValueFactory<>("estimatedReturnDate"));

		// Actual return date column
		TableColumn<HiringRecordBean, DateTime> actualReturnDateColumn = new TableColumn<>("Actual Return Date");
		actualReturnDateColumn.setMinWidth(100);
		actualReturnDateColumn.setCellValueFactory(new PropertyValueFactory<>("actualReturnDate"));

		// Rent fee column
		TableColumn<HiringRecordBean, Double> rentFeeColumn = new TableColumn<>("Rent Fee");
		rentFeeColumn.setMinWidth(100);
		rentFeeColumn.setCellValueFactory(new PropertyValueFactory<>("rentalFee"));

		// Late fee column
		TableColumn<HiringRecordBean, Double> lateFeeColumn = new TableColumn<>("Late Fee");
		lateFeeColumn.setMinWidth(100);
		lateFeeColumn.setCellValueFactory(new PropertyValueFactory<>("lateFee"));

		TableView<HiringRecordBean> table = new TableView<>();
		table.setItems(list);
		table.getColumns().addAll(recordIDColumn, rentDateColumn, estimatedReturnDateColumn, actualReturnDateColumn,
				rentFeeColumn, lateFeeColumn);

		VBox vBox = new VBox();
		vBox.getChildren().addAll(table);

		Button rentRoomButton = new Button("Rent Room");
		Button returnRoomButton = new Button("Return Room");
		Button performMaintButton = new Button("Perform Maintenance");
		Button completeMaintButton = new Button("Complete Maintenance");

		Button quitButton = new Button("Back");
		HBox hBox = new HBox();
		hBox.setAlignment(Pos.CENTER);
		if (rBean.getRoom_status().equalsIgnoreCase("Available")) {
			hBox.getChildren().add(rentRoomButton);
			hBox.getChildren().add(performMaintButton);
		} else if (rBean.getRoom_status().equalsIgnoreCase("Rented")) {
			hBox.getChildren().add(returnRoomButton);
		} else {
			hBox.getChildren().add(completeMaintButton);
		}
		hBox.setSpacing(5);
		hBox.getChildren().add(quitButton);

		// rent room
		rentRoomButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				RentRoomView.rentRoom(rBean.getRoom_id());
			}
		});

		// return room
		returnRoomButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				ReturnRoomView.returnRoom(rBean.getRoom_id());
			}
		});

		// performance maintenance
		performMaintButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				PerformMaintenanceView.perfMaintVehicle(rBean.getRoom_id());
			}
		});

		completeMaintButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				CompleteMaintenanceView.completeMaintVehicle(rBean.getRoom_id());
			}
		});

	
		quitButton.setOnAction(new DialogCancelController(stg));

		BorderPane borderPane = new BorderPane();
		borderPane.setBottom(hBox);
		borderPane.setCenter(vBox);
		borderPane.setTop(vb);
		BorderPane.setAlignment(hBox, Pos.CENTER);
		Scene bScene = new Scene(borderPane, 600, 600);

		stg.setTitle("Room Detail Screen"); // Set the stage title
		stg.setScene(bScene); // Place the scene in the stage

		stg.show(); // Display the stage
	}

}
