/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajpassignment;

import java.io.*;
import java.util.*;

/**
 *
 * @author Wee Kiat
 */
public class BusService {
    private static final BusService BS_Singleton = new BusService();
    private final HashMap<String,BusStop> bus_stops;
    private final HashMap<String,Bus> bus_svcs;
    private final DijkstraAlgorithm dijkstra;
    
    static public BusService get()
    {
        return BS_Singleton;
    }
    
    private BusService()
    {
        bus_stops = new HashMap<>();
        bus_svcs = new HashMap<>();
        dijkstra = new DijkstraAlgorithm();
    }
    
    public ArrayList<BusStop> GetAllBusStop()
    {
        ArrayList<BusStop> list = new ArrayList<BusStop>(bus_stops.values());
        Collections.sort(list);
        return list;
    }
    
    public List<Bus> GetAllBuses()
    {
        List<Bus> list = new ArrayList<Bus>(bus_svcs.values());
        Collections.sort(list);
        return list;
    }
    
    public boolean Initialize()
    {
        try{
            if (!InitializeBusStops()){
                return false;
            }else if (!InitializeBusSvcs("lta-sbst_route.csv",true)){
                return false;
            }else if (!InitializeBusSvcs("lta-smrt_route.csv",false)){
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
    
    private boolean InitializeBusSvcs(String FileName,boolean SBS_Bus) throws IOException
    {
        FileReader BusSvcFile;
        try{
            BusSvcFile = new FileReader(FileName);
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
                b = new Bus(BusCode,SBS_Bus);
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
        ArrayList<BusStop> BS_List = new ArrayList<>();
        for(BusStop bs : bus_stops.values()){
            if (bs.toString().toLowerCase().contains(sub_string)){
                BS_List.add(bs);
            }
        }
        Collections.sort(BS_List);
        return BS_List;
    }
    
    public ArrayList<Bus> SearchBus(String sub_string)
    {
        sub_string = sub_string.toLowerCase();
        ArrayList<Bus> B_List = new ArrayList<>();
        for(Bus b : bus_svcs.values()){
            if (b.GetBusCode().toLowerCase().contains(sub_string)){
                B_List.add(b);
            }
        }
        Collections.sort(B_List);
        return B_List;
    }
    
    private ArrayList<BusStopPathCollection> TryGetDirectPath(BusStop Start,BusStop Dest)
    {
        ArrayList<BusStopPathCollection> collection = new ArrayList<>();
        ArrayList<Bus> buses = Start.GetBuses();
        for(Bus b : buses){
            if (b.CanReachDestination(Start, Dest)){
                BusStopPathCollection c = b.GenerateBusStopPath(Start, Dest);
                if (c.GetPath().size() > 0){
                    collection.add(c);
                }
            }
        }
        return collection;
    }
    
    public ArrayList<BusStopPathCollection> GeneratePath(BusStop Start, BusStop Dest)
    {
        ArrayList<BusStopPathCollection> paths = TryGetDirectPath(Start,Dest);
        dijkstra.SetBusStopNotToCheck(null);
        LinkedList<BusStopPath> route = dijkstra.getPath(Start, Dest);
        if (route != null){
            BusStopPathCollection c = new BusStopPathCollection(route);
            boolean SameRoute = false;
            for(BusStopPathCollection o : paths){
                if (o.equals(c)){
                    SameRoute = true;
                }
            }
            if (SameRoute){
                 Collections.sort(paths);
                return paths;
            }
            paths.add(c);
            ArrayList<BusStop> ignore_list = new ArrayList<>();
            LinkedList<BusStopPath> reverse_path = new LinkedList<>(route);
            Collections.reverse(reverse_path);
            int MaxBusStop = route.size();
            for(BusStopPath p : reverse_path){
                ignore_list.clear();
                ignore_list.add(p.GetDest());
                dijkstra.SetBusStopNotToCheck(ignore_list);
                LinkedList<BusStopPath> route2 = dijkstra.getPath(Start, Dest);
                if(route2 != null && route2.size() > MaxBusStop){
                    MaxBusStop = route2.size();
                    paths.add(new BusStopPathCollection(route2));
                }
            }
        }
        Collections.sort(paths);
        return paths;
    }
}
