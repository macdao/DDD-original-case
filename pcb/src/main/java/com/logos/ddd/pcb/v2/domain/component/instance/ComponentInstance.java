package com.logos.ddd.pcb.v2.domain.component.instance;

import com.logos.ddd.pcb.v2.domain.component.type.ComponentType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class ComponentInstance {
    private final Id id;
    private final ComponentType type;

    public Pin pin(int pinNumber) {
        return new Pin(id, pinNumber);
    }

    public List<Pin> getOutPins(Pin pin) {
        return type.getOutputPins(pin.pinNumber()).stream().map(number -> new Pin(id, number)).toList();
    }

    public record Id(long value) {

    }
}
