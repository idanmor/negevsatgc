/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MissionItems;

import javafx.scene.layout.GridPane;

/**
 *
 * @author Max
 */
public class MissionItemTakeTemperature extends MissionItemStrat{
    
    
    public MissionItemTakeTemperature(GridPane leftPane, GridPane rightPane) {
        super("Take Temperature",leftPane, rightPane);
      
    }

    @Override
    public void execute() {
      addScheduleCalendar();
    }
    
}
