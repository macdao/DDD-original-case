package com.logos.ddd.pcb.v2.domain.net;


import com.logos.ddd.pcb.v2.domain.component.instance.Pin;


public record Net(Long id, Pin startPin, Pin endPin) {

}