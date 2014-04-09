/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package negevsatgui;

import MenuItems.MainMenu;
import Utils.Constants;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

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
//        BorderPane logsTemp = new BorderPane();
//        TextField logs = new TextField("Mission logs");
//        logsTemp.setTop(logs);
//        logsTemp.setCenter(new TextArea());
//        mainPane.setCenter(logsTemp);
        mainPane.setCenter(smartSentance);
        mainPane.setBottom(satteliteStatus);
        Scene scene = new Scene(root, width, height);      
       // ((BorderPane) scene.getRoot()).getChildren().addAll(new MainMenu(), mainPane);
     
        primaryStage.setScene(scene);
        scene.getStylesheets().add
        (NegevSatGui.class.getResource(Constants.CSS_MAIN).toExternalForm());
      //  primaryStage.setFullScreen(true);
       // primaryStage.show();
    }

    public BorderPane getMainPane(){
        return mainPane;
    }

    private String getSatteliteStatus() {
        String[] generatedStatus = {"Pass phase: Time remaining 3:20","Pass phase: In 2:30:45", "Connection Loss"};
        return generatedStatus[(int)(Math.random()*generatedStatus.length)];
    }
    
    
    
}
