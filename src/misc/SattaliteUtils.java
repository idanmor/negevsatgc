/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package misc;

import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author Max
 */
public class SattaliteUtils {
    
    public  static VBox getVbox(){
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(15, 12, 15, 12));
        return vbox;
    }
    
    public static HBox getHBox(){
        HBox box = new HBox();
        box.setPadding(new Insets(15, 12, 15, 12));
        box.setSpacing(10);
        return box;
    }
    
    
}
