package com.logos.ddd.pcb.v1.application;

import com.logos.ddd.pcb.v1.domain.LinkChipService;
import org.springframework.stereotype.Service;

@Service
public class DrawService {

    private final LinkChipService linkChipService;

    public DrawService(LinkChipService linkChipService) {
        this.linkChipService = linkChipService;
    }

    public void linkChip(Long startChipId, Long endChipId) {
         linkChipService.linkChip(startChipId, endChipId);
    }
}
