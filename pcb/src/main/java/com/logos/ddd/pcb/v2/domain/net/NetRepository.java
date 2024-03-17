package com.logos.ddd.pcb.v2.domain.net;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface NetRepository {
    Net save(Net net);

    List<Net> findAll();
}
