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
public class Bus implements Comparable{
    
    private final String bus_code;
    private final HashMap<Integer,ArrayList<BusStop>> svc_routes;
    
    public Bus() 
    {
        bus_code = "";
        svc_routes = new HashMap<>();
    }

    public Bus(String BusCode) 
    {
        bus_code = BusCode;
        svc_routes = new HashMap<>();
    }
    
    public String GetBusCode() 
    {
        return bus_code;
    }
    
    public HashMap<Integer,ArrayList<BusStop>> GetRoutes()
    {
        return svc_routes;
    }
    
    @Override
    public String toString()
    {
        return bus_code;
    }

    public void AddBusStop(int Dir,BusStop bs) 
    {
        if (svc_routes.containsKey(Dir)){
            svc_routes.get(Dir).add(bs);
        }else{
            ArrayList<BusStop> NewRoute = new ArrayList<>();
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

    public BusStop GetNextBusStop(BusStop current)
    {
        for(ArrayList<BusStop> route : svc_routes.values()){
            int BsIndex = route.indexOf(current);
            if (BsIndex >= 0 && ++BsIndex < route.size()){
            	return route.get(BsIndex);
            }
        }
        return null;
    }
    
    private int GetBusCleanCode()
    {
        String SNumber = "";
        for(int i = 0;i < bus_code.length();i++){
            char c = bus_code.charAt(i);
            if (c >= '0' && c <= '9'){
                SNumber += c;
            }
        }
        return Integer.parseInt(SNumber);
    }
    
    @Override
    public int compareTo(Object o) {
        Bus Bus2 = (Bus)o;
        boolean isFirstCharAlpha1 = bus_code.charAt(0) >= 'A';
        boolean isFirstCharAlpha2 = Bus2.bus_code.charAt(0) >= 'A';
        if (isFirstCharAlpha1 && isFirstCharAlpha2){
            return bus_code.compareTo(Bus2.bus_code);
        }else if (isFirstCharAlpha1 && !isFirstCharAlpha2){
            return 1;
        }else if (isFirstCharAlpha2 && !isFirstCharAlpha1){
            return -1;
        }
        int BusNumber1 = GetBusCleanCode();
        int BusNumber2 = Bus2.GetBusCleanCode();
        if (BusNumber1 > BusNumber2){
            return 1;
        }else if (BusNumber1 == BusNumber2){
            return 0;
        }
        return -1;
    }
}
