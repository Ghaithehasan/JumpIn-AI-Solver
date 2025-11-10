package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RabbitAction implements Action{
    final private int rabbit_id;
    final private Position newPosition;

    public RabbitAction(int rabbit_id , Position newPosition)
    {
        this.rabbit_id=rabbit_id;
        this.newPosition =newPosition;
    }

    public int getRabbit_id() {
        return rabbit_id;
    }

    public Position getNewPosition() {
        return newPosition;
    }

    @Override
    public State applyMove(State currentState) {
        List<Rabbit> currentRabbits = currentState.getRabbits();
        List<Rabbit> newRabbits = new ArrayList<>();

        for (Rabbit rabbit : currentRabbits) {
            if (rabbit.getId() == this.rabbit_id) {
                newRabbits.add(new Rabbit(rabbit.getId(), this.newPosition));
            } else {
                newRabbits.add(rabbit);
            }
        }

        return new State(newRabbits, currentState.getFoxes(), currentState.getLevel());
    }


    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
            return true;
        if(!(obj instanceof RabbitAction))
            return false;

        RabbitAction x = (RabbitAction) obj;

            return this.rabbit_id==x.rabbit_id && this.newPosition.equals(x.newPosition);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(rabbit_id , newPosition);
    }


    @Override
    public String toString() {
        return "RabbitAction{" +
                "rabbit_id=" + rabbit_id +
                ", newPosition=" + newPosition +
                '}';
    }
}
