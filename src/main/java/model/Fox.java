package model;

import java.util.Objects;

public class Fox {

    private final int id;
    private final Position position;
    private final Orientation orientation;

    public Fox(int id, Position position, Orientation orientation) {
        this.id = id;
        this.position = position;
        this.orientation = orientation;
    }

    public int getId() {
        return id;
    }

    public Position getPosition() {
        return position;
    }

    public Orientation getOrientation() {
        return orientation;
    }


    // this is for compare deep
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;

        if(!(obj instanceof Fox))
            return false;

        Fox obj2 = (Fox)obj;
        return this.id == obj2.id && this.position.equals(obj2.position) && this.orientation == obj2.orientation;
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, position, orientation);
    }
}
