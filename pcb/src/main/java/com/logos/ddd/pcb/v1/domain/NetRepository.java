package com.logos.ddd.pcb.v1.domain;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface NetRepository {
    Net save(Net net);

    List<Net> findAll();
}
