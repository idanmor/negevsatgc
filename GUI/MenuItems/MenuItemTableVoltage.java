package MenuItems;

import Panels.EnergyComponentStatistics;
import javafx.scene.layout.BorderPane;

public class MenuItemTableVoltage extends AbstractMenuItem {

	public MenuItemTableVoltage() {
		super("Components Voltage");
	}

	@Override
	public void applyActionOnGrid(BorderPane mainPane) {
		new EnergyComponentStatistics(mainPane);
		
	}

}
