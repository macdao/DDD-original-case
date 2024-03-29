package com.logos.ddd.pcb.v2.domain.component.instance;

import com.logos.ddd.pcb.v2.domain.component.type.ComponentType;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
class ComponentInstanceTest {
    @Test
    void should_create_pin_object_when_pin() {
        long instanceId = 1L;
        int pinNumber = 6;
        ComponentInstance componentInstance = new ComponentInstance(instanceId, new ComponentType("A", Map.of()));

        Pin pin = componentInstance.pin(pinNumber);

        assertThat(pin.componentInstanceId()).isEqualTo(instanceId);
        assertThat(pin.pinNumber()).isEqualTo(pinNumber);
    }

    @Test
    void should_return_output_pin_when_get_out() {
        long instanceId = 1L;
        int inPinNumber = 1;
        int outPinNumber = 2;
        ComponentInstance componentInstance = new ComponentInstance(instanceId, new ComponentType("A", Map.of(
                inPinNumber, List.of(outPinNumber)
        )));

        List<Pin> out = componentInstance.getOutPins(new Pin(1L, 1));

        assertThat(out.size()).isEqualTo(1);
        assertThat(out.get(0).pinNumber()).isEqualTo(outPinNumber);
    }
}