/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajpassignment;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 *
 * @author Wee Kiat
 */
public class BusService {
    private static final BusService BS_Singleton = new BusService();
    private final HashMap<String,BusStop> bus_stops;
    private final HashMap<String,Bus> bus_svcs;
    
    static public BusService get()
    {
        return BS_Singleton;
    }
    
    private BusService()
    {
        bus_stops = new HashMap<String, BusStop>();
        bus_svcs = new HashMap<String, Bus>();
    }
    
    public boolean Initialize()
    {
        try{
            if (!InitializeBusStops()){
                return false;
            }else if (!InitializeBusSvcs()){
                return false;
            }
        }catch(IOException e){
            return false;
        }
        for(BusStop bs : bus_stops.values()){
            bs.PopulateBuses(bus_svcs);
        }
        return true;
    }
    
    private boolean InitializeBusStops() throws IOException
    {
        FileReader BusStopFile;
        try{
            BusStopFile = new FileReader("lta-bus_stop_codes.csv");
        }catch(FileNotFoundException e){
            return false;
        }
        BufferedReader BusStopReader = new BufferedReader(BusStopFile);

        BusStopReader.readLine();
        String s = BusStopReader.readLine();
        do{
                StringTokenizer st = new StringTokenizer(s,",",false);
                String BusCode = st.nextToken();
                String RoadDesc = st.nextToken();
                String BusStopDesc = st.nextToken();
                bus_stops.put(BusCode,new BusStop(BusCode,RoadDesc,BusStopDesc));

                s = BusStopReader.readLine();
        }while(s != null && !s.isEmpty());
        
        BusStopReader.close();
        return true;
    }
    
    private boolean InitializeBusSvcs() throws IOException
    {
        FileReader BusSvcFile;
        try{
            BusSvcFile = new FileReader("lta-sbst_route.csv");
        }catch(FileNotFoundException e){
            return false;
        }
        BufferedReader BusSvcReader = new BufferedReader(BusSvcFile);

        BusSvcReader.readLine();
        String s = BusSvcReader.readLine();
        do{
                StringTokenizer st = new StringTokenizer(s,",",false);
                String BusCode = st.nextToken();
                
                Bus b = bus_svcs.get(BusCode);
                int Dir = Integer.parseInt(st.nextToken());
                st.nextToken();//route seq
                String BusStopCode = st.nextToken();
                if (b != null){
                    BusStop bs = bus_stops.get(BusStopCode);
                    b.AddBusStop(Dir,bs);
                }else{
                    b = new Bus(BusCode);
                    bus_svcs.put(BusCode,b);
                    b.AddBusStop(Dir,bus_stops.get(BusStopCode));
                }
                s = BusSvcReader.readLine();
        }while(s != null && !s.isEmpty());
        BusSvcReader.close();
        return true;
    }
    
    public ArrayList<BusStop> SearchBusStop(String sub_string)
    {
        sub_string = sub_string.toLowerCase();
        ArrayList<BusStop> BS_List = new ArrayList<BusStop>();
        for(BusStop bs : bus_stops.values()){
            if (bs.toString().toLowerCase().contains(sub_string)){
                BS_List.add(bs);
            }
        }
        Collections.sort(BS_List, new Comparator<BusStop>() {
            @Override
            public int compare(BusStop BusStop1, BusStop BusStop2)
            {
                return BusStop1.GetBusStopCode().compareTo(BusStop2.GetBusStopCode());
            }
        });
        return BS_List;
    }
    
    public ArrayList<Bus> SearchBus(String sub_string)
    {
        sub_string = sub_string.toLowerCase();
        ArrayList<Bus> B_List = new ArrayList<Bus>();
        for(Bus b : bus_svcs.values()){
            if (b.GetBusCode().toLowerCase().contains(sub_string)){
                B_List.add(b);
            }
        }
        
        Collections.sort(B_List, new Comparator<Bus>() {
            @Override
            public int compare(Bus Bus1, Bus Bus2)
            {
                int BusNumber1 = 0,BusNumber2 = 0;
                try {
                    BusNumber1 = Integer.parseInt(Bus1.GetBusCode());
                }catch(NumberFormatException e){
                    String BusNumber = Bus1.GetBusCode();
                    BusNumber1 = Integer.parseInt(BusNumber.substring(0,BusNumber.length() - 1));
                }
                try {
                    BusNumber2 = Integer.parseInt(Bus2.GetBusCode());
                }catch(NumberFormatException e){
                    String BusNumber = Bus2.GetBusCode();
                    BusNumber2 = Integer.parseInt(BusNumber.substring(0,BusNumber.length() - 1));
                }
                if (BusNumber1 > BusNumber2){
                    return 1;
                }else if (BusNumber1 == BusNumber2){
                    return 0;
                }
                return -1;
            }
        });
        return B_List;
    }
}
