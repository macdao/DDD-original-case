package com.logos.ddd.pcb.v1.domain;

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

        Chip startChip = Chip.builder().id(1L).type("A").build();
        Chip endChip = Chip.builder().id(2L).type("B").build();
        Wire wire = Wire.builder().id(1L).build();
        LinkChipService drawService = new LinkChipService(wireRepository, chipRepository);
        ArgumentCaptor<Wire> netCaptor = ArgumentCaptor.forClass(Wire.class);
        Mockito.when(chipRepository.find(startChipId)).thenReturn(startChip);
        Mockito.when(chipRepository.find(endChipId)).thenReturn(endChip);

        // when
        drawService.linkChip(startChipId, endChipId);

        // then
        Mockito.verify(wireRepository).save(netCaptor.capture());
        Wire savedWire = netCaptor.getValue();
        assertEquals(startChip, savedWire.getStartChip());
        assertEquals(endChip, savedWire.getEndChip());
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
        Wire wireBetweenAAndB = Wire.builder().id(1L).startChip(aChip).endChip(bChip).build();
        Wire wireBetweenBAndC = Wire.builder().id(2L).startChip(bChip).endChip(cChip).build();
        LinkChipService linkChipService = new LinkChipService(wireRepository, chipRepository);

        Mockito.when(wireRepository.findAll()).thenReturn(List.of(wireBetweenAAndB, wireBetweenBAndC));

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