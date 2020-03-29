package it.polimi.ingsw.ps51.exceptions;

public class OutOfMapException extends Exception{
    Integer x;
    Integer y;

    public OutOfMapException(Integer x, Integer y){
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "OutOfMapException{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
