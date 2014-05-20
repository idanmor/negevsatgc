/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MissionItems;

import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

/**
 *
 * @author Max
 */
public class MissionComboBoxWrapper extends ComboBox<Object>implements MissionItemWrapper{

    
    
    public MissionComboBoxWrapper(ObservableList<Object> lst){
        super(lst);
    }
    @Override
    public String getMissionValue() {
        return this.getSelectionModel().getSelectedItem().toString();
    }

    @Override
    public Parent getWrappedItem() {
        return this;
    }
    
      @Override
    public String getValueStringWithPreIfNeeded(Label prefix) {
        if(prefix == null){
            return getMissionValue();
        }
        return prefix.toString().trim() + " " + getMissionValue();
    }
}
