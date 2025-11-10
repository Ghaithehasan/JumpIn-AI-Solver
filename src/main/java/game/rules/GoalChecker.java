package game.rules;

import model.Position;
import model.Rabbit;
import model.State;

import java.util.List;
import java.util.Set;

public class GoalChecker {




    public static boolean isFinal(State state)
    {
        Set<Position> holes = state.getHolesSet();
        for(Rabbit rabbit : state.getRabbits())
        {
            if(!holes.contains(rabbit.getPosition()))
            {
                return false;
            }
        }
        return true;
    }
}
