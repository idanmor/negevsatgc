/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package negevsatgui;

import MenuItems.MainMenu;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import misc.SattaliteUtils;

/**
 *
 * @author Max
 */
public class MainWindow{
    private BorderPane mainPane;
    private static MainWindow instance;
    
    public MainWindow(){
    super();
    synchronized(MainWindow.class){
        if(instance != null) throw new UnsupportedOperationException(
                getClass()+" is singleton but constructor called more than once");
        instance = this;
    }
}
    public static MainWindow  getMainWindow(){
        if(instance == null){
            return new MainWindow();
        }
        return instance;
    }
    
   
    public void start(Stage primaryStage) {
        int height = 600;
        int width = 800;
        
        mainPane = new BorderPane();  
        BorderPane root = new BorderPane();
        root.setTop(new MainMenu());
        root.setCenter(mainPane);
        Label smartSentance = new Label("Anyone who has never made a mistake has never tried anything new.\n - Albert Einstein");
        smartSentance.setStyle("-fx-font-size:20;");
        Label satteliteStatus = new Label(getSatteliteStatus());
        satteliteStatus.setStyle("-fx-font-size:20;");
        mainPane.setCenter(smartSentance);
        mainPane.setBottom(satteliteStatus);
        Scene scene = new Scene(root, width, height);      
       // ((BorderPane) scene.getRoot()).getChildren().addAll(new MainMenu(), mainPane);
     
        primaryStage.setScene(scene);
        scene.getStylesheets().add
        (NegevSatGui.class.getResource("mainCss.css").toExternalForm());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        showMainScreen(mainPane);
    }

    public BorderPane getMainPane(){
        return mainPane;
    }

    private String getSatteliteStatus() {
        String[] generatedStatus = {"Pass phase: Time remaining 3:20","Pass phase: In 2:30:45", "Connection Loss"};
        return generatedStatus[(int)(Math.random()*generatedStatus.length)];
    }
    
    TextArea demoListView;
    public void showMainScreen(BorderPane mainPane){
        VBox mainScreen = new VBox();
        HBox firtsLine = new HBox();
        HBox secondLine = new HBox();
        VBox logBox = new VBox();
        VBox buttonBox = SattaliteUtils.getVbox();
     //   firtsLine.getChildren().add(getImageViewFromLocation("map.png"));
        firtsLine.getChildren().addAll(getImageViewFromLocation("map.png",firtsLine),getImageViewFromLocation("sattelite.png",firtsLine));
       
         demoListView = new TextArea();
         demoListView.setEditable(false);
         TextField toLogger = new TextField();
         toLogger.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent t) {
                 if (t.getCode() == KeyCode.ENTER) {
                    demoListView.appendText(addDateToString(toLogger.getText()) + "\n");
                    toLogger.setText("");
                 }
            }
        });
        Label logTitle = new Label("Log Screen");
        demoListView.setText("(1/1/2014)Sattelite status: = Good \n(1/1/2014)Mulfunction in Power supply \n(1/1/2014)Battery status: 60% \n");
        demoListView.setPrefWidth(400);
        logBox.getChildren().addAll(logTitle,demoListView,toLogger);
        buttonBox.getChildren().addAll(generateButtonHolders());
        secondLine.getChildren().addAll(logBox,buttonBox);
        mainScreen.getChildren().addAll(firtsLine,secondLine);
        //sattelite.png
        mainPane.setCenter(mainScreen);
         firtsLine.autosize();
    }
    private List<HBox> generateButtonHolders(){
       final String[] logOutPut = {"Moving to power save mode... ", "Moving to maintenece mode...",
           "Restart command initiated..", "Sending telemetry file...", "Sending scheduled commands", "Running system analasys"};
       String[] buttonNames = {"Power Save", "Maitenance Mode",
           "Restart systmes", "Send Telemetry", "Send shceduled command", "Analyze systems"};
       HBox one = SattaliteUtils.getHBox();
       HBox two =  SattaliteUtils.getHBox();
       int numOfButton = logOutPut.length;
       
        List<HBox> buttonList = new ArrayList<HBox>();
        for(int i = 0; i < numOfButton; i++){
            Button b = new Button(buttonNames[i]);
            final String a = logOutPut[i];
            b.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent t) {
                    
                    demoListView.appendText(addDateToString(a) + "\n");
                }
            });
            if(i < numOfButton/2){
                one.getChildren().add(b);
            }else{
               two.getChildren().add(b);
            }
        }
        buttonList.add(one);
        buttonList.add(two);
        
        return buttonList;
    }
    
    private String addDateToString(String text){
           DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
           Date date = new Date();
           return "(" + dateFormat.format(date) + ")" + text ;
                    
    }
    
    private ImageView getImageViewFromLocation(String location, HBox father){
        ImageView iv = new ImageView(new Image(getClass().getResourceAsStream(location)));
        return iv;
          
    }
    
    
    
}
