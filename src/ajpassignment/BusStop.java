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
public class BusStop {
    
    private final String bus_stop_code;
    private final String road_desc;
    private final String bus_stop_desc;
    private final ArrayList<Bus> buses;
    
    public BusStop()
    {
        bus_stop_code = "";
        road_desc = "";
        bus_stop_desc = "";
        buses = new ArrayList<Bus>();
    }
    
    public BusStop(String BusCode,String RoadDesc,String BusStopDesc)
    {
    	bus_stop_code = BusCode;
        road_desc = RoadDesc;
        bus_stop_desc = BusStopDesc;
        buses = new ArrayList<Bus>();
    }
    
    public String GetBusStopCode()
    {
        return bus_stop_code;
    }
    
    public String GetRoadDesc()
    {
        return road_desc;
    }
    
    public String GetBusStopDesc()
    {
        return bus_stop_desc;
    }
    
    public ArrayList<Bus> GetBuses()
    {
        return buses;
    }
    
    @Override
    public String toString() 
    {
    	return bus_stop_code + "-" + bus_stop_desc + "," + road_desc;
    }
    
    public void PopulateBuses(HashMap<String,Bus> bus_svcs)
    {
        for(Bus b : bus_svcs.values()){
            if (b.ServingBusStop(this)){
                buses.add(b);
            }
        }
    }
}
