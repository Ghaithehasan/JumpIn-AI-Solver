package game.search;

import game.rules.GoalChecker;
import game.rules.NodeGenerator;
import model.Level;
import model.Node;
import model.State;

import java.util.*;

public class UCSStrategy implements SearchStrategy {

//    @Override
//    public Node search(Level initialLevel) {
//        // Statistics
//        int visitedNodesCount = 0;
//        int maxDepth = 0;
//
//        Set<State> visited = new HashSet<>();
//
//        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(
//                Comparator.comparingInt(Node::getCost)
//        );
//
//        Node rootNode = new Node(null, initialLevel.createInitialState(), null, 0);
//
//        priorityQueue.add(rootNode);
//
//        while (!priorityQueue.isEmpty()) {
//            Node currentNode = priorityQueue.poll();
//
//            if (visited.contains(currentNode.getState())) {
//                continue;
//            }
//
//            visited.add(currentNode.getState());
//            visitedNodesCount++;
//
//            if (currentNode.getDepth() > maxDepth) {
//                maxDepth = currentNode.getDepth();
//            }
//
//            if (GoalChecker.isFinal(currentNode.getState())) {
//                printStatistics(visitedNodesCount, maxDepth, currentNode);
//                return currentNode;
//            }
//
//            List<Node> childrenNodes = NodeGenerator.generateNextNodes(currentNode);
//
//            for (Node child : childrenNodes) {
//                if (!visited.contains(child.getState())) {
//                    priorityQueue.add(child);
//                }
//            }
//        }
//
//        printStatistics(visitedNodesCount, maxDepth, null);
//        return null;
//    }

    @Override
    public Node search(Level initialLevel) {
        int visitedNodesCount = 0;
        int maxDepth = 0;

        Map<State, Integer> bestCost = new HashMap<>();

        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(
                Comparator.comparingInt(Node::getCost)
        );

        Node rootNode = new Node(null, initialLevel.createInitialState(), null, 0);
        priorityQueue.add(rootNode);

        while (!priorityQueue.isEmpty()) {
            Node currentNode = priorityQueue.poll();
            State currentState = currentNode.getState();
            int currentCost = currentNode.getCost();

            if (bestCost.containsKey(currentState)) {
                if ( currentCost >= bestCost.get(currentState)  ) {
                    continue;
                }
            }

            bestCost.put(currentState, currentCost);
            visitedNodesCount++;

            if (currentNode.getDepth() > maxDepth) {
                maxDepth = currentNode.getDepth();
            }

            if (GoalChecker.isFinal(currentState)) {
                printStatistics(visitedNodesCount, maxDepth, currentNode);
                return currentNode;
            }

            List<Node> childrenNodes = NodeGenerator.generateNextNodes(currentNode);


            for (Node child : childrenNodes) {
                State childState = child.getState();
                int childCost = child.getCost();

                if (!bestCost.containsKey(childState) || bestCost.get(childState) > childCost) {
                    priorityQueue.add(child);
                }
            }
        }

        printStatistics(visitedNodesCount, maxDepth, null);
        return null;
    }


    private void printStatistics(int visitedNodes, int maxDepth, Node solutionNode) {
        System.out.println("\n=== UCS Statistics ===");
        System.out.println("Visited Nodes: " + visitedNodes);
        System.out.println("Max Depth: " + maxDepth);

        if (solutionNode != null) {
            System.out.println("Solution Depth: " + solutionNode.getDepth());
            System.out.println("Solution Cost: " + solutionNode.getCost());
            System.out.println("Status: Solution Found");
        } else {
            System.out.println("Status: No Solution");
        }
        System.out.println("===================");
    }
}