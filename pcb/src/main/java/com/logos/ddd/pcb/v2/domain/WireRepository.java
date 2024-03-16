package com.logos.ddd.pcb.v2.domain;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface WireRepository {
    Net save(Net net);

    List<Net> findAll();
}
