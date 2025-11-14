package game.rules;

import model.Action;
import model.Node;
import model.State;

import java.util.ArrayList;
import java.util.List;

public class NodeGenerator {

    public static List<Node> generateNextNodes(Node currentNode)
    {
        List<Node> childNodes = new ArrayList<>();
        State currentState = currentNode.getState();
        int currentDepth = currentNode.getDepth();
        for(Action action:MoveGenerator.getPossibleActions(currentState))
        {
            State nextState = action.applyMove(currentState);
            childNodes.add(new Node(currentNode , nextState , action , currentDepth+1));
        }
        return childNodes;
    }
}
