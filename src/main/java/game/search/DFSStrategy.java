package game.search;

import game.rules.GoalChecker;
import game.rules.NodeGenerator;
import model.Level;
import model.Node;
import model.State;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class DFSStrategy implements SearchStrategy{
    @Override
    public Node search(Level initialLevel) {
        if (initialLevel == null) {
            throw new IllegalArgumentException("Level cannot be null");
        }

        Set<State> visited = new HashSet<>();
        Stack<Node> stack = new Stack<>();  // Modern Stack

        Node rootNode = new Node(null, initialLevel.createInitialState(), null, 0);

        stack.push(rootNode);
        visited.add(rootNode.getState());

        while (!stack.isEmpty()) {
            Node currentNode = stack.pop();
            System.out.println(" the depth : " + currentNode.getDepth());


            // Check if goal reached
            if (GoalChecker.isFinal(currentNode.getState())) {
                return currentNode;
            }


            // Generate and explore children
            List<Node> childrenNodes = NodeGenerator.generateNextNodes(currentNode);

            // Add in reverse order to maintain DFS order
            for (int i = childrenNodes.size() - 1; i >= 0; i--) {
                Node child = childrenNodes.get(i);

                if (!visited.contains(child.getState())) {
                    stack.push(child);
                    visited.add(child.getState());
                }
            }
        }

        // No solution found
        return null;
    }
}
