/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.*;
import javax.swing.JTable;
/**
 *
 * @author Wee Kiat
 */
public class TableClickEvent extends MouseAdapter{
    
    private final boolean DoubleClickEvent;
    private final Function<Integer,Void> eventproc;
    
    public TableClickEvent(boolean IsDoubleClick,Function<Integer,Void> event)
    {
        DoubleClickEvent = IsDoubleClick;
        eventproc = event;
    }
    
    @Override
    public void mousePressed(MouseEvent me)
    {
        if (me.getClickCount() == (DoubleClickEvent ? 2 : 1)) {
            JTable table =(JTable)me.getSource();
            Point p = me.getPoint();
            int row = table.rowAtPoint(p);
            eventproc.apply(row);
        }
    }
}
