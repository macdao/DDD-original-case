package com.logos.ddd.pcb.v2.domain.net;


import com.logos.ddd.pcb.v2.domain.component.instance.Pin;


public record Net(Id id, Pin startPin, Pin endPin) {

    public record Id(long id) {

    }
}