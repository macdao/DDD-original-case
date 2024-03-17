package com.logos.ddd.pcb.v2.domain.component.type;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ComponentType {
    private final String name;
    private final Map<Integer, List<Integer>> pushMap;
    private final List<Integer> pins;

    ComponentType(String name, Map<Integer, List<Integer>> pushMap, List<Integer> pins) {
        this.name = name;
        this.pushMap = pushMap;
        this.pins = pins;
    }

    public List<Integer> getOutputPins(int i) {
        return pushMap.get(i);
    }

    public String name() {
        return name;
    }

    public Map<Integer, List<Integer>> pushMap() {
        return pushMap;
    }

    public List<Integer> pins() {
        return pins;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ComponentType) obj;
        return Objects.equals(this.name, that.name) &&
                Objects.equals(this.pushMap, that.pushMap) &&
                Objects.equals(this.pins, that.pins);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, pushMap, pins);
    }

    @Override
    public String toString() {
        return "ComponentType[" +
                "name=" + name + ", " +
                "pushMap=" + pushMap + ", " +
                "pins=" + pins + ']';
    }

}
