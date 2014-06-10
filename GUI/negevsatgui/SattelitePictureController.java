/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package negevsatgui;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Utils.Utils;
import data.DataManager;
import data.Status;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.util.Pair;

/**
 *
 * @author Max
 */
public class SattelitePictureController implements Initializable {
    
    @FXML
    private ImageView ImageViewSunSensor;
    @FXML
    private Label labelSunSensor;
    @FXML
    private Label labelMomentumWheel;
    @FXML
    private ImageView ImageViewMomentum;
    @FXML
    private Label LabelSBand;
    @FXML
    private ImageView ImageViewSBand;
    @FXML
    private Group groupPayload;
    @FXML
    private ImageView ImageViewPayload;
    @FXML
    private Label LabelPayload;
    @FXML
    private Group groupPayload1;
    @FXML
    private ImageView ImageViewComputer;
    @FXML
    private Label labelComputer;
    @FXML
    private Group groupSP;
    @FXML
    private ImageView ImageViewSP;
    @FXML
    private Label labelSP;
    @FXML
    private Group groupPDU;
    @FXML
    private ImageView ImageViewPDU;
    @FXML
    private Label labelPDU;
    @FXML
    private Group groupRadio;
    @FXML
    private ImageView ImageViewRadio;
    @FXML
    private Label labelRadio;
    @FXML
    private Group groupBattery;
    @FXML
    private ImageView ImageViewBattery;
    @FXML
    private Label labelBattery;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//      DataManager db = DataManager.getInstance();
//      final long monthInMS = 26280000;//need to mult by 100
//      Timestamp oldestTS=new Timestamp(System.currentTimeMillis() - monthInMS * 100);
//	    Timestamp TS=new Timestamp(System.currentTimeMillis());
//      ArrayList<Pair<String, Status>> statusPairs = db.getListOfStatusPairs(db.getStatus(oldestTS, oldestTS));
      
      ImageViewBattery.setImage(Utils.getIconForStatus(getClass(), Status.ON).getImage());
      ImageViewRadio.setImage(Utils.getIconForStatus(getClass(), Status.MALFUNCTION).getImage());
      ImageViewPDU.setImage(Utils.getIconForStatus(getClass(), Status.STANDBY).getImage());
      
    }    
    
}
