/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajpassignment;
import javax.swing.JOptionPane;
import ui.*;

/**
 *
 * @author wee
 */
public class AJPAssignment {

    public static boolean LaunchSplash() throws InterruptedException{
        SplashScreen Splash = new SplashScreen();
        Splash.setVisible(true);
        if (!BusService.get().Initialize()){
            JOptionPane.showMessageDialog(null, "Failed to initialize bus data","Error",JOptionPane.ERROR_MESSAGE);
            Splash.dispose();
            return false;
        }
        Splash.SetProgressBarFull();
        Thread.sleep(1000);
        Splash.dispose();
        return true;
    }
    
    public static void main(String[] args) throws InterruptedException{
        // TODO code application logic here
        if (!LaunchSplash()){
            return;
        }
        new MainForm().setVisible(true);
    }
    
}
