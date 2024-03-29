package com.logos.ddd.pcb.v2.domain.component.instance;

import com.logos.ddd.pcb.v2.domain.net.Net;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class PinTest {
    @Test
    void should_link_two_component_instance_when_link_given_two_pin_instance() {
        Pin pin1 = new Pin(new ComponentInstance.Id(1L), 1);
        Pin pin2 = new Pin(new ComponentInstance.Id(2L), 1);
        Net net = pin1.linkTo(new Net.Id(3L), pin2);

        assertThat(net.startPin()).isEqualTo(pin1);
        assertThat(net.endPin()).isEqualTo(pin2);
    }
}