package sae.solver;

import sae.graph.GraphSoluce;
import sae.graph.Node;

import java.util.*;

public class SolverWithAstar implements Solver {

    private int steps;
    private GraphSoluce soluce;
    private Node start;
    private Node end;

    public SolverWithAstar(Node start, Node end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public void resolve() {
        this.soluce = new GraphSoluce();
        Map<Node, Double> gValues = new HashMap<>();
        Map<Node, Double> fValues = new HashMap<>();

        Comparator<Node> nodeComparator = (s1, s2) -> {
            double f1 = fValues.getOrDefault(s1, Double.POSITIVE_INFINITY);
            double f2 = fValues.getOrDefault(s2, Double.POSITIVE_INFINITY);
            return Double.compare(f1, f2);
        };

        PriorityQueue<Node> openList = new PriorityQueue<>(nodeComparator);
        Set<Node> closedSet = new HashSet<>();
        Map<Node, Node> predecessors = new HashMap<>();

        this.steps = 0;
        gValues.put(start, 0.0);
        fValues.put(start, calculateHeuristic(start));

        openList.add(start);

        while (!openList.isEmpty()) {
            Node currentNode = openList.poll();
            if (currentNode.equals(end)) {
                reconstructPath(predecessors);
                break;
            }
            closedSet.add(currentNode);

            for (Node neighbor : currentNode.neighbors()) {
                this.steps++;
                if (closedSet.contains(neighbor)) {
                    continue;
                }

                double tentativeG = gValues.get(currentNode) + calculateDistance(currentNode, neighbor);
                if (!openList.contains(neighbor) || tentativeG < gValues.get(neighbor)) {
                    gValues.put(neighbor, tentativeG);
                    fValues.put(neighbor, tentativeG + calculateHeuristic(neighbor));
                    predecessors.put(neighbor, currentNode);
                    if (!openList.contains(neighbor)) {
                        openList.add(neighbor);
                    }
                }
            }
        }
    }

    private double calculateDistance(Node node1, Node node2) {
        double deltaX = node1.getCoord().getX() - node2.getCoord().getX();
        double deltaY = node1.getCoord().getY() - node2.getCoord().getY();
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    private double calculateHeuristic(Node node) {
        double deltaX = node.getCoord().getX() - end.getCoord().getX();
        double deltaY = node.getCoord().getY() - end.getCoord().getY();
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    private void reconstructPath(Map<Node, Node> predecessors) {
        Node currentNode = end;
        while (currentNode != null) {
            soluce.add(currentNode);
            currentNode = predecessors.get(currentNode);
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
