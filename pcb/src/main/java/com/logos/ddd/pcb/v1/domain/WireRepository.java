package com.logos.ddd.pcb.v1.domain;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface WireRepository {
    Wire save(Wire wire);

    List<Wire> findAll();
}
