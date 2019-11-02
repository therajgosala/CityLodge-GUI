package main;

import java.io.File;
import java.util.List;

import bean.RoomBean;
import controller.DialogCancelController;
import controller.ExportController;
import controller.ImportController;
import controller.QuitButtonController;
import dao.RoomDB;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import view.AddRoomView;
import view.RoomDetailView;

public class Main extends Application {

	public static void main(String[] args) {
		CityLodgeApp city = new CityLodgeApp();
		try {
			city.startUp();
		} catch (Exception e) {
			e.printStackTrace();
		}
		launch(args);
	}

	static class CustomCell extends ListCell<RoomBean> {
		HBox hbox = new HBox();
		RoomBean lastItem;
		ImageView imgView = new ImageView();

		Label label = new Label("(empty)");
		Pane pane = new Pane();
		Button button = new Button("View Details");

		public CustomCell() {
			super();
			hbox.getChildren().addAll(imgView, label, pane, button);
			HBox.setHgrow(pane, Priority.ALWAYS);
			button.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					RoomDetailView.detailView(lastItem);
				}
			});
		}

		@Override
		protected void updateItem(RoomBean item, boolean empty) {
			super.updateItem(item, empty);
			setText(null);
			if (empty) {
				lastItem = null;
				setGraphic(null);
			} else {
				lastItem = item;
				label.setText(item != null ? item.getDetails() : "<null>");
				imgView.setImage(new Image(new File("Images/" + item.getImageName()).toURI().toString(), 100, 100,
						false, false));
				setGraphic(hbox);
			}
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Stage window = primaryStage;

		// create a menu
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
		m3.setOnAction(new QuitButtonController(window));

		// create a menubar
		MenuBar mb = new MenuBar();

		// add menu to menubar
		mb.getMenus().add(m);
		VBox vb = new VBox(mb);

		Button addRoomButton = new Button("Add Room");
		Button ExitButton = new Button("Exit");
		HBox h1box = new HBox();
		h1box.getChildren().addAll(addRoomButton, ExitButton);
		h1box.setSpacing(20);

		ExitButton.setOnAction(new DialogCancelController(window));
		addRoomButton.setOnAction(new AddRoomView());

		RoomDB rdb = new RoomDB();
		List<RoomBean> vList = rdb.getRooms();

		StackPane pane = new StackPane();

		ObservableList<RoomBean> list = FXCollections.observableArrayList(vList);
		ListView<RoomBean> lv = new ListView<>(list);
		lv.setCellFactory(new Callback<ListView<RoomBean>, ListCell<RoomBean>>() {
			public ListCell<RoomBean> call(ListView<RoomBean> param) {
				return new CustomCell();
			}
		});

		pane.getChildren().add(lv);
		BorderPane borderPane = new BorderPane();
		borderPane.setTop(vb);
		borderPane.setCenter(h1box);
		borderPane.setBottom(pane);
		BorderPane.setAlignment(pane, Pos.CENTER);
		Scene scene = new Scene(borderPane, 600, 600);
		window.setScene(scene);
		window.show();
	}

}
