package com.logos.ddd.pcb.v2.domain;


import com.logos.ddd.pcb.v2.domain.component.instance.ComponentInstance;
import com.logos.ddd.pcb.v2.domain.component.instance.ComponentInstanceRepository;
import com.logos.ddd.pcb.v2.domain.component.type.ComponentType;
import com.logos.ddd.pcb.v2.domain.component.type.PinType;
import com.logos.ddd.pcb.v2.domain.net.Net;
import com.logos.ddd.pcb.v2.domain.net.NetRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.List;

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


//             ┌╶╶╶╶╶┐       ┌╶╶╶╶╶┐     ┌╶╶╶╶╶┐
//             ╎     ╎       ╎     ╎     ╎     ╎
//             ╎   A ╎       ╎  B  ╎     ╎ C   ╎
//             ╎     ╶╶╶╶╶╶╶╶╶     ╶╶╶╶╶╶╶     ╎
//             ╎     ╎       ╎     ╎     ╎     ╎
//             └╶╶╶╶╶┘       └╶╶╶╶╶┘     └╶╶╶╶╶┘

//    @Test
//    void should_return_hops_of_two_chip_when_get_hops_given_two_chip_id() {
//        Long aChipId = 1L;
//        Long bChipId = 2L;
//        Long cChipId = 3L;
//        int expectedHops = 2; // replace with the expected number of hops
//
//        ComponentInstance aComponentInstance = ComponentInstance.builder().id(aChipId).type("A").build();
//        ComponentInstance bComponentInstance = ComponentInstance.builder().id(bChipId).type("B").build();
//        ComponentInstance cComponentInstance = ComponentInstance.builder().id(cChipId).type("A").build();
//        Net netBetweenAAndB = Net.builder().id(1L).startComponentInstance(aComponentInstance).endComponentInstance(bComponentInstance).build();
//        Net netBetweenBAndC = Net.builder().id(2L).startComponentInstance(bComponentInstance).endComponentInstance(cComponentInstance).build();
//        LinkChipService linkChipService = new LinkChipService(netRepository, componentInstanceRepository);
//
//        Mockito.when(netRepository.findAll()).thenReturn(List.of(netBetweenAAndB, netBetweenBAndC));
//
//        // when
//        int actualHops = linkChipService.getHops(aChipId, cChipId);
//
//        // then
//        assertEquals(expectedHops, actualHops);
//    }

    // 这样可咋办啊？
//            ┌╶╶╶╶╶┐      ┌╶╶╶╶╶┐
//            ╎     ╎      ╎     ╎
//            ╎     ╎      ╎     ╎
//            ╎  A  ╶╶╶╶╶╶╶╎  B  ╎
//            ╎     ╎      ╎     ╎
//            └╶╶ ╶╶┘      └╶╶╶  ┘
//               ╎             ╎
//               ╎   ┌╶╶╶╶╶┐   ╎
//               ╎   ╎     ╎   ╎
//               └╶╶╶┼     ┼╶╶╶┘
//                   ╎  C  ╎
//                   ╎     ╎
//                   └╶╶╶╶╶┘

}