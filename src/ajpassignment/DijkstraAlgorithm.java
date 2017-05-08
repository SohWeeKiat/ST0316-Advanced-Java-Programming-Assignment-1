/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajpassignment;

import java.util.*;

/**
 *
 * @author wee
 */
public class DijkstraAlgorithm {
    
    private Set<BusStop> settledNodes;
    private Set<BusStop> unSettledNodes;
    private Map<BusStop, BusStop> predecessors;
    private Map<BusStop, Integer> distance;
    
    public DijkstraAlgorithm()
    {
    }
    
    private void SetStartBusStop(BusStop start)
    {
        settledNodes = new HashSet<>();
        unSettledNodes = new HashSet<>();
        distance = new HashMap<>();
        predecessors = new HashMap<>();
        distance.put(start, 0);
        unSettledNodes.add(start);
        while (unSettledNodes.size() > 0) {
        	BusStop node = getMinimum(unSettledNodes);
        	settledNodes.add(node);
        	unSettledNodes.remove(node);
        	findMinimalDistances(node);
        }
    }
	
    private void findMinimalDistances(BusStop node) 
    {
        List<BusStop> adjacentNodes = getNeighbors(node);
        for (BusStop target : adjacentNodes) {
            if (getShortestDistance(target) > getShortestDistance(node)
                    + getDistance(node, target)) {
                distance.put(target, getShortestDistance(node)
                    + getDistance(node, target));
                predecessors.put(target, node);
                unSettledNodes.add(target);
            }
        }
    }
	
    private int getDistance(BusStop node, BusStop target) 
    {
        List<BusStop> neighbors = node.GetAllNextBusStops();
        if (neighbors.contains(target)){
        	return 1;
        }
        System.out.println("Failed to get distance");
        throw new RuntimeException("Should not happen");
    }
	
    private List<BusStop> getNeighbors(BusStop node) 
    {
        List<BusStop> neighbors = node.GetAllNextBusStops();
        List<BusStop> neighborsCopy = new ArrayList<>(neighbors);
        for (BusStop bs : neighbors) {
            if (isSettled(bs)) {
                neighborsCopy.remove(bs);
            }
        }
        return neighborsCopy;
    }
	
    private BusStop getMinimum(Set<BusStop> bus_stops) 
    {
        BusStop minimum = null;
        for (BusStop bs : bus_stops) {
            if (minimum == null) {
                minimum = bs;
            } else {
                if (getShortestDistance(bs) < getShortestDistance(minimum)) {
                    minimum = bs;
                }
            }
        }
        return minimum;
    }
	
    private boolean isSettled(BusStop bus_stop) {
        return settledNodes.contains(bus_stop);
    }
	
    private int getShortestDistance(BusStop destination) 
    {
        Integer d = distance.get(destination);
        if (d == null) {
            return Integer.MAX_VALUE;
        }
        return d;
    }
	
    private LinkedList<BusStop> getPath(BusStop target) 
    {
        LinkedList<BusStop> path = new LinkedList<>();
        BusStop step = target;
        // check if a path exists
        if (predecessors.get(step) == null) {
                return null;
        }
        path.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
        }
        // Put it into the correct order
        Collections.reverse(path);
        return path;
    }
	
    public LinkedList<BusStop> getPath(BusStop Start, BusStop Dest)
    {
        SetStartBusStop(Start);
        return getPath(Dest);
    }
}
