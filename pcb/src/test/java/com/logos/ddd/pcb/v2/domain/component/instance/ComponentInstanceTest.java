package com.logos.ddd.pcb.v2.domain.component.instance;

import com.logos.ddd.pcb.v2.domain.component.type.ComponentType;
import org.junit.jupiter.api.Test;

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
}