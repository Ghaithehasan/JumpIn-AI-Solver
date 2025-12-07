package game.search;

import game.rules.GoalChecker;
import game.rules.NodeGenerator;
import model.Action;
import model.Level;
import model.Node;
import model.State;

import java.util.*;

public class SearchAlgorithms {

    public static List<Action> BFS(Level initialLevel)
    {
        Set<State> visited = new HashSet<>();
        Queue<Node> queue = new LinkedList<>();
        Node rootNode = new Node(null , initialLevel.createInitialState() , null , 0);

        queue.add(rootNode);
        visited.add(rootNode.getState());

        while (!queue.isEmpty())
        {
            Node current_node = queue.poll();

            if(GoalChecker.isFinal(current_node.getState()))
            {
                return current_node.getPath();
            }

            List<Node> childrenNodes = NodeGenerator.generateNextNodes(current_node);

            for(Node child:childrenNodes)
            {
                if(!visited.contains(child.getState()))
                {
                    visited.add(child.getState());
                    queue.add(child);

                }
            }
        }
        return null;
    }

    public static List<Action> DFS(Level initialLevel) {
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
                return currentNode.getPath();
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
