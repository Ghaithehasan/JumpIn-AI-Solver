package game.search;

import game.rules.GoalChecker;
import game.rules.NodeGenerator;
import model.Action;
import model.Level;
import model.Node;
import model.State;

import java.util.*;

public class SearchAlgorithms {

    public static List<Action> bfs(Level initialLevel)
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
}
