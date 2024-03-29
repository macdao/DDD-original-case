package com.logos.ddd.pcb.v2.domain.component.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;


@Getter
@RequiredArgsConstructor
public class ComponentType {
    private final String name;
    private final Map<Integer, List<Integer>> mapOfPushes;

    public List<Integer> getOutputPins(int i) {
        return mapOfPushes.get(i);
    }
}
