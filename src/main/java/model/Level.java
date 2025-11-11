package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Level {

    final private int levelId;
    final private List<Position> holes;
    final private List<Position> mushrooms;
    final private List<Rabbit> initialRabbits;
    final private List<Fox> initialFoxes;


    public Level(int levelId, List<Position> holes, List<Position> mushrooms, List<Rabbit> initialRabbits, List<Fox> initialFoxes) {
        this.levelId = levelId;
        this.holes = List.copyOf(holes);
        this.mushrooms = List.copyOf(mushrooms);
        this.initialRabbits = List.copyOf(initialRabbits);
        this.initialFoxes = List.copyOf(initialFoxes);
    }

    public State createInitialState()
    {
        return new State(this.initialRabbits, this.initialFoxes, this);
    }


    public int getLevelId() {
        return levelId;
    }

    public List<Position> getHoles() {
        return List.copyOf(holes);
    }

    public List<Position> getMushrooms() {
          return List.copyOf(mushrooms);
    }

    public List<Fox> getInitialFoxes() {
        return List.copyOf(initialFoxes);
    }

    public List<Rabbit> getInitialRabbits() {
        return List.copyOf(initialRabbits);
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Level)) return false;
        Level other = (Level) obj;
        return this.levelId == other.levelId &&
                this.holes.equals(other.holes) &&
                this.mushrooms.equals(other.mushrooms) &&
                this.initialRabbits.equals(other.initialRabbits) &&
                this.initialFoxes.equals(other.initialFoxes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(levelId, holes, mushrooms, initialRabbits, initialFoxes);
    }
}

