package com.logos.ddd.pcb.v2.application;

import com.logos.ddd.pcb.v1.domain.LinkChipService;
import com.logos.ddd.pcb.v2.domain.component.instance.ComponentInstance;
import com.logos.ddd.pcb.v2.domain.component.instance.ComponentInstanceFactory;
import com.logos.ddd.pcb.v2.domain.component.instance.ComponentInstanceRepository;
import com.logos.ddd.pcb.v2.domain.component.type.ComponentType;
import org.springframework.stereotype.Service;

@Service
public class DrawService {

    private final LinkChipService linkChipService;
    private final ComponentInstanceRepository componentInstanceRepository;

    public DrawService(LinkChipService linkChipService, ComponentInstanceRepository componentInstanceRepository) {
        this.linkChipService = linkChipService;
        this.componentInstanceRepository = componentInstanceRepository;
    }

    public void linkChip(Long startChipId, Long endChipId) {
        linkChipService.linkChip(startChipId, endChipId);
    }

    public ComponentInstance createComponentInstance(ComponentType type) {
        ComponentInstance componentInstance = ComponentInstanceFactory.createByType(type);
        componentInstanceRepository.save(componentInstance);
        return componentInstance;
    }
}
