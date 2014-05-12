package SatteliteData;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class DataTabPane {
	private final int NUM_OF_TABS = 3;
	
	public DataTabPane(BorderPane mainPane){
		Group root = new Group();
		Scene scene = new Scene(root, 400, 250, Color.WHITE);
		TabPane tabPane = new TabPane();
		BorderPane borderPane = new BorderPane();
		for (int i = 0; i < NUM_OF_TABS; i++) {
			Tab tab = new Tab();
			tab.setGraphic(new Circle(0, 0, 10));
			HBox hbox = new HBox();
			hbox.getChildren().add(new Label("Tab" + i));
			hbox.setAlignment(Pos.CENTER);
			tab.setContent(hbox);
			tabPane.getTabs().add(tab);
		}
		// bind to take available space
		borderPane.prefHeightProperty().bind(scene.heightProperty());
		borderPane.prefWidthProperty().bind(scene.widthProperty());

		borderPane.setCenter(tabPane);
		root.getChildren().add(borderPane);

	}
	
	private void initTabs(){
		
	}
	
	private void getDemoTemperatureTabs(){
		
	}
	
}

