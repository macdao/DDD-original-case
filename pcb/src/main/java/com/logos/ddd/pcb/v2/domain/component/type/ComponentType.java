package com.logos.ddd.pcb.v2.domain.component.type;

import java.util.List;


public class ComponentType {
    private final String name;
    private final List<PinType> pinTypes;

    ComponentType(String name, List<PinType> pinTypes) {
        this.name = name;
        this.pinTypes = pinTypes;
    }

    public List<Integer> getOutputPins(int i) {
        PinType pin = pinTypes.stream().filter(pinType -> pinType.getNumber() == i).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Pin not found"));
        return pin.getOutPutPinNumber();
    }
}
