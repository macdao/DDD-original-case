package com.logos.ddd.pcb.v2.domain.component.instance;

import com.logos.ddd.pcb.v2.domain.component.type.ComponentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class ComponentInstance {
    private final Long id;
    private final ComponentType type;

    public Pin pin(int pinNumber) {
        return new Pin(id, pinNumber);
    }

    public List<Pin> getOutPins(Pin pin) {
        return type.getOutputPins(pin.pinNumber()).stream().map(number -> new Pin(id, number)).toList();
    }
}
