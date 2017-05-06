/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;
import java.awt.Color;
import javax.swing.JWindow;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.ImageIcon;
/**
 *
 * @author wee
 */
public class SplashScreen extends JWindow {
    
    private final Image img;
    
    public SplashScreen(String ImageName) throws IOException
    {
        ImageIcon imageIcon = new ImageIcon(getClass().getClassLoader().getResource(ImageName));
        Image tmpImage = imageIcon.getImage();

        img = new BufferedImage(imageIcon.getIconWidth(), 
                imageIcon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        img.getGraphics().drawImage(tmpImage, 0, 0, null);
        tmpImage.flush();
        setSize(imageIcon.getIconWidth(), imageIcon.getIconHeight());
        setLocationRelativeTo(null);
        setBackground(new Color(0, 255, 0, 0));
    }
    
    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        g.drawImage(img,0,0,this);
    }
}
