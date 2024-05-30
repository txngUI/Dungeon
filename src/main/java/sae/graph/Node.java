package sae.graph;

import sae.dungeon.Coord;

import java.util.HashSet;
import java.util.Set;

public class Node {
    private String name;
    private Set<Node> neighbors = new HashSet<>();
    private Coord coord;
    public int cost;
    public int heuristic;

    public Node(String name, Coord coord) {
        super();
        this.name = name;
        this.coord = coord;
    }
    public Set<Node> neighbors() {
        return neighbors;
    }

    public void addNeighbour(Node node) {
        neighbors.add(node);
    }

    public String getName() {
        return name;
    }

    public Coord getCoord() {
        return coord;
    }

    public boolean equals(Object obj) {
        Node other = (Node) obj;
        return this.name == other.name;
    }
}
