/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajpassignment;

import java.util.LinkedList;

/**
 *
 * @author wee
 */
public class BusStopPathCollection implements Comparable{
    
    private final LinkedList<BusStopPath> path;
    
    public BusStopPathCollection(LinkedList<BusStopPath> p)
    {
        path = p;
    }
    
    public LinkedList<BusStopPath> GetPath()
    {
        return path;
    }
    
    public boolean IsDirectPathRoute()
    {
        return false;
        //return path.get(1).GetBus().CanReachDestination(Start, Dest)
    }
    
    @Override
    public int compareTo(Object o) {
        BusStopPathCollection c = (BusStopPathCollection)o;
        if (path.size() > c.path.size())
            return 1;
        else if (path.size() == c.path.size())
            return 0;
        return -1;
    }
}
