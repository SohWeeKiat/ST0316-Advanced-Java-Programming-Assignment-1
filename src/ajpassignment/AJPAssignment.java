/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajpassignment;
import java.io.IOException;
import ui.*;

/**
 *
 * @author wee
 */
public class AJPAssignment {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        
        SplashScreen splash = new SplashScreen("ajpassignment\\BusIcon.png");
        splash.setVisible(true);
        Thread.sleep(2000);
        splash.dispose();
        MainForm MainUI = new MainForm();
        MainUI.setVisible(true);
    }
    
}
