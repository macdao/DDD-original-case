package com.logos.ddd.pcb.v2.domain.component.type;

import lombok.Getter;

import java.util.List;

@Getter
public class PinType {
    private final int number;
    private final List<PinType> outputPinTypes;

    public PinType(int number, List<PinType> outputPinTypes) {
        this.number = number;
        this.outputPinTypes = outputPinTypes;
    }

    List<Integer> getOutPutPinNumber() {
        return getOutputPinTypes().stream().map(PinType::getNumber).toList();
    }
}
