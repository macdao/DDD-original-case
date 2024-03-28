package com.logos.ddd.pcb.v2.domain.component.instance;

import com.logos.ddd.pcb.v2.domain.component.type.PinType;
import lombok.Getter;

import java.util.List;

@Getter
public class PinInstance {
    private int number;

    public PinInstance(PinType type) {
        this.number = type.getNumber();
    }
}
