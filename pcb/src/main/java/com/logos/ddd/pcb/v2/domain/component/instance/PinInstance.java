package com.logos.ddd.pcb.v2.domain.component.instance;

import com.logos.ddd.pcb.v2.domain.component.type.PinType;
import lombok.Getter;

import java.util.List;

@Getter
public class PinInstance {
    private int number;
    private List<PinInstance> outPins;

    public PinInstance(int number, List<PinInstance> outPins) {
        this.number = number;
        this.outPins = outPins;
    }

    public PinInstance(PinType type) {
        this.number = type.getNumber();
        this.outPins = type.getOutputPinTypes().stream().map(PinInstance::new).toList();
    }

    List<Integer> getOutPutPinNumber() {
        return outPins.stream().map(PinInstance::getNumber).toList();
    }
}
