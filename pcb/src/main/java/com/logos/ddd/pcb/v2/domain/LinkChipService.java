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


    public Net linkChip(Long startChipId, Long endChipId) {
        Net net = new Net();
        ComponentInstance startComponentInstance = componentInstanceRepository.find(startChipId);
        ComponentInstance endComponentInstance = componentInstanceRepository.find(endChipId);
        net.setStartComponentInstance(startComponentInstance);
        net.setEndComponentInstance(endComponentInstance);
        return netRepository.save(net);
    }

    public int getHops(Long aChipId, Long cChipId) {
        Map<Long, List<Long>> graph = getNetGraph();

        return getHopsUseBFS(aChipId, cChipId, graph);
    }

    private int getHopsUseBFS(Long aChipId, Long cChipId, Map<Long, List<Long>> graph) {
        Queue<Pair<Long, Integer>> queue = new LinkedList<>();
        Set<Long> visited = new HashSet<>();
        queue.offer(new MutablePair<>(aChipId, 0));
        visited.add(aChipId);
        while (!queue.isEmpty()) {
            Pair<Long, Integer> pair = queue.poll();
            Long chipId = pair.getKey();
            Integer hops = pair.getValue();
            if (chipId.equals(cChipId)) {
                return hops;
            }
            for (Long neighborId : graph.getOrDefault(chipId, new ArrayList<>())) {
                if (!visited.contains(neighborId) && !isInQueue(queue, neighborId)) {
                    queue.offer(new MutablePair<>(neighborId, hops + 1));
                    visited.add(neighborId);
                }
            }
        }

        return -1;
    }

    private Map<Long, List<Long>> getNetGraph() {
        List<Net> nets = netRepository.findAll();
        Map<Long, List<Long>> graph = new HashMap<>();
        for (Net net : nets) {
            Long startId = net.getStartComponentInstance().getId();
            Long endId = net.getEndComponentInstance().getId();
            graph.putIfAbsent(startId, new ArrayList<>());
            graph.putIfAbsent(endId, new ArrayList<>());
            graph.get(startId).add(endId);
            graph.get(endId).add(startId);
        }
        return graph;
    }

    private boolean isInQueue(Queue<Pair<Long, Integer>> queue, Long chipId) {
        return queue.stream().anyMatch(pair -> pair.getKey().equals(chipId));
    }
}
