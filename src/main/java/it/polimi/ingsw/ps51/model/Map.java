package it.polimi.ingsw.ps51.model;

import it.polimi.ingsw.ps51.events.events_for_client.EventForClient;
import it.polimi.ingsw.ps51.events.events_for_client.MapUpdate;
import it.polimi.ingsw.ps51.exceptions.OutOfMapException;
import it.polimi.ingsw.ps51.utility.Observable;

import java.io.Serializable;
import java.util.*;

/**
 * Is the game's map
 * @author Mattia Iamundo
 */
public class Map extends Observable<EventForClient> implements Serializable, Iterable<Square>, Cloneable{

    private Square[][] effectiveMap;

    /**
     * This is the standard constructor, it's create a 5x5 map
     * The X-axis grows from left to right, The Y-axis grows top down
     * the origin is in the upper left corner
     */
    public Map(){
        effectiveMap = new Square[5][5];
        for (int x=0; x < 5; x++){
            for (int y=0; y < 5; y++){
                effectiveMap[y][x] = new Square(new Coordinates(x, y));
            }
        }
    }

    /***
     * This is an alternative constructor in case a map of different dimension is needed
     * @param x the number of columns
     * @param y the number o rows
     */
    public Map(int x, int y){
        effectiveMap = new Square[y][x];
        for (int i=0; i < x; i++){
            for (int k=0; k < y; k++){
                effectiveMap[k][i] = new Square(new Coordinates(i, k));
            }
        }
    }

    /**
     * Retrieve a square given it's position
     * @param x the column number
     * @param y the row number
     * @return the square in the specified coordinates
     * @throws OutOfMapException if the given coordinate indicates a point outside the map
     */
    public Square getSquare(Integer x , Integer y) throws OutOfMapException {
        if ((x < 0) || (y < 0) || (y >= effectiveMap.length) || (x >= effectiveMap[y].length)){
            throw new OutOfMapException(x, y);
        }else {
            return effectiveMap[y][x];
        }
    }

    /**
     * Get the list of the near squares in a precise order
     * @param square whose neighbors are wanted
     * @return a List pointing to the squares starting from the upper left one continuing clockwise. If a square
     *         doesn't exists it's position in the list is set to null
     */
    public List<Square> getAdjacentSquare(Square square){
        Coordinates squareCoord = square.getCoordinates();
        List<Square> adjacentSquares = new ArrayList<Square>();
        //This for retrieve the three square above the selected one
        for (int i=-1; i < 2; i++){
            try {
                adjacentSquares.add(getSquare(squareCoord.getX() + i, squareCoord.getY() - 1));
            } catch (OutOfMapException e) {
                adjacentSquares.add(null);
            }
        }
        //This retrieve the square in the right of the selected one
        try {
            adjacentSquares.add(getSquare(squareCoord.getX() + 1, squareCoord.getY()));
        }catch (OutOfMapException e){
            adjacentSquares.add(null);
        }
        //This for retrieve the three square under the selected one
        for (int i=1; i > -2; i--){
            try {
                adjacentSquares.add(getSquare(squareCoord.getX() + i, squareCoord.getY() + 1));
            } catch (OutOfMapException e) {
                adjacentSquares.add(null);
            }
        }
        //This retrieve the left square of the selected one
        try {
            adjacentSquares.add(getSquare(squareCoord.getX() - 1, squareCoord.getY()));
        }catch (OutOfMapException e){
            adjacentSquares.add(null);
        }
        return adjacentSquares;
    }

    /**
     * check if a square is on the map's perimeter
     * @param square to check
     * @return true if the given square is on the map's perimeter, false otherwise
     */
    public boolean isThisPerimeterSquare(Square square){
        Coordinates coordinates = square.getCoordinates();

        return (coordinates.getX() == 0) || (coordinates.getY() == 0) || (coordinates.getX() == effectiveMap.length - 1)
                || (coordinates.getY() == effectiveMap[0].length - 1);
    }

    /**
     * The method notify all the players that the map has been changed, to the clients is sent a deep copy of the map
     */
    public void notifyMapUpdate(){
        try {
            notify(new MapUpdate((Map) this.clone()));
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    /**
     * The class create an iterator for the map that scans it from left to right top down
     */
    private class MapIterator implements Iterator<Square>{

        Square[][] map;
        Integer x = 0;
        Integer y = 0;

        public MapIterator(Square[][] map){
            this.map = map;
        }

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return (y < map.length) && (x < map[y].length);
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public Square next() {
            if (x < map[y].length - 1){
                Square square = map[y][x];
                x++;
                return square;
            }else if (y < map.length){
                Square square = map[y][x];
                x = 0;
                y++;
                return square;
            }else {
                throw new NoSuchElementException();
            }
        }
    }


    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<Square> iterator() {
        return new MapIterator(effectiveMap);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }

        if (obj == null || obj.getClass() != this.getClass()){
            return false;
        }

        Map itsMap = (Map) obj;
        for (int y = 0; y < itsMap.effectiveMap.length; y++){
            for (int x = 0; x < itsMap.effectiveMap[y].length; x++){
                if (!itsMap.effectiveMap[y][x].equals(effectiveMap[y][x])){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Map newMap = (Map) super.clone();
        newMap.effectiveMap = new Square[this.effectiveMap.length][this.effectiveMap[0].length];
        for (int y = 0; y < newMap.effectiveMap.length; y++){
            for (int x = 0; x < newMap.effectiveMap[y].length; x++){
                newMap.effectiveMap[y][x] = (Square) this.effectiveMap[y][x].clone();
            }
        }

        return newMap;
    }
}
