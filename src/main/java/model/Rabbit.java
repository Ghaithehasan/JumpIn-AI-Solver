package model;

import java.util.Objects;

public class Rabbit {

    final private int id;
    final Position position;



    public Rabbit(int id , Position position)
    {
        this.id = id;
        this.position=position;
    }

    public int getId() {
        return id;
    }
    public Position getPosition()
    {
        return position;
    }


    // any premitive data type we make (==)
    // and any object we make this.position.equals(obj.position)
    @Override
    public boolean equals(Object obj)
    {

        if(this == obj)
            return true;

        if(!(obj instanceof Rabbit))
            return false;

        Rabbit obj1 = (Rabbit) obj;

        return this.id == obj1.id && this.position.equals(obj1.position);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, position);
    }

}
