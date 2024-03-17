package com.logos.ddd.pcb.v2.domain.component.instance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComponentInstance {
    private Long id;
    private String type;
}
