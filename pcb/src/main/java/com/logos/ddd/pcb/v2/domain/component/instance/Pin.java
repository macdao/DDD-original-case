package com.logos.ddd.pcb.v2.domain.component.instance;

import com.logos.ddd.pcb.v2.domain.net.Net;

public record Pin(Long componentInstanceId, int pinNumber) {
    public Net linkTo(long NetId, Pin endPin) {
        return new Net(NetId, this, endPin);
    }
}
