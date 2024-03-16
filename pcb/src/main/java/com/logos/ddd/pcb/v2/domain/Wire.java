package com.logos.ddd.pcb.v2.domain;

import com.logos.ddd.pcb.v1.domain.Chip;
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
    private com.logos.ddd.pcb.v1.domain.Chip startChip;
    private Chip endChip;
}
