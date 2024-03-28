package com.logos.ddd.pcb.v2.domain.component.instance;

import lombok.Getter;

import java.util.List;

@Getter
public class PinInstance {
    private int number;

    public PinInstance(int number) {
        this.number = number;
    }
}
