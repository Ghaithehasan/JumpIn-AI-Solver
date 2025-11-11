package model;
import java.util.ArrayList;
import java.util.List;


public class Node {

    final private Node parent;
    final private Action action;
    final private int depth; // cost = depth
    final private State state;

    public Node(Node parent, State state, Action action, int depth) {
        this.parent = parent;
        this.state = state;
        this.action = action;
        this.depth = depth;
    }


    public Node getParent() {
        return parent;
    }

    public State getState() {
        return state;
    }

    public Action getAction() {
        return action;
    }

    public int getDepth() {
        return depth;
    }


    public int getCost() {
        return depth;
    }


    public boolean isRoot() {
        return this.parent == null;
    }


    public List<Action> getPath() {
        List<Action> path = new ArrayList<>();
        Node current = this;

        while (current.parent != null) {
            path.add(0, current.action);
            current = current.parent;
        }

        return path;
    }


    @Override
    public String toString() {
        return "Node{" +
                "parent=" + parent +
                ", action=" + action +
                ", depth=" + depth +
                ", state=" + state +
                '}';
    }
}
