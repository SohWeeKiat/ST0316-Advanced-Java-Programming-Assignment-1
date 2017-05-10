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
public class BusStop implements Comparable{
    
    private final String bus_stop_code;
    private final String road_desc;
    private final String bus_stop_desc;
    private final ArrayList<Bus> buses;
    
    public BusStop()
    {
        bus_stop_code = "";
        road_desc = "";
        bus_stop_desc = "";
        buses = new ArrayList<>();
    }
    
    public BusStop(String BusCode,String RoadDesc,String BusStopDesc)
    {
    	bus_stop_code = BusCode;
        road_desc = RoadDesc;
        bus_stop_desc = BusStopDesc;
        buses = new ArrayList<>();
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
    	return bus_stop_code + " - " + bus_stop_desc + " , " + road_desc;
    }
    
    public void PopulateBuses(HashMap<String,Bus> bus_svcs)
    {
        for(Bus b : bus_svcs.values()){
            if (b.ServingBusStop(this)){
                buses.add(b);
            }
        }
    }
    
    public ArrayList<BusStopPath> GetAllNextBusStops()
    {
    	ArrayList<BusStopPath> BusStops = new ArrayList<>();
    	for(Bus b : buses){
            BusStop bs = b.GetNextBusStop(this);
            if (bs != null){
                BusStops.add(new BusStopPath(this,bs,b));
            }
    	}
    	return BusStops;
    }

    public ArrayList<Bus> GetNextBusStopSvcs(BusStop NextBusStop)
    {
        ArrayList<Bus> n_buses = new ArrayList<>();
    	for(Bus b : buses){
            BusStop bs = b.GetNextBusStop(this);
            if (bs == NextBusStop){
                n_buses.add(b);
            }
    	}
    	return n_buses;
    }
    
    @Override
    public int compareTo(Object o) {
        BusStop bs2 = (BusStop)o;
        boolean isFirstCharAlpha1 = bus_stop_code.charAt(0) >= 'A';
        boolean isFirstCharAlpha2 = bs2.bus_stop_code.charAt(0) >= 'A';
        if (isFirstCharAlpha1 && isFirstCharAlpha2){
            return bus_stop_code.compareTo(bs2.bus_stop_code);
        }else if (isFirstCharAlpha1 && !isFirstCharAlpha2){
            return 1;
        }else if (isFirstCharAlpha2 && !isFirstCharAlpha1){
            return -1;
        }
        int BusStop1 = Integer.parseInt(bus_stop_code);
        int BusStop2 = Integer.parseInt(bs2.bus_stop_code);
        if (BusStop1 > BusStop2){
            return 1;
        }else if (BusStop1 == BusStop2){
            return 0;
        }
        return -1;
        //return GetBusStopCode().compareTo(((BusStop)o).GetBusStopCode());
    }
}
