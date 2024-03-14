package com.logos.ddd.pcb.v1.domain;


import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LinkChipService {

    private final WireRepository wireRepository;
    private final ChipRepository chipRepository;

    public LinkChipService(WireRepository wireRepository, ChipRepository chipRepository) {
        this.wireRepository = wireRepository;
        this.chipRepository = chipRepository;
    }


    public Wire linkChip(Long startChipId, Long endChipId) {
        Wire wire = new Wire();
        Chip startChip = chipRepository.find(startChipId);
        Chip endChip = chipRepository.find(endChipId);
        wire.setStartChip(startChip);
        wire.setEndChip(endChip);
        return wireRepository.save(wire);
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
        List<Wire> wires = wireRepository.findAll();
        Map<Long, List<Long>> graph = new HashMap<>();
        for (Wire wire : wires) {
            Long startId = wire.getStartChip().getId();
            Long endId = wire.getEndChip().getId();
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
