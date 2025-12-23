package game.search;

import game.rules.GoalChecker;
import game.rules.NodeGenerator;
import model.*;

import java.util.*;


public class ASTARStrategy implements SearchStrategy {

    private int visitedNodesCount = 0;
    private int maxDepth = 0;

    @Override
    public Node search(Level initialLevel) {
        visitedNodesCount = 0;
        maxDepth = 0;

        Map<State, Integer> gScore = new HashMap<>();

        // Priority Queue ordered by f(n) = g(n) + h(n)
        PriorityQueue<AStarNode> openSet = new PriorityQueue<>(
                Comparator.comparingInt(AStarNode::getFScore)
        );

        State initialState = initialLevel.createInitialState();
        Node rootNode = new Node(null, initialState, null, 0);
        int initialH = heuristic(initialState);


        AStarNode initialAStarNode = new AStarNode(rootNode, 0, initialH);
        openSet.add(initialAStarNode);
        gScore.put(initialState, 0);

        while (!openSet.isEmpty()) {
            AStarNode current = openSet.poll();
            Node currentNode = current.node;
            State currentState = currentNode.getState();
            int currentG = current.gScore;

            if (gScore.containsKey(currentState) && gScore.get(currentState) < currentG) {
                continue;
            }

            visitedNodesCount++;

            if (currentNode.getDepth() > maxDepth) {
                maxDepth = currentNode.getDepth();
            }

            if (GoalChecker.isFinal(currentState)) {
                printStatistics(currentNode);
                return currentNode;
            }

            List<Node> children = NodeGenerator.generateNextNodes(currentNode);

            for (Node child : children) {
                State childState = child.getState();
                int newG = currentG + 1;

                if (!gScore.containsKey(childState) || newG < gScore.get(childState)) {
                    gScore.put(childState, newG);

                    int h = heuristic(childState);

                    AStarNode childAStarNode = new AStarNode(child, newG, h);
                    openSet.add(childAStarNode);
                }
            }
        }

        // No solution found
        printStatistics(null);
        return null;
    }


    private int heuristic(State state) {
        int h1 = manhattanDistanceHeuristic(state);
        int h2 = rabbitsNotInHoleHeuristic(state);
        return Math.max(h1 , h2);
    }


    private int manhattanDistanceHeuristic(State state) {
        int totalJumps = 0;
        Set<Position> holes = state.getHolesSet();

        for (Rabbit rabbit : state.getRabbits()) {
            Position rabbitPos = rabbit.getPosition();

            if (holes.contains(rabbitPos)) {
                continue;
            }

            int minJumps = Integer.MAX_VALUE;

            for (Position hole : holes) {
                int jumps = calculateJumpsRequired(rabbitPos, hole);
                minJumps = Math.min(minJumps, jumps);
            }

            totalJumps += minJumps;
        }

        return totalJumps;
    }

    private int calculateJumpsRequired(Position from, Position to) {
        int dx = Math.abs(to.getX() - from.getX());
        int dy = Math.abs(to.getY() - from.getY());

        if (dx == 0 && dy == 0) {
            return 0;
        }

        if (dx + dy == 1) {
            return Integer.MAX_VALUE;
        }

        if (dx == 0 || dy == 0) {
            return 1;  // One jump
        }

        return 2;
    }

    private int rabbitsNotInHoleHeuristic(State state) {
        Set<Position> holes = state.getHolesSet();

        int rabbitsNotInHoles = 0;
        for (Rabbit rabbit : state.getRabbits()) {
            if (!holes.contains(rabbit.getPosition())) {
                rabbitsNotInHoles++;
            }
        }
        return rabbitsNotInHoles;
    }



    private static class AStarNode {

        final Node node;
        final int gScore;
        final int hScore;

        AStarNode(Node node, int gScore, int hScore) {
            this.node = node;
            this.gScore = gScore;
            this.hScore = hScore;
        }

        int getFScore() {
            return gScore + hScore;  // f(n)= g(n) + h(n)
        }
    }


    private void printStatistics(Node solutionNode) {
        System.out.println("Visited Nodes:     " + String.format("%6d", visitedNodesCount) + "         ");
        if (solutionNode != null) {
            System.out.println("Solution Depth:    " + String.format("%6d", solutionNode.getDepth()) + "         ");
            System.out.println("Solution Cost:     " + String.format("%6d", solutionNode.getCost()) + "           ");
        } else {
            System.out.println("Not Found");
        }

    }
}