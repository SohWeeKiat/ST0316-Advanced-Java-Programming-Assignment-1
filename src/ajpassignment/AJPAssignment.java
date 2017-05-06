/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajpassignment;
import ui.*;

/**
 *
 * @author wee
 */
public class AJPAssignment {

    public static void LaunchSplash() throws InterruptedException{
        SplashScreen Splash = new SplashScreen();
        Splash.setVisible(true);
        Thread.sleep(2000);
        Splash.dispose();
    }
    
    public static void main(String[] args) throws InterruptedException{
        // TODO code application logic here
        LaunchSplash();
        UIManager.GetMainUI().setVisible(true);
    }
    
}
