package com.logos.ddd.pcb.v2.domain;

import com.logos.ddd.pcb.v1.domain.Wire;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface WireRepository {
    com.logos.ddd.pcb.v1.domain.Wire save(com.logos.ddd.pcb.v1.domain.Wire wire);

    List<Wire> findAll();
}
