/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

/**
 *
 * @author Wee Kiat
 */
public class UIManager {
    
    private static final MainForm2 MainUI = new MainForm2();
    
    public static MainForm2 GetMainUI()
    {
        return MainUI;
    }
}
