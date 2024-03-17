package com.logos.ddd.pcb.v2.domain.component.type;

import lombok.Getter;

import java.util.List;

@Getter
public class PinType {
    private  int number;
    private  List<Integer> outPutPinNumber;


    public PinType(int number, List<Integer> outPutPinNumber) {
        this.number = number;
        this.outPutPinNumber = outPutPinNumber;
    }
}
