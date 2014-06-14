/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MenuItems;

import Factories.InternalScreenFactory;
import Factories.StatisticsScreeFactory;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author Max
 */
public class MenuItemStatistics extends AbstractMenuItem{

    public MenuItemStatistics() {
        super("Statistics");
    }

    @Override
    public void applyActionOnGrid(BorderPane mainPane) {
        InternalScreenFactory factory = new StatisticsScreeFactory();
        factory.createScreenPane(mainPane);
    }
    
}
