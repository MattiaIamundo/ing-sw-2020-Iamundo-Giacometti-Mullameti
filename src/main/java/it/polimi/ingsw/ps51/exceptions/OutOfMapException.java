package it.polimi.ingsw.ps51.exceptions;

public class OutOfMapException extends Exception{
    final Integer x;
    final Integer y;

    public OutOfMapException(Integer x, Integer y){
        this.x = x;
        this.y = y;
    }

}
