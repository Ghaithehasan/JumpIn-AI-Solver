package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FoxAction implements Action{

    final private int fox_id;
    final private Position newPosition;

    public FoxAction(int fox_id , Position newPosition)
    {
        this.fox_id=fox_id;
        this.newPosition=newPosition;
    }

    public Position getNewPosition() {
        return newPosition;
    }

    public int getFox_id() {
        return fox_id;
    }

    @Override
    public State applyMove(State currentState) {
        List<Fox> currentFoxes = currentState.getFoxes();
        List<Fox> newFoxes = new ArrayList<>();

        for (Fox fox : currentFoxes) {
            if(fox.getId()==this.fox_id)
            {
                newFoxes.add(new Fox(fox.getId() , this.newPosition , fox.getOrientation()));
            }
            else
            {
                newFoxes.add(fox);
            }
        }
        return new State(currentState.getRabbits(), newFoxes , currentState.getLevel());
    }

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
            return true;
        if(!(obj instanceof FoxAction))
            return false;

        FoxAction x = (FoxAction) obj;

        return this.fox_id==x.fox_id && this.newPosition.equals(x.newPosition);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(fox_id , newPosition);
    }

    @Override
    public String toString() {
        return "FoxAction{" +
                "fox_id=" + fox_id +
                ", newPosition=" + newPosition +
                '}';
    }
}
