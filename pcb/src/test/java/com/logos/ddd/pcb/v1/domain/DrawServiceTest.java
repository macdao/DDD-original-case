package com.logos.ddd.pcb.v1.domain;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.CapturesArguments;

import static org.junit.jupiter.api.Assertions.*;

class DrawServiceTest {


    private ChipRepository chipRepository = Mockito.mock(ChipRepository.class);
    private NetRepository netRepository = Mockito.mock(NetRepository.class);

    @Test
    void should_add_start_chip_and_end_chip_when_link_chip_given_two_chip_to_a_net() {
        // given
        Chip startChip = Chip.builder().id(1L).type("A").build();
        Chip endChip = Chip.builder().id(2L).type("B").build();
        Net net = Net.builder().id(1L).build();
        DrawService drawService = new DrawService(netRepository, chipRepository);
        ArgumentCaptor<Net> netCaptor = ArgumentCaptor.forClass(Net.class);
        // when
        drawService.linkChip(net, startChip, endChip);

        // then
        Mockito.verify(netRepository).save(netCaptor.capture());
        Net savedNet = netCaptor.getValue();
        assertEquals(startChip, savedNet.getStartChip());
        assertEquals(endChip, savedNet.getEndChip());
    }
}