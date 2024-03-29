package com.logos.ddd.pcb.v2.domain;

import com.logos.ddd.pcb.v2.domain.component.instance.Pin;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HopCalculator {


    public int getHops(Long startComponentInstanceId, int startPinNumber, Long endComponentInstanceId, int endPinNumber, Map<Pin, List<Pin>> graph) {
        // Define the start and end nodes
        Pin startNode = new Pin(startComponentInstanceId, startPinNumber);
        Pin endNode = new Pin(endComponentInstanceId, endPinNumber);

        // Use BFS to find the shortest path
        Queue<Pin> queue = new LinkedList<>();
        Map<Pin, Integer> distances = new HashMap<>();
        queue.offer(startNode);
        distances.put(startNode, 0);

        while (!queue.isEmpty()) {
            Pin node = queue.poll();
            int distance = distances.get(node);

            if (node.equals(endNode)) {
                return distance;
            }

            for (Pin neighbor : graph.getOrDefault(node, Collections.emptyList())) {
                if (!distances.containsKey(neighbor)) {
                    queue.offer(neighbor);
                    // Only increase the distance if the hop is not within the same component instance
                    if (!node.isOnSameComponentInstance(neighbor)) {
                        distances.put(neighbor, distance + 1);
                    } else {
                        distances.put(neighbor, distance);
                    }
                }
            }
        }

        // If there is no path from the start node to the end node, return -1
        return -1;
    }

}
