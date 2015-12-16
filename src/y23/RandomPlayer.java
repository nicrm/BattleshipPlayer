/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package y23;

import battleship.interfaces.BattleshipsPlayer;
import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import battleship.interfaces.Board;
import battleship.interfaces.Ship;
import java.util.ArrayList;
import java.util.Random;


/**
 *
 * @author Tobias
 */
public class RandomPlayer implements BattleshipsPlayer
{
    private final static Random rnd = new Random();
    private int sizeX;
    private int sizeY;
    private Board myBoard;
    Shooter shooterObject;
    int[][] shipTrackList;
    
   
    public RandomPlayer()
    {
        shooterObject = new Shooter();
        //the creation of the shooterObject should be delayed til the game bord siye is known. the game borad size is only knwon after the place ships method is called.
        //
    }

   
    /**
     * The method called when its time for the AI to place ships on the board 
     * (at the beginning of each round).
     * 
     * The Ship object to be placed  MUST be taken from the Fleet given 
     * (do not create your own Ship objects!).
     * 
     * A ship is placed by calling the board.placeShip(..., Ship ship, ...) 
     * for each ship in the fleet (see board interface for details on placeShip()).
     * 
     * A player is not required to place all the ships. 
     * Ships placed outside the board or on top of each other are wrecked.
     * 
     * @param fleet Fleet all the ships that a player should place. 
     * @param board Board the board were the ships must be placed.
     */
    @Override
    public void placeShips(Fleet fleet, Board board)
    {      
        myBoard = board;
        sizeX = board.sizeX();
        sizeY = board.sizeY();
        
        shooterObject.initializeShooterGameboard(sizeX, sizeY);
        shipTrackList = new int[sizeX][sizeY];
        
          for (int s = 0; s<shipTrackList.length;s++){
            for(int t = 0; t<shipTrackList[s].length;t++){
                shipTrackList[s][t] = 0;
            }
        }
        
        for(int i = 0; i < fleet.getNumberOfShips(); ++i)
        {
            Ship s = fleet.getShip(i);
            boolean vertical = rnd.nextBoolean();
            
            Position placeShipPosition = placeSingleShip(s, vertical);
            board.placeShip(placeShipPosition, s, vertical);
        }
        
        //prints the shiptracklist
        for (int s = 0; s<shipTrackList.length;s++){
            for(int t = 0; t<shipTrackList[s].length;t++){
                System.out.print("["+shipTrackList[s][t]+"]");
            }
            System.out.println();
        }
    }
    
    public Position placeSingleShip(Ship s,boolean vertical){
        Position pos;
            if(vertical)
            {
                int x = rnd.nextInt(sizeX);
           //  int y = rnd.nextInt(sizeY-(s.size()-1));
                int y = rnd.nextInt(sizeY-(s.size()));
                pos = new Position(x, y);
            }
            else
            {
              //  int x = rnd.nextInt(sizeX-(s.size()-1));
                int x = rnd.nextInt(sizeX-(s.size()));
                int y = rnd.nextInt(sizeY);
                pos = new Position(x, y);
            }
            
            //make sure that position is free
            boolean allFree = false;
            for(int a=0; a < s.size(); a++){
                if(vertical){
                    if(shipTrackList[pos.x][(pos.y+a)] == 0){
                        allFree = true;
                    }else{
                        allFree = false;
                        break;
                    }
                }else{
                    if(shipTrackList[(pos.x+a)][pos.y] == 0){
                        allFree = true;
                    }else{
                        allFree = false;
                        break;
                    }
                }
            }
            //if the position is free, we want to place the ship and therefore mark for future runs that the positions are not free anymore
            if (allFree) {
                for (int a = 0; a < s.size(); a++) {
                    if (vertical) {
                        shipTrackList[pos.x][(pos.y + a)] = 1;
                        System.out.println("added to ["+pos.x+"]["+(pos.y+a)+"]shiplist");
                    } else {
                        shipTrackList[(pos.x + a)][pos.y] = 1;
                        System.out.println("added to ["+(pos.x+a)+"]["+pos.y+"]shiplist");
                    }
                }
            }else{
                // if the position is already taken a.k.a occupied, the function is called in a recursion till a free position is found and the result is returned
                pos = placeSingleShip(s, vertical);
            }
          return pos;
    }

    /**
     * Called every time the enemy has fired a shot.
     * 
     * The purpose of this method is to allow the AI to react to the 
     * enemy's incoming fire and place his/her ships differently next round.
     * 
     * @param pos Position of the enemy's shot 
     */
    @Override
    public void incoming(Position pos)
    {
        //Do nothing
    }

    
    /**
     * Called by the Game application to get the Position of your shot.
     *  hitFeedBack(...) is called right after this method.
     * 
     * @param enemyShips Fleet the enemy's ships. Compare this to the Fleet 
     * supplied in the hitFeedBack(...) method to see if you have sunk any ships.
     * 
     * @return Position of you next shot.
     */
    @Override
    public Position getFireCoordinates(Fleet enemyShips)
    {
        Position pos = shooterObject.getNextShootingPosition();
        return pos;
    }

    
    /**
     * Called right after getFireCoordinates(...) to let your AI know if you hit
     * something or not. 
     * 
     * Compare the number of ships in the enemyShips with that given in 
     * getFireCoordinates in order to see if you sunk a ship.
     * 
     * @param hit boolean is true if your last shot hit a ship. False otherwise.
     * @param enemyShips Fleet the enemy's ships.
     */
    @Override
    public void hitFeedBack(boolean hit, Fleet enemyShips)
    {
         shooterObject.hitFeedBack(hit, enemyShips);
    }    

    
    /**
     * Called in the beginning of each match to inform about the number of 
     * rounds being played.
     * @param rounds int the number of rounds i a match
     */
    @Override
    public void startMatch(int rounds)
    {
        //Do nothing
    }
    
    
    /**
     * Called at the beginning of each round.
     * @param round int the current round number.
     */
    @Override
    public void startRound(int round)
    {
        //Do nothing
    }

    
    /**
     * Called at the end of each round to let you know if you won or lost.
     * Compare your points with the enemy's to see who won.
     * 
     * @param round int current round number.
     * @param points your points this round: 100 - number of shot used to sink 
     * all of the enemy's ships. 
     *
     * @param enemyPoints int enemy's points this round. 
     */
    @Override
    public void endRound(int round, int points, int enemyPoints)
    {
        //Do nothing
    }
    
    
    /**
     * Called at the end of a match (that usually last 1000 rounds) to let you 
     * know how many losses, victories and draws you scored.
     * 
     * @param won int the number of victories in this match.
     * @param lost int the number of losses in this match.
     * @param draw int the number of draws in this match.
     */
    @Override
    public void endMatch(int won, int lost, int draw)
    {
        //Do nothing
    }

    public Shooter getShooterObject() {
        return shooterObject;
    }

    public void setSizeX(int sizeX) {
        this.sizeX = sizeX;
    }

    public void setSizeY(int sizeY) {
        this.sizeY = sizeY;
    }

    public void setShipTrackList(int[][] shipTrackList) {
        this.shipTrackList = shipTrackList;
    }
    
    
}
