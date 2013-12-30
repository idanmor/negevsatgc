/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MissionItems;

import java.util.List;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import webmap.WebMap;

/**
 *
 * @author Max
 */
public class MissionItemsVisiblity extends MissionItemStrat{
   

    public MissionItemsVisiblity(String name,List<Node> list) {
        super(name,list);
    }

    @Override
    public void execute() {
        for(Node n: list){
            n.setVisible(true);
        }
        WebMap map = new WebMap();
        map.start();
    }

   
    
}
