package com.logos.ddd.pcb.v2.domain.component.instance;

import com.logos.ddd.pcb.v2.domain.component.type.ComponentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComponentInstance {
    private Long id;
    private ComponentType type;

    public List<Integer> getOutPins(int pinNumber) {
        return type.getOutputPins(pinNumber);
    }

    public Pin pin(int pinNumber) {
        return new Pin(id, pinNumber);
    }
}
