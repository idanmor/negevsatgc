package Panels;

import SatteliteData.DataTabPane;
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
