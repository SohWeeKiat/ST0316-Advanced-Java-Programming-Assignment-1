/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajpassignment;

import java.util.*;
import java.util.Map.Entry;

/**
 *
 * @author wee
 */
public class DijkstraAlgorithm {
    
    private Set<BusStopPath> settledNodes;
    private Set<BusStopPath> unSettledNodes;
    private Map<BusStopPath, BusStopPath> predecessors;
    private Map<BusStop, Integer> distance;
    private ArrayList<BusStop> busStopNotToCheck;
    
    public DijkstraAlgorithm()
    {
        busStopNotToCheck = null;
    }
    
    public void SetBusStopNotToCheck(ArrayList<BusStop> BusStops)
    {
        busStopNotToCheck = BusStops;
    }
    
    private void SetStartBusStop(BusStop start)
    {
        settledNodes = new HashSet<>();
        unSettledNodes = new HashSet<>();
        distance = new HashMap<>();
        predecessors = new HashMap<>();
        distance.put(start, 0);
        unSettledNodes.add(new BusStopPath(null,start,null));
        while (unSettledNodes.size() > 0) {
        	BusStopPath node = getMinimum(unSettledNodes);
        	settledNodes.add(node);
        	unSettledNodes.remove(node);
        	findMinimalDistances(node);
        }
    }
	
    private void findMinimalDistances(BusStopPath node) 
    {
        List<BusStopPath> adjacentNodes = getNeighbors(node);
        for (BusStopPath target : adjacentNodes) {
            if (getShortestDistance(target) > getShortestDistance(node)
                    + getDistance(node, target)) {
                distance.put(target.GetDest(), getShortestDistance(node)
                    + getDistance(node, target));
                predecessors.put(target, node);
                unSettledNodes.add(target);
            }
        }
    }
	
    private int getDistance(BusStopPath node, BusStopPath target) 
    {
        List<BusStopPath> neighbors = node.GetDest().GetAllNextBusStops();
        if (neighbors.contains(target)){
        	return 1;
        }
        throw new RuntimeException("Should not happen");
    }
	
    private List<BusStopPath> getNeighbors(BusStopPath node) 
    {
        List<BusStopPath> neighbors = node.GetDest().GetAllNextBusStops();
        List<BusStopPath> neighborsCopy = new ArrayList<>(neighbors);
        for (BusStopPath bsp : neighbors) {
            if (busStopNotToCheck != null && busStopNotToCheck.contains(bsp.GetDest())){
                neighborsCopy.remove(bsp);
            }else if (isSettled(bsp)) {
                neighborsCopy.remove(bsp);
            }
        }
        return neighborsCopy;
    }
	
    private BusStopPath getMinimum(Set<BusStopPath> bus_stops) 
    {
        BusStopPath minimum = null;
        for (BusStopPath bsp : bus_stops) {
            if (minimum == null) {
                minimum = bsp;
            } else {
                if (getShortestDistance(bsp) < getShortestDistance(minimum)) {
                    minimum = bsp;
                }
            }
        }
        return minimum;
    }
	
    private boolean isSettled(BusStopPath bus_stop) {
        return settledNodes.contains(bus_stop);
    }
	
    private int getShortestDistance(BusStopPath destination) 
    {
        Integer d = distance.get(destination.GetDest());
        if (d == null) {
            return Integer.MAX_VALUE;
        }
        return d;
    }
	
    private LinkedList<BusStopPath> getPath(BusStop target) 
    {
        LinkedList<BusStopPath> path = new LinkedList<>();
        BusStopPath step = new BusStopPath(null,target,null);
        // check if a path exists
        if (predecessors.get(step) == null) {
            return null;
        }
        step = predecessors.get(step);
        for (Entry<BusStopPath, BusStopPath> entry : predecessors.entrySet()) {
            if (entry.getValue()== step) {
                path.add(entry.getKey());
                break;
            }
        }
        path.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
        }
        Collections.reverse(path);
        path.remove(0);
        return path;
    }
	
    public LinkedList<BusStopPath> getPath(BusStop Start, BusStop Dest)
    {
        SetStartBusStop(Start);
        return getPath(Dest);
    }
}
