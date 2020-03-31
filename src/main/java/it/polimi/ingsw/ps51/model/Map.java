package it.polimi.ingsw.ps51.model;

import it.polimi.ingsw.ps51.exceptions.OutOfMapException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Is the game's map
 * @author Mattia Iamundo
 */
public class Map implements Serializable {

    private Square[][] map;

    /**
     * This is the standard constructor, it's create a 5x5 map
     */
    public Map(){
        map = new Square[5][5];
        for (int x=0; x < 5; x++){
            for (int y=0; y < 5; y++){
                map[y][x] = new Square(new Coordinates(y, x));
            }
        }
    }

    /***
     * This is an alternative constructor in case a map of different dimension is needed
     * @param x the number of columns
     * @param y the number o rows
     */
    public Map(int x, int y){
        map = new Square[x][y];
        for (int i=0; i < x; i++){
            for (int k=0; k < y; k++){
                map[k][i] = new Square(new Coordinates(k, i));
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
        if ((x < 0) || (y < 0) || (x >= map.length) || (y >= map[x].length)){
            throw new OutOfMapException(x, y);
        }else {
            return map[x][y];
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

        return (coordinates.getX() == 0) || (coordinates.getY() == 0) || (coordinates.getX() == map.length - 1)
                || (coordinates.getY() == map[0].length - 1);
    }
}
