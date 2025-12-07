package model;
import java.util.ArrayList;
import java.util.Collections;
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
        // 1. استخدم قائمة مؤقتة (أو Stack) لجمع المسار بشكل عكسي
        List<Action> reversedPath = new ArrayList<>();
        Node current = this;

        while (current.parent != null) {
            // أضف في النهاية (عملية سريعة O(1))
            reversedPath.add(current.action);
            current = current.parent;
        }

        // 2. اعكس القائمة مرة واحدة في النهاية (عملية سريعة O(d))
        Collections.reverse(reversedPath);

        return reversedPath;
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
