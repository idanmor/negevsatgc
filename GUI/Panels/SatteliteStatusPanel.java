package Panels;

import SatteliteData.DataTabPane;
import Utils.Utils;
import javafx.animation.ScaleTransition;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class SatteliteStatusPanel extends PanelsWithClickInterface {
	public SatteliteStatusPanel(BorderPane mainPane) {
		super(mainPane, "sattelite.png");
	}
	
	
	@Override
	public void applyClickOnPanel() {
		new DataTabPane(mainPane);
	}
	
	
}
