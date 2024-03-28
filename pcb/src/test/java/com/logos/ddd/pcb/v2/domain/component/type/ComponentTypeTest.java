package com.logos.ddd.pcb.v2.domain.component.type;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ComponentTypeTest {

    //根据元件输入的引脚获得输出的引脚
    @Test
    void should_return_output_pins_when_get_pushes_given_component_type() {
        //given
        ComponentType componentType = new ComponentType("A", Map.of(
                1, List.of(2, 3),
                2, List.of(),
                3, List.of()
        ));
        //when
        List<Integer> outputPins = componentType.getOutputPins(1);
        //then
        assertEquals(List.of(2, 3), outputPins);
    }

}