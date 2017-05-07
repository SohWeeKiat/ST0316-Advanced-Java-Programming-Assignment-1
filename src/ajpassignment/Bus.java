/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajpassignment;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Wee Kiat
 */
public class Bus {
    
    private final String bus_code;
    private final HashMap<Integer,ArrayList<BusStop>> svc_routes;
    
    public Bus() 
    {
        bus_code = "";
        svc_routes = new HashMap<Integer, ArrayList<BusStop>>();
    }

    public Bus(String BusCode) 
    {
        bus_code = BusCode;
        svc_routes = new HashMap<Integer, ArrayList<BusStop>>();
    }
    
    public String GetBusCode() 
    {
        return bus_code;
    }
    
    public void AddBusStop(int Dir,BusStop bs) 
    {
        if (svc_routes.containsKey(Dir)){
            svc_routes.get(Dir).add(bs);
        }else{
            ArrayList<BusStop> NewRoute = new ArrayList<BusStop>();
            NewRoute.add(bs);
            svc_routes.put(Dir,NewRoute);
        }
    }
    
    public boolean ServingBusStop(BusStop bus_stop)
    {
        for(ArrayList<BusStop> route : svc_routes.values()){
            if (route.contains(bus_stop)){
                return true;
            }
        }
        return false;
    }
    
    public boolean CanReachDestination(BusStop Start, BusStop Dest)
    {
        for(ArrayList<BusStop> route : svc_routes.values()){
            int StartBsIndex = route.indexOf(Start);
            int EndBsIndex = route.indexOf(Dest);
            if (StartBsIndex >= 0 && EndBsIndex >= 0){
                if (StartBsIndex < EndBsIndex){
                    return true;
                }
            }
        }
        return false;
    }
    
    public ArrayList<BusStop> GetRoute(BusStop Current)
    {
        for(ArrayList<BusStop> route : svc_routes.values()){
            if (route.indexOf(Current) >= 0){
                return route;
            }
        }
        return null;
    }
}
