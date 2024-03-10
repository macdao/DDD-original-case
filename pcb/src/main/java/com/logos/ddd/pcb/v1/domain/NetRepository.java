package com.logos.ddd.pcb.v1.domain;

import org.springframework.stereotype.Component;

@Component
public interface NetRepository {
    Net save(Net net);
}
