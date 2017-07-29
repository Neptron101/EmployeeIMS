/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package empims;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Bikin Maharjan
 */
public class SplashFXMLController implements Initializable {
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            
           
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(SplashFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    } 
    
    class SplashScreen extends Thread{
        @Override
        public void run(){
            try{
                Thread.sleep(3000);
                
                Platform.runLater(new Runnable(){
                @Override
                public void run() {
                    Parent root = null;
                    try{
                        root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                    } catch (IOException ex) {
                        Logger.getLogger(SplashFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();
                   
                    
            }
                });     
                 
            } catch (InterruptedException ex) {
                Logger.getLogger(SplashFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
