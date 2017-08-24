package com.rafalpiotrowski.cb.domain.graph;

import com.rafalpiotrowski.cb.domain.graph.NodeWeight;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class NodeWeightTest {

    @Test
    public void shouldThrowExceptionGivenNegativeWeight() {
        // given
        Integer weight = -10;

        // when
        Throwable exception = catchThrowable(() -> {
            new NodeWeight(weight);
        });

        // then
        assertThat(exception).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldCreateNewNodeWeightGivenZeroWeight() {
        // given
        Integer weight = 0;

        // when
        NodeWeight nodeWeight = new NodeWeight(weight);

        // then
        assertThat(nodeWeight.getAsInteger()).isEqualTo(weight);
    }
}