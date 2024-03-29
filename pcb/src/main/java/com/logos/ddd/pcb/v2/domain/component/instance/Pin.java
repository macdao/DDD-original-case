package com.logos.ddd.pcb.v2.domain.component.instance;

import com.logos.ddd.pcb.v2.domain.net.Net;

public record Pin(ComponentInstance.Id componentInstanceId, int pinNumber) {
    public Net linkTo(Net.Id NetId, Pin endPin) {
        return new Net(NetId, this, endPin);
    }

    public boolean isOnSameComponentInstance(Pin neighbor) {
        return componentInstanceId.equals(neighbor.componentInstanceId());
    }
}
