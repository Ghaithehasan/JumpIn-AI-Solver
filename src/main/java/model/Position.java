package model;

import java.util.Objects;

public class Position {
    final private int x;
    final private int y;
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Position))
            return false;
        Position obj1 = (Position) obj;

        return this.x == obj1.x && this.y == obj1.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

}
