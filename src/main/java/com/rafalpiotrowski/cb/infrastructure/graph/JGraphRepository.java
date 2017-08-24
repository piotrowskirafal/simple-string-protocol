package com.rafalpiotrowski.cb.infrastructure.graph;

import com.rafalpiotrowski.cb.domain.graph.Graph;
import com.rafalpiotrowski.cb.domain.graph.GraphRepository;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedPseudograph;
import org.springframework.stereotype.Component;

@Component
public class JGraphRepository implements GraphRepository {

    private final Graph graph = new JGraph(new DirectedWeightedPseudograph<>(DefaultWeightedEdge.class));

    @Override
    public Graph loadGraph() {
        return graph;
    }
}
