package com.logos.ddd.pcb.v2.application;

import com.logos.ddd.pcb.v2.domain.component.instance.ComponentInstance;
import com.logos.ddd.pcb.v2.domain.component.instance.Pin;
import com.logos.ddd.pcb.v2.domain.component.type.ComponentType;
import com.logos.ddd.pcb.v2.domain.net.Net;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class GraphFactoryTest {


    private ComponentInstanceRepository componentInstanceRepository = Mockito.mock(ComponentInstanceRepository.class);
    private NetRepository netRepository = Mockito.mock(NetRepository.class);

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
    void should_return_correct_graph_when_link_given_three_component_instances() {
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
        ComponentInstance firstComponentInstance = new ComponentInstance(new ComponentInstance.Id(1L), componentType1);
        when(componentInstanceRepository.find(firstComponentInstance.getId())).thenReturn(firstComponentInstance);

        ComponentInstance secondComponentInstance = new ComponentInstance(new ComponentInstance.Id(2L), componentType1);
        when(componentInstanceRepository.find(secondComponentInstance.getId())).thenReturn(secondComponentInstance);

        ComponentInstance thirdComponentInstance = new ComponentInstance(new ComponentInstance.Id(3L), componentType2);

        when(componentInstanceRepository.find(thirdComponentInstance.getId())).thenReturn(thirdComponentInstance);

        List<Net> expectedNets = List.of(
                new Net(1L, firstComponentInstance.pin(3), secondComponentInstance.pin(1)),
                new Net(2L, secondComponentInstance.pin(3), thirdComponentInstance.pin(3)),
                new Net(3L, firstComponentInstance.pin(2), thirdComponentInstance.pin(2))
        );
        when(netRepository.findAll()).thenReturn(expectedNets);

        //when

        GraphFactory graphFactory = new GraphFactory(netRepository, componentInstanceRepository);

        Map<Pin, List<Pin>> graph = graphFactory.buildGraph();

        //then
        assertThat(graph.size()).isEqualTo(6);
        assertThat(graph.get(firstComponentInstance.pin(1))).isNull();
        List<Pin> outputPinListOf12 = graph.get(firstComponentInstance.pin(2));
        assertThat(outputPinListOf12.size()).isEqualTo(1);
        assertThat(outputPinListOf12.get(0)).isEqualTo(thirdComponentInstance.pin(2));
        List<Pin> outputPinListOf21 = graph.get(secondComponentInstance.pin(1));
        assertThat(outputPinListOf21.size()).isEqualTo(1);
        assertThat(outputPinListOf21.get(0)).isEqualTo(secondComponentInstance.pin(3));
        List<Pin> outputPinListOf33 = graph.get(thirdComponentInstance.pin(3));
        assertThat(outputPinListOf33.size()).isEqualTo(1);
        assertThat(outputPinListOf33.get(0)).isEqualTo(thirdComponentInstance.pin(1));
    }

}