package com.logos.ddd.pcb.v2.domain.net;


import com.logos.ddd.pcb.v2.domain.component.instance.ComponentInstance;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Net {

    private Long id;
    private ComponentInstance startComponentInstance;
    private ComponentInstance endComponentInstance;
    private int startPinNumber;
    private int endPinNumber;
}
