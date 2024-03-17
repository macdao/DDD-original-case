package com.logos.ddd.pcb.v2.domain.component.instance;


import org.springframework.stereotype.Component;

@Component
public interface ComponentInstanceRepository {
    ComponentInstance find(Long id);
}
