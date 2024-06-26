package com.logos.ddd.pcb.v2.domain;


import com.logos.ddd.pcb.v2.domain.component.instance.ComponentInstance;
import com.logos.ddd.pcb.v2.domain.component.instance.Pin;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HopCalculatorTest {


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
        HopCalculator hopCalculator = new HopCalculator();
        Map<Pin, List<Pin>> graph = Map.of(
                new Pin(new ComponentInstance.Id(1L), 2), List.of(new Pin(new ComponentInstance.Id(3L), 2)),
                new Pin(new ComponentInstance.Id(1L), 3), List.of(new Pin(new ComponentInstance.Id(2L), 1)),
                new Pin(new ComponentInstance.Id(2L), 1), List.of(new Pin(new ComponentInstance.Id(2L), 3)),
                new Pin(new ComponentInstance.Id(2L), 3), List.of(new Pin(new ComponentInstance.Id(3L), 3)),
                new Pin(new ComponentInstance.Id(3L), 2), List.of(new Pin(new ComponentInstance.Id(3L), 4)),
                new Pin(new ComponentInstance.Id(3L), 3), List.of(new Pin(new ComponentInstance.Id(3L), 1))
        );

        //when
        int hops = hopCalculator.getHops(new Pin(new ComponentInstance.Id(1L), 3), new Pin(new ComponentInstance.Id(3L), 3), graph);
        //then
        assertEquals(2, hops);
    }
}