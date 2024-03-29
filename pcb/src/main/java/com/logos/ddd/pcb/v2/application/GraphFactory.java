package com.logos.ddd.pcb.v2.application;

import com.logos.ddd.pcb.v2.domain.component.instance.ComponentInstance;
import com.logos.ddd.pcb.v2.domain.component.instance.Pin;
import com.logos.ddd.pcb.v2.domain.net.Net;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GraphFactory {
    private final NetRepository netRepository;
    private final ComponentInstanceRepository componentInstanceRepository;

    public GraphFactory(NetRepository netRepository, ComponentInstanceRepository componentInstanceRepository) {
        this.netRepository = netRepository;
        this.componentInstanceRepository = componentInstanceRepository;
    }

    public Map<Pin, List<Pin>> buildGraph() {
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
            List<Pin> outputPins = startComponentInstance.getOutPins(net.startPin());
            if (!outputPins.isEmpty()) {
                graph.putIfAbsent(start, new ArrayList<>());
                graph.get(start).addAll(outputPins);
            }

            ComponentInstance endComponentInstance = componentInstanceRepository.find(net.endPin().componentInstanceId());
            List<Pin> endOutputPins = endComponentInstance.getOutPins(net.endPin());
            if (!endOutputPins.isEmpty()) {
                graph.putIfAbsent(end, new ArrayList<>());
                graph.get(end).addAll(endOutputPins);
            }
        }
        return graph;
    }
}
