package com.logos.ddd.pcb.v2.domain;

import com.logos.ddd.pcb.v2.domain.component.instance.ComponentInstance;
import com.logos.ddd.pcb.v2.domain.component.instance.ComponentInstanceRepository;
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
        Map<Pair<Long, Integer>, List<Pair<Long, Integer>>> graph = new HashMap<>();

        // Build the graph
        for (Net net : nets) {
            Pair<Long, Integer> start = Pair.of(net.getStartComponentInstance().getId(), net.getStartPinNumber());
            Pair<Long, Integer> end = Pair.of(net.getEndComponentInstance().getId(), net.getEndPinNumber());
            graph.putIfAbsent(start, new ArrayList<>());
            graph.get(start).add(end);

            List<Integer> outputPins = net.getStartComponentInstance().getOutPins(net.getStartPinNumber());
            for (Integer outputPin : outputPins) {
                Pair<Long, Integer> internalEnd = Pair.of(net.getStartComponentInstance().getId(), outputPin);
                graph.putIfAbsent(start, new ArrayList<>());
                graph.get(start).add(internalEnd);
            }

            List<Integer> endOutputPins = net.getEndComponentInstance().getOutPins(net.getEndPinNumber());
            for (Integer endOutputPin : endOutputPins) {
                Pair<Long, Integer> internalEnd = Pair.of(net.getEndComponentInstance().getId(), endOutputPin);
                graph.putIfAbsent(end, new ArrayList<>());
                graph.get(end).add(internalEnd);
            }
        }

        // Define the start and end nodes
        Pair<Long, Integer> startNode = Pair.of(startComponentInstanceId, startPinNumber);
        Pair<Long, Integer> endNode = Pair.of(endComponentInstanceId, endPinNumber);

        // Use BFS to find the shortest path
        Queue<Pair<Long, Integer>> queue = new LinkedList<>();
        Map<Pair<Long, Integer>, Integer> distances = new HashMap<>();
        queue.offer(startNode);
        distances.put(startNode, 0);

        while (!queue.isEmpty()) {
            Pair<Long, Integer> node = queue.poll();
            int distance = distances.get(node);

            if (node.equals(endNode)) {
                return distance;
            }

            for (Pair<Long, Integer> neighbor : graph.getOrDefault(node, Collections.emptyList())) {
                if (!distances.containsKey(neighbor)) {
                    queue.offer(neighbor);
                    // Only increase the distance if the hop is not within the same component instance
                    if (!node.getLeft().equals(neighbor.getLeft())) {
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

    public void link(Long startComponentInstanceId, int startComponentInstancePin,
                     Long endComponentInstanceId, int endComponentInstancePin) {
        Net net = new Net();
        ComponentInstance startComponentInstance = componentInstanceRepository.find(startComponentInstanceId);
        ComponentInstance endComponentInstance = componentInstanceRepository.find(endComponentInstanceId);
        net.setStartComponentInstance(startComponentInstance);
        net.setEndComponentInstance(endComponentInstance);
        net.setStartPinNumber(startComponentInstancePin);
        net.setEndPinNumber(endComponentInstancePin);
        netRepository.save(net);
    }
}
