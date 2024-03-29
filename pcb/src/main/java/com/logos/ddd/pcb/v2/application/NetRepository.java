package com.logos.ddd.pcb.v2.application;

import com.logos.ddd.pcb.v2.domain.net.Net;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface NetRepository {
    Net save(Net net);

    List<Net> findAll();
}
