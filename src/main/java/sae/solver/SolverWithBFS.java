package sae.solver;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import sae.graph.GraphSoluce;
import sae.graph.Node;

public class SolverWithBFS implements Solver {
    private Node nodeA;
    private Node nodeB;
    private int steps;
    private GraphSoluce soluce;

    public SolverWithBFS(Node nodeA, Node nodeB) {
        super();
        this.nodeA = nodeA;
        this.nodeB = nodeB;
    }

    @Override
    public void resolve() {
        Queue<Node> file = new LinkedList<>();
        Map<Node, Boolean> marquer = new HashMap<>();
        Map<Node, Node> predecesseurs = new HashMap<>();
        this.steps = 0;

        marquer.put(nodeA, true);
        file.add(nodeA);

        while (!file.isEmpty()) {
            Node node = file.poll();
            if (node.equals(nodeB))
                break;
            for (Node voisin : node.neighbors()) {
                this.steps++;
                if (!marquer.containsKey(voisin)) {
                    predecesseurs.put(voisin, node);
                    marquer.put(voisin, true);
                    file.add(voisin);
                }
            }
        }

        this.soluce = new GraphSoluce();
        if (marquer.containsKey(nodeB)) {
            Node node = nodeB;
            while (!node.equals(nodeA)) {
                soluce.add(node);
                node = predecesseurs.get(node);
            }
            soluce.add(nodeA);
        }
        Collections.reverse(soluce.getSoluce());
    }

    @Override
    public GraphSoluce getSoluce() {
        return this.soluce;
    }

    @Override
    public int getSteps() {
        return steps;
    }
}