package com.logos.ddd.pcb.v2.application;

import com.logos.ddd.pcb.v2.domain.component.instance.ComponentInstance;
import com.logos.ddd.pcb.v2.domain.component.instance.ComponentInstanceRepository;
import com.logos.ddd.pcb.v2.domain.component.instance.Pin;
import com.logos.ddd.pcb.v2.domain.net.Net;
import com.logos.ddd.pcb.v2.domain.net.NetRepository;
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
        return graph;
    }
}
