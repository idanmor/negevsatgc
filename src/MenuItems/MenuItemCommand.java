/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MenuItems;


import Factories.CommandScreen;
import MissionFrames.MissionSplitFrameIMPL;
import javafx.scene.layout.BorderPane;


/**
 *
 * @author Max
 */
public class MenuItemCommand extends MenuItemStrategy {
  

    public MenuItemCommand() {
        super("Command");
    }

    @Override
    public void applyActionOnGrid(BorderPane mainPane) {
      //  CommandScreen factory = new CommandScreen();
     //   factory.createScreenPane(mainPane);
        MissionSplitFrameIMPL m = new MissionSplitFrameIMPL(mainPane);
        
    }
    
}
