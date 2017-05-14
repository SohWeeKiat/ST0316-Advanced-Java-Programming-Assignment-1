/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajpassignment;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;

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
    
    private BusStop GetStartLocation()
    {
        return path.get(0).GetSrc();
    }
    
    private BusStop GetDestination()
    {
        return path.get(path.size() - 1).GetDest();
    }
    
    public boolean IsDirectPathRoute()
    {
        BusStop Start = GetStartLocation();
        BusStop Dest = GetDestination();
        return path.get(0).GetBus().CanReachDestination(Start, Dest)
                && GetBusesTaken().size() == 1;
    }
    
    public ArrayList<Bus> GetBusesTaken()
    {
        ArrayList<Bus> buses = new ArrayList<>();
        for(BusStopPath p : path){
            if (!buses.contains(p.GetBus()))
                buses.add(p.GetBus());
        }
        return buses;
    }
    
    @Override
    public int compareTo(Object o) {
        BusStopPathCollection c = (BusStopPathCollection)o;
        if (path.size() > c.path.size())
            return 1;
        else if (path.size() == c.path.size()){
            ArrayList<Bus> BusListA = GetBusesTaken();
            ArrayList<Bus> BusListB = c.GetBusesTaken();
            if (BusListA.equals(BusListB))
                return 0;
            else if (BusListA.size() > BusListB.size())
                return 1;
            else if (BusListA.size() < BusListB.size())
                return -1;
            return 0;
        }
        return -1;
    }
    
    @Override
    public boolean equals(Object o)
    {
        BusStopPathCollection c = (BusStopPathCollection)o;
        if (path.size() != c.path.size())
            return false;
        if (GetBusesTaken().equals(c.GetBusesTaken())){
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.path);
        return hash;
    }
    
    @Override
    public String toString()
    {
        return "[BusStops:" + path.size() + " Buses:" + GetBusesTaken().size() + "]";
    }
}
