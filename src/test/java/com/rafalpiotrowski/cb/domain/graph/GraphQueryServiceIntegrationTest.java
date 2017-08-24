package com.rafalpiotrowski.cb.domain.graph;

import com.rafalpiotrowski.cb.infrastructure.config.App;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class GraphQueryServiceIntegrationTest {

    @Autowired
    private GraphService graphService;

    @Autowired
    private GraphQueryService graphQueryService;

    @Test
    public void shouldFindNodesCloserThan() {
        // given
        NodeWeight nodeWeight = new NodeWeight(5);
        NodeName nodeName1 = new NodeName("FOO-201");
        NodeName nodeName2 = new NodeName("FOO-202");
        NodeName nodeName3 = new NodeName("FOO-203");
        NodeName nodeName4 = new NodeName("FOO-204");
        graphService.addNode(nodeName1);
        graphService.addNode(nodeName2);
        graphService.addNode(nodeName3);
        graphService.addNode(nodeName4);
        graphService.addEdge(nodeName1, nodeName2, new NodeWeight(1));
        graphService.addEdge(nodeName2, nodeName3, new NodeWeight(2));
        graphService.addEdge(nodeName3, nodeName4, new NodeWeight(3));

        // when
        Set<String> result = graphQueryService.findNodesCloserThan(nodeWeight, nodeName1);

        // then
        assertThat(result).containsExactly("FOO-202", "FOO-203");
    }
}