package com.logos.ddd.pcb.v2.domain.component.type;

import lombok.Getter;

import java.util.List;


@Getter
public class ComponentType {
    private String name;
    private List<PinType> pinTypes;

    public ComponentType(String name, List<PinType> pinTypes) {
        this.name = name;
        this.pinTypes = pinTypes;
    }

    public List<Integer> getOutputPins(int i) {
        PinType pin = pinTypes.stream().filter(pinType -> pinType.getNumber() == i).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Pin not found"));
        return pin.getOutPutPinNumber();
    }
}
