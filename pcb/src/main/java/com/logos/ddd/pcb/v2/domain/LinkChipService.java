package com.logos.ddd.pcb.v2.domain;

import com.logos.ddd.pcb.v2.domain.component.instance.ComponentInstance;
import com.logos.ddd.pcb.v2.domain.component.instance.ComponentInstanceRepository;
import com.logos.ddd.pcb.v2.domain.component.instance.Pin;
import com.logos.ddd.pcb.v2.domain.net.Net;
import com.logos.ddd.pcb.v2.domain.net.NetRepository;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LinkChipService {

    private final NetRepository netRepository;
    private final ComponentInstanceRepository componentInstanceRepository;

    public LinkChipService(NetRepository netRepository, ComponentInstanceRepository componentInstanceRepository) {
        this.netRepository = netRepository;
        this.componentInstanceRepository = componentInstanceRepository;
    }

    public int getHops(Long startComponentInstanceId, int startPinNumber, Long endComponentInstanceId, int endPinNumber) {
        // Load all nets
        List<Net> nets = netRepository.findAll();

        // Create a map to store the adjacency list
        Map<Pin, List<Pin>> graph = new HashMap<>();

        // Build the graph
        for (Net net : nets) {
            Pin start = net.startPin();
            Pin end = net.endPin();
            graph.putIfAbsent(start, new ArrayList<>());
            graph.get(start).add(end);

            ComponentInstance startComponentInstance = componentInstanceRepository.find(net.startPin().componentInstanceId());
            List<Integer> outputPins = startComponentInstance.getOutPins(net.startPin().pinNumber());
            for (Integer outputPin : outputPins) {
                Pin internalEnd = new Pin(net.startPin().componentInstanceId(), outputPin);
                graph.putIfAbsent(start, new ArrayList<>());
                graph.get(start).add(internalEnd);
            }

            ComponentInstance endComponentInstance = componentInstanceRepository.find(net.endPin().componentInstanceId());
            List<Integer> endOutputPins = endComponentInstance.getOutPins(net.endPin().pinNumber());
            for (Integer endOutputPin : endOutputPins) {
                var internalEnd = new Pin(endComponentInstance.getId(), endOutputPin);
                graph.putIfAbsent(end, new ArrayList<>());
                graph.get(end).add(internalEnd);
            }
        }

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
                    if (!node.componentInstanceId().equals(neighbor.componentInstanceId())) {
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

    public void link(Pin startPin, Pin endPin) {
        Net net = new Net(1001L, startPin, endPin);
        netRepository.save(net);
    }
}
