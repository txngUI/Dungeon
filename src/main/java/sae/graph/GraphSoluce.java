package sae.graph;

import java.util.ArrayList;
import java.util.List;

public class GraphSoluce {
    private List<Node> soluce = new ArrayList<Node>();

    public GraphSoluce() {
        super();
        soluce = new ArrayList<>();
    }

    public void add(Node node) {
        soluce.add(node);
    }

    public List<Node> getSoluce() {
        return soluce;
    }
}