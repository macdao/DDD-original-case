package com.logos.ddd.pcb.v2.domain;


import com.logos.ddd.pcb.v2.domain.component.instance.ComponentInstance;
import com.logos.ddd.pcb.v2.domain.component.instance.ComponentInstanceFactory;
import com.logos.ddd.pcb.v2.domain.component.instance.ComponentInstanceRepository;
import com.logos.ddd.pcb.v2.domain.component.instance.PinInstance;
import com.logos.ddd.pcb.v2.domain.component.type.ComponentType;
import com.logos.ddd.pcb.v2.domain.component.type.PinType;
import com.logos.ddd.pcb.v2.domain.net.Net;
import com.logos.ddd.pcb.v2.domain.net.NetRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DrawServiceTest {


    private ComponentInstanceRepository componentInstanceRepository = Mockito.mock(ComponentInstanceRepository.class);
    private NetRepository netRepository = Mockito.mock(NetRepository.class);

    @Test
    void should_link_two_component_instance_when_link_given_two_pin_instance() {


        Long startComponentInstanceId = 1L;
        Long endComponentInstanceId = 2L;
        int startPinNumber = 2;
        int endPinNumber = 1;
        LinkChipService drawService = new LinkChipService(netRepository, componentInstanceRepository);
        ArgumentCaptor<Net> netCaptor = ArgumentCaptor.forClass(Net.class);
        PinType pinType2 = new PinType(2, List.of());
        PinType pinType3 = new PinType(3, List.of());
        PinType pinType1 = new PinType(1, List.of(2, 3));
        ComponentType componentType = new ComponentType("A", List.of(pinType1, pinType2, pinType3));
        ComponentInstance startComponentInstance = ComponentInstance.builder().id(1L).type(componentType).build();
        ComponentInstance endComponentInstance = ComponentInstance.builder().id(2L).type(componentType).build();
        Mockito.when(componentInstanceRepository.find(startComponentInstanceId)).thenReturn(startComponentInstance);
        Mockito.when(componentInstanceRepository.find(endComponentInstanceId)).thenReturn(endComponentInstance);

        // when
        drawService.link(startComponentInstanceId, startPinNumber, endComponentInstanceId, endPinNumber);

        // then
        Mockito.verify(netRepository).save(netCaptor.capture());
        Net savedNet = netCaptor.getValue();
        assertEquals(startComponentInstance, savedNet.getStartComponentInstance());
        assertEquals(2, savedNet.getStartPinNumber());
        assertEquals(1, savedNet.getEndPinNumber());
    }


    //     ┌───────┐         ┌────────┐
//   1┌┐  A    ┌┐3     1┌┐   A    ┌┐3
//    └┘ 1->3  └───────>└┘ 1->3   └│
//     │ 4->2  │         │ 4->2   ││
//   2┌┐       ┌┐4     2┌┐        ┌│4
//    │┘       └┘       └┘        └│
//    │└───────┘         └────────┘│
//    │                            │
//    │                            │
//    │                            │
//    │         ┌───────┐          │
//    │       1┌┐       ┌┐3        │
//    │        └┘  3->1 └<─────────┘
//    │         │  2->4 │
//    │       2┌┐       ┌┐4
//    └────────>┘       └┘
//              └───────┘
    @Test
    void should_return_correct_hop_count_when_link_given_three_component_instances() {
        // Arrange
        PinType pinType1 = new PinType(1, List.of(3));
        PinType pinType2 = new PinType(2, List.of());
        PinType pinType3 = new PinType(3, List.of());
        PinType pinType4 = new PinType(4, List.of(2));
        PinType pinType11 = new PinType(2, List.of());
        PinType pinType12 = new PinType(3, List.of());
        PinType pinType13 = new PinType(3, List.of(1));
        PinType pinType14 = new PinType(2, List.of(4));
        ComponentType componentType1 = new ComponentType("A", List.of(pinType1, pinType2, pinType3, pinType4));
        ComponentType componentType2 = new ComponentType("B", List.of(pinType11, pinType12, pinType13, pinType14));
        ComponentInstance firstComponentInstance = ComponentInstance.builder()
                .id(1L)
                .type(componentType1)
                .pins(componentType1.getPinTypes().stream().map(PinInstance::new).collect(Collectors.toList()))
                .build();

        ComponentInstance secondComponentInstance = ComponentInstance.builder()
                .id(2L)
                .type(componentType1)
                .pins(componentType1.getPinTypes().stream().map(PinInstance::new).collect(Collectors.toList()))
                .build();

        ComponentInstance thirdComponentInstance = ComponentInstance.builder()
                .id(3L)
                .type(componentType2)
                .pins(componentType2.getPinTypes().stream().map(PinInstance::new).collect(Collectors.toList()))
                .build();
        LinkChipService drawService = new LinkChipService(netRepository, componentInstanceRepository);
        List<Net> expectedNets = List.of(
                new Net(1L, firstComponentInstance, secondComponentInstance, 3, 1),
                new Net(2L, secondComponentInstance, thirdComponentInstance, 3, 3),
                new Net(3L, firstComponentInstance, thirdComponentInstance, 2, 2)
        );
        Mockito.when(netRepository.findAll()).thenReturn(expectedNets);

        //when

        int hops = drawService.getHops(1L, 3, 3L, 3);
        //then
        assertEquals(2, hops);
    }
}