package com.logos.ddd.pcb.v1.domain;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DrawServiceTest {


    private ChipRepository chipRepository = Mockito.mock(ChipRepository.class);
    private NetRepository netRepository = Mockito.mock(NetRepository.class);

    @Test
    void should_add_start_chip_and_end_chip_when_link_chip_given_two_chip_to_a_net() {
        // given
        Long startChipId = 1L;
        Long endChipId = 2L;

        Chip startChip = Chip.builder().id(1L).type("A").build();
        Chip endChip = Chip.builder().id(2L).type("B").build();
        Net net = Net.builder().id(1L).build();
        LinkChipService drawService = new LinkChipService(netRepository, chipRepository);
        ArgumentCaptor<Net> netCaptor = ArgumentCaptor.forClass(Net.class);
        Mockito.when(chipRepository.find(startChipId)).thenReturn(startChip);
        Mockito.when(chipRepository.find(endChipId)).thenReturn(endChip);

        // when
        drawService.linkChip(startChipId, endChipId);

        // then
        Mockito.verify(netRepository).save(netCaptor.capture());
        Net savedNet = netCaptor.getValue();
        assertEquals(startChip, savedNet.getStartChip());
        assertEquals(endChip, savedNet.getEndChip());
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

        Chip aChip = Chip.builder().id(aChipId).type("A").build();
        Chip bChip = Chip.builder().id(bChipId).type("B").build();
        Chip cChip = Chip.builder().id(cChipId).type("A").build();
        Net netBetweenAAndB = Net.builder().id(1L).startChip(aChip).endChip(bChip).build();
        Net netBetweenBAndC = Net.builder().id(2L).startChip(bChip).endChip(cChip).build();
        LinkChipService linkChipService = new LinkChipService(netRepository, chipRepository);

        Mockito.when(netRepository.findAll()).thenReturn(List.of(netBetweenAAndB,netBetweenBAndC));

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