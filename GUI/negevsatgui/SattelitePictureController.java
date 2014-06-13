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

import Utils.Constants;
import Utils.Utils;
import data.DataManager;
import data.Satellite;
import data.Status;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.util.Pair;

/**
 *
 * @author Max
 */
public class SattelitePictureController implements Initializable {
    private static SattelitePictureController instance;
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
    Satellite st = null;
    URL url = null;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	instance = this;
//      final long monthInMS = 26280000;//need to mult by 100
//      Timestamp oldestTS=new Timestamp(System.currentTimeMillis() - monthInMS * 100);
//	    Timestamp TS=new Timestamp(System.currentTimeMillis());
//      ArrayList<Pair<String, Status>> statusPairs = db.getListOfStatusPairs(db.getStatus(oldestTS, oldestTS));
      DataManager db = DataManager.getInstance();
      
//       this.url = url;
//       if(st == null ){
//    	   return;
//       }else{
//    	   updateSateliteStatus(st);
//       }
      //db.getListOfStatusPairs(st);
      ImageViewBattery.setImage(Utils.getIconForStatus(getClass(), Status.ON).getImage());
      ImageViewRadio.setImage(Utils.getIconForStatus(getClass(), Status.MALFUNCTION).getImage());
      ImageViewPDU.setImage(Utils.getIconForStatus(getClass(), Status.STANDBY).getImage());
      ImageViewComputer.setImage(Utils.getIconForStatus(getClass(), Status.ON).getImage());
      ImageViewPayload.setImage(Utils.getIconForStatus(getClass(), Status.ON).getImage());
      ImageViewSP.setImage(Utils.getIconForStatus(getClass(), Status.ON).getImage());
  
      
//      ImageViewBattery.setImage(Utils.getIconForStatus(getClass(), st.getEnergyStatus()).getImage());
//      ImageViewRadio.setImage(Utils.getIconForStatus(getClass(), st.getTempratureStatus()).getImage());
//      ImageViewPDU.setImage(Utils.getIconForStatus(getClass(), st.getThermalStatus()).getImage());
//      ImageViewComputer.setImage(Utils.getIconForStatus(getClass(), st.getSbandStatus()).getImage());
//      ImageViewPayload.setImage(Utils.getIconForStatus(getClass(), st.getPayloadStatus()).getImage());
//      ImageViewSP.setImage(Utils.getIconForStatus(getClass(), st.getSolarPanelsStatus()).getImage());
//      
    }
    
    public void setSatellite(Satellite st){
    	this.st = st;
    	initialize(url, null);
    }
    public static void updateSateliteStatus(Satellite st){
    
      instance.ImageViewBattery.setImage(Utils.getIconForStatus(instance.getClass(), st.getEnergyStatus()).getImage());
      instance.ImageViewRadio.setImage(Utils.getIconForStatus(instance.getClass(), st.getTempratureStatus()).getImage());
      instance. ImageViewPDU.setImage(Utils.getIconForStatus(instance.getClass(), st.getThermalStatus()).getImage());
      instance. ImageViewComputer.setImage(Utils.getIconForStatus(instance.getClass(), st.getSbandStatus()).getImage());
      instance.  ImageViewPayload.setImage(Utils.getIconForStatus(instance.getClass(), st.getPayloadStatus()).getImage());
      instance. ImageViewSP.setImage(Utils.getIconForStatus(instance.getClass(), st.getSolarPanelsStatus()).getImage());
      
    }
    
}
