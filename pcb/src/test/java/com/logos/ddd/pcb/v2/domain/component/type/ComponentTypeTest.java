package com.logos.ddd.pcb.v2.domain.component.type;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ComponentTypeTest {

    //根据元件输入的引脚获得输出的引脚
    @Test
    void should_return_output_pins_when_get_pushes_given_component_type() {
        //given
        PinType pinType2 = new PinType(2, List.of());
        PinType pinType3 = new PinType(3, List.of());
        PinType pinType1 = new PinType(1, List.of(2,3));
        ComponentType componentType = new ComponentType("A", List.of(pinType1, pinType2, pinType3));
        //when
        List<Integer> outputPins = componentType.getOutputPins(1);
        //then
        assertEquals(List.of(2, 3), outputPins);
    }

}