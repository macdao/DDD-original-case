package com.logos.ddd.pcb.v2.domain.component.type;

import lombok.Getter;

import java.util.List;
import java.util.Map;


@Getter
public class ComponentType {
    private String name;
    private Map<Integer, List<Integer>> mapOfPushes;

    public ComponentType(String name, Map<Integer, List<Integer>> mapOfPushes) {
        this.name = name;
        this.mapOfPushes = mapOfPushes;
    }

    public List<Integer> getOutputPins(int i) {
        return mapOfPushes.get(i);
    }

    public List<PinType> getPinTypes() {
        return mapOfPushes.entrySet().stream().map(entry -> new PinType(entry.getKey(), entry.getValue())).toList();
    }
}
