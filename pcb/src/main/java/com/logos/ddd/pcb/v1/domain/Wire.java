package com.logos.ddd.pcb.v1.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Wire {

    private Long id;
    private Chip startChip;
    private Chip endChip;
}
