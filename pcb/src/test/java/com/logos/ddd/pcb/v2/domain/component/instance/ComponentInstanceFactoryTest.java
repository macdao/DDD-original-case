package com.logos.ddd.pcb.v2.domain.component.instance;

import com.logos.ddd.pcb.v2.domain.component.type.ComponentType;
import com.logos.ddd.pcb.v2.domain.component.type.PinType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ComponentInstanceFactoryTest {

    @Test
    void should_create_component_instance_by_component_type_when_create_component_instance_by_type() {
        //given
        PinType pinType1 = new PinType(1, List.of());
        PinType pinType2 = new PinType(2, List.of(1));
        ComponentType componentType = new ComponentType("A", List.of(pinType1, pinType2));
        //when
        ComponentInstance componentInstance = ComponentInstanceFactory.createByType(componentType);
        //then
        assertEquals("A", componentInstance.getType().getName());
        assertEquals(2, componentInstance.getPins().size());
        assertEquals(1, componentInstance.getPins().get(0).getNumber());
        assertEquals(2, componentInstance.getPins().get(1).getNumber());
        assertEquals(List.of(1), componentInstance.getOutPins(2));
    }
}