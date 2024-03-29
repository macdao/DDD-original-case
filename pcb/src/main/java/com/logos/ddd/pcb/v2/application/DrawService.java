package com.logos.ddd.pcb.v2.application;

import com.logos.ddd.pcb.v2.domain.component.instance.ComponentInstance;
import com.logos.ddd.pcb.v2.domain.component.instance.Pin;
import com.logos.ddd.pcb.v2.domain.component.type.ComponentType;
import com.logos.ddd.pcb.v2.domain.net.Net;
import org.springframework.stereotype.Service;

@Service
public class DrawService {

    private final ComponentInstanceRepository componentInstanceRepository;

    private final NetRepository netRepository;


    public DrawService(ComponentInstanceRepository componentInstanceRepository, NetRepository netRepository) {
        this.componentInstanceRepository = componentInstanceRepository;
        this.netRepository = netRepository;
    }

    public void linkChip(Long startComponentInstanceId, int startPinNumber, Long endComponentInstanceId, int endPinNumber) {
        Pin startPin = new Pin(new ComponentInstance.Id(startComponentInstanceId), startPinNumber);
        Pin endPin = new Pin(new ComponentInstance.Id(endComponentInstanceId), endPinNumber);
        // todo generate net id
        Net net = startPin.linkTo(1L, endPin);

        netRepository.save(net);
    }

    public ComponentInstance createComponentInstance(ComponentType type) {
        // todo generate id
        ComponentInstance componentInstance = new ComponentInstance(new ComponentInstance.Id(1L), type);
        componentInstanceRepository.save(componentInstance);
        return componentInstance;
    }
}
