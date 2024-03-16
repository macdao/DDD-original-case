package com.logos.ddd.pcb.v2.domain;


import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DrawServiceTest {


    private ChipRepository chipRepository = Mockito.mock(ChipRepository.class);
    private WireRepository wireRepository = Mockito.mock(WireRepository.class);

    @Test
    void should_add_start_chip_and_end_chip_when_link_chip_given_two_chip_to_a_net() {
        // given
        Long startChipId = 1L;
        Long endChipId = 2L;

        ComponentInstance startComponentInstance = ComponentInstance.builder().id(1L).type("A").build();
        ComponentInstance endComponentInstance = ComponentInstance.builder().id(2L).type("B").build();
        LinkChipService drawService = new LinkChipService(wireRepository, chipRepository);
        ArgumentCaptor<Net> netCaptor = ArgumentCaptor.forClass(Net.class);
        Mockito.when(chipRepository.find(startChipId)).thenReturn(startComponentInstance);
        Mockito.when(chipRepository.find(endChipId)).thenReturn(endComponentInstance);

        // when
        drawService.linkChip(startChipId, endChipId);

        // then
        Mockito.verify(wireRepository).save(netCaptor.capture());
        Net savedNet = netCaptor.getValue();
        assertEquals(startComponentInstance, savedNet.getStartComponentInstance());
        assertEquals(endComponentInstance, savedNet.getEndComponentInstance());
    }

//             ┌╶╶╶╶╶┐       ┌╶╶╶╶╶┐     ┌╶╶╶╶╶┐
//             ╎     ╎       ╎     ╎     ╎     ╎
//             ╎   A ╎       ╎  B  ╎     ╎ C   ╎
//             ╎     ╶╶╶╶╶╶╶╶╶     ╶╶╶╶╶╶╶     ╎
//             ╎     ╎       ╎     ╎     ╎     ╎
//             └╶╶╶╶╶┘       └╶╶╶╶╶┘     └╶╶╶╶╶┘

    @Test
    void should_return_hops_of_two_chip_when_get_hops_given_two_chip_id() {
        Long aChipId = 1L;
        Long bChipId = 2L;
        Long cChipId = 3L;
        int expectedHops = 2; // replace with the expected number of hops

        ComponentInstance aComponentInstance = ComponentInstance.builder().id(aChipId).type("A").build();
        ComponentInstance bComponentInstance = ComponentInstance.builder().id(bChipId).type("B").build();
        ComponentInstance cComponentInstance = ComponentInstance.builder().id(cChipId).type("A").build();
        Net netBetweenAAndB = Net.builder().id(1L).startComponentInstance(aComponentInstance).endComponentInstance(bComponentInstance).build();
        Net netBetweenBAndC = Net.builder().id(2L).startComponentInstance(bComponentInstance).endComponentInstance(cComponentInstance).build();
        LinkChipService linkChipService = new LinkChipService(wireRepository, chipRepository);

        Mockito.when(wireRepository.findAll()).thenReturn(List.of(netBetweenAAndB, netBetweenBAndC));

        // when
        int actualHops = linkChipService.getHops(aChipId, cChipId);

        // then
        assertEquals(expectedHops, actualHops);
    }

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