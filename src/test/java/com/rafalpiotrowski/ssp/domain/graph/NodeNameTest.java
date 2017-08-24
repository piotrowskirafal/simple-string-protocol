package com.rafalpiotrowski.ssp.domain.graph;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class NodeNameTest {

    @Test
    public void shouldThrowExceptionGivenNameWithNonAlphanumericCharacters() {
        // given
        String name = "non_aplhanumeric_name";

        // when
        Throwable exception = catchThrowable(() -> {
            new NodeName(name);
        });

        // then
        assertThat(exception).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldThrowExceptionGivenNullName() {
        // given
        String name = null;

        // when
        Throwable exception = catchThrowable(() -> {
            new NodeName(name);
        });

        // then
        assertThat(exception).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldCreateNewNodeName() {
        // given
        String name = "THIS-IS-CORRECT-ALPHANUMERIC-NODE-NAME-12345";

        // when
        NodeName nodeName = new NodeName(name);


        // then
        assertThat(nodeName.getAsString()).isEqualTo(name);
    }
}