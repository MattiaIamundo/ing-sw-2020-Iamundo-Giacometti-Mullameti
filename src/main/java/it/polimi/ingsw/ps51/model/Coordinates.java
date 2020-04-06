package it.polimi.ingsw.ps51.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Coordinates implements Serializable, Cloneable{
   
    private Integer x ;
    private Integer y ;

    
    public Coordinates(Integer x , Integer y){
        this.x = x;
        this.y = y;
    }

    public List<Integer> getCoordinates(){
        List<Integer> coordinates = new ArrayList<>();
        coordinates.add(x);
        coordinates.add(y);
        return coordinates;
    }

    public Integer getX(){
        return x;
    }

    public Integer getY(){
        return y;
    }

    @Override
    public boolean equals(Object o){
        if (this == o){
            return true;
        }

        if (o == null || o.getClass() != this.getClass()){
            return false;
        }

        Coordinates coord = (Coordinates) o;
        return (coord.x.equals(this.x) && coord.y.equals(this.y));
    }

    @Override
    public int hashCode(){
        return (this.x + this.y);
    }

}
