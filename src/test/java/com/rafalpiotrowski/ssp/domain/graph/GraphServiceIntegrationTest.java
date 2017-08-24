package com.rafalpiotrowski.ssp.domain.graph;

import com.rafalpiotrowski.ssp.infrastructure.config.App;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class GraphServiceIntegrationTest {

    @Autowired
    private GraphService graphService;

    @Test
    public void shouldNotAddDuplicatedNode() {
        // given
        NodeName nodeName = new NodeName("FOO-101");
        graphService.addNode(nodeName);

        // when
        Throwable exception = catchThrowable(() -> {
            graphService.addNode(nodeName);
        });

        // then
        assertThat(exception).isInstanceOf(GraphException.class);
    }

    @Test
    public void shouldAddDuplicatedEdge() {
        // given
        NodeName fooNode = new NodeName("FOO-102");
        NodeName barNode = new NodeName("BAR-102");
        graphService.addNode(fooNode);
        graphService.addNode(barNode);

        // when
        Throwable exception = catchThrowable(() -> {
            graphService.addEdge(fooNode, barNode, new NodeWeight(1));
        });

        // then
        assertThat(exception).isNull();
    }

    @Test
    public void shouldNotReturnErrorWhenRemovingNonExistingEdge() {
        // given
        NodeName fooNode = new NodeName("FOO-103");
        NodeName barNode = new NodeName("BAR-103");
        graphService.addNode(fooNode);
        graphService.addNode(barNode);

        // when
        Throwable exception = catchThrowable(() -> {
            graphService.removeEdge(fooNode, barNode);
        });

        // then
        assertThat(exception).isNull();
    }

    @Test
    public void shouldThrowExceptionWhenRemovingNonExistingNode() {
        // given
        NodeName nodeName = new NodeName("this-node-does-not-exist");

        // when
        Throwable exception = catchThrowable(() -> {
            graphService.removeNode(nodeName);
        });

        // then
        assertThat(exception).isInstanceOf(GraphException.class);
    }
}