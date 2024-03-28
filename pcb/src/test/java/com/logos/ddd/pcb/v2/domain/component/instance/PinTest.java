package com.logos.ddd.pcb.v2.domain.component.instance;

import com.logos.ddd.pcb.v2.domain.net.Net;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class PinTest {
    @Test
    void should_link_two_component_instance_when_link_given_two_pin_instance() {
        Pin pin1 = new Pin(1L, 1);
        Pin pin2 = new Pin(2L, 1);
        Net net = pin1.linkTo(3L, pin2);

        assertThat(net.startPin()).isEqualTo(pin1);
        assertThat(net.endPin()).isEqualTo(pin2);
    }
}