package com.logos.ddd.pcb.v2.domain.net;


import com.logos.ddd.pcb.v2.domain.component.instance.ComponentInstance;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Net {

    private Long id;
    private ComponentInstance startComponentInstance;
    private ComponentInstance endComponentInstance;
}
