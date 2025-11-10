package model;

import java.util.ArrayList;
import java.util.List;


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
        return new State();
//        return new State(this.initialRabbits, this.initialFoxes, this);
    }


    public int getLevelId() {
        return levelId;
    }

    public List<Position> getHoles() {
        return new ArrayList<>(holes);
    }

    public List<Position> getMushrooms() {
        return new ArrayList<>(mushrooms);
    }

    public List<Fox> getInitialFoxes() {
        return new ArrayList<>(initialFoxes);
    }

    public List<Rabbit> getInitialRabbits() {
        return new ArrayList<>(initialRabbits);
    }
}

