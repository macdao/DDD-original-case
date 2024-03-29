package com.logos.ddd.pcb.v2.application;


import com.logos.ddd.pcb.v2.domain.component.instance.ComponentInstance;
import org.springframework.stereotype.Component;

@Component
public interface ComponentInstanceRepository {
    ComponentInstance find(ComponentInstance.Id id);

    ComponentInstance save(ComponentInstance componentInstance);
}
