package com.logos.ddd.pcb.v1.domain;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

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
}