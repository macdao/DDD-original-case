package com.logos.ddd.pcb.v2.domain;


import com.logos.ddd.pcb.v2.domain.component.instance.ComponentInstance;
import com.logos.ddd.pcb.v2.domain.component.instance.ComponentInstanceFactory;
import com.logos.ddd.pcb.v2.domain.component.instance.ComponentInstanceRepository;
import com.logos.ddd.pcb.v2.domain.component.type.ComponentType;
import com.logos.ddd.pcb.v2.domain.net.Net;
import com.logos.ddd.pcb.v2.domain.net.NetRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.List;
import java.util.Map;
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
        ComponentType componentType = new ComponentType("A", Map.of(
                1, List.of(2, 3),
                2, List.of(),
                3, List.of()
        ));
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
        ComponentType componentType1 = new ComponentType("A", Map.of(
                1, List.of(3),
                2, List.of(),
                3, List.of(),
                4, List.of(2)
        ));
        ComponentType componentType2 = new ComponentType("B", Map.of(
                2, List.of(4),
                3, List.of(1)
        ));
        ComponentInstance firstComponentInstance = ComponentInstance.builder()
                .id(1L)
                .type(componentType1)
                .build();

        ComponentInstance secondComponentInstance = ComponentInstance.builder()
                .id(2L)
                .type(componentType1)
                .build();

        ComponentInstance thirdComponentInstance = ComponentInstance.builder()
                .id(3L)
                .type(componentType2)
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