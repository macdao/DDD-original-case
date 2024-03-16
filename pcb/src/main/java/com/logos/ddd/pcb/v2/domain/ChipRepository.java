package com.logos.ddd.pcb.v2.domain;


import org.springframework.stereotype.Component;

@Component
public interface ChipRepository {
    ComponentInstance find(Long id);
}
