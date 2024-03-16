package com.logos.ddd.pcb.v2.domain;

import com.logos.ddd.pcb.v1.domain.Chip;
import org.springframework.stereotype.Component;

@Component
public interface ChipRepository {
    Chip find(Long id);
}
