package model;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class State {

    final private List<Rabbit> rabbits;
    final private List<Fox> foxes;
    final private Level level;


    final private Set<Position> holesSet;


    public State(List<Rabbit> rabbits , List<Fox> foxes , Level level)
    {
        this.rabbits=List.copyOf(rabbits);
        this.foxes=List.copyOf(foxes);
        this.level=level;
        this.holesSet=new HashSet<>(level.getHoles());
    }


    public List<Rabbit> getRabbits() {
        return List.copyOf(rabbits);
    }

    public List<Fox> getFoxes() {
        return List.copyOf(foxes);
    }

    public Set<Position> getHolesSet() {
        return holesSet;
    }

    public Level getLevel() {
        return level;
    }


    @Override
    public boolean equals(Object obj)
    {
        if(this==obj)
            return true;
        if (!(obj instanceof State))
            return false;

        State other = (State) obj;
        return this.level.equals(other.level) && this.rabbits.equals(other.rabbits) && this.foxes.equals(other.foxes);

    }

    @Override
    public int hashCode() {
        return Objects.hash(rabbits , foxes , level);
    }

}
