package game.search;

import game.rules.GoalChecker;
import game.rules.NodeGenerator;
import model.Action;
import model.Level;
import model.Node;
import model.State;

import java.util.*;

public class SearchAlgorithms {


    public static List<Action> solve(Level level, SearchStrategy strategy)
    {
        Node goalNode = strategy.search(level);
        if (goalNode == null) {
            return null;
        }
        return goalNode.getPath();
    }
}








//
//
//    public static List<Action> DFS(Level initialLevel) {
//        if (initialLevel == null) {
//            throw new IllegalArgumentException("Level cannot be null");
//        }
//
//        Set<State> visited = new HashSet<>();
//        Stack<Node> stack = new Stack<>();  // Modern Stack
//
//        Node rootNode = new Node(null, initialLevel.createInitialState(), null, 0);
//
//        stack.push(rootNode);
//        visited.add(rootNode.getState());
//
//        while (!stack.isEmpty()) {
//            Node currentNode = stack.pop();
//
//            // Check if goal reached
//            if (GoalChecker.isFinal(currentNode.getState())) {
//                return currentNode.getPath();
//            }
//
//            // Generate and explore children
//            List<Node> childrenNodes = NodeGenerator.generateNextNodes(currentNode);
//
//            // Add in reverse order to maintain DFS order
//            for (int i = childrenNodes.size() - 1; i >= 0; i--) {
//                Node child = childrenNodes.get(i);
//
//                if (!visited.contains(child.getState())) {
//                    stack.push(child);
//                    visited.add(child.getState());
//                }
//            }
//        }
//
//        // No solution found
//        return null;
//    }
//
