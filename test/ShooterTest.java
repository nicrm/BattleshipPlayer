/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import battleship.interfaces.Ship;
import java.util.Iterator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import static sun.invoke.util.ValueConversions.ignore;
import y23.RandomPlayer;
import y23.Shooter;

/**
 *
 * @author Nicolai
 */
public class ShooterTest {
    
    static int[][] shootingTrackList;
    static RandomPlayer randomPlayer;

    @BeforeClass
    public static void setUpClass() {
        randomPlayer = new RandomPlayer();
        randomPlayer.getShooterObject().initializeShooterGameboard(10, 10);
    }

    @Before
    public void setUp() {

    }

    @Test
        @Ignore
    public void testGetShootPositionTest() {
        shootingTrackList = new int[10][10];
        for (int i = 0; i < 10; i++) {
            for (int y = 0; y < 10; y++) {
                {
                    shootingTrackList[i][y] = 1;
                }
            }
        }
        
        shootingTrackList[0][0] = 0;

        randomPlayer.getShooterObject().setShootingTrackList(shootingTrackList);
        Position pos = randomPlayer.getShooterObject().getNextShootingPosition();
        assertEquals(0, pos.x);
        assertEquals(0, pos.y);
    }
    
    
    @Test
    @Ignore
    public void randomPlayer(){
        randomPlayer.getFireCoordinates(null);
    }
    
    @Test
    public void placeShips(){
        
        
        
        randomPlayer.setSizeX(10);
        randomPlayer.setSizeY(10);
        int [][] shipList = new int[10][10];
  
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                shipList[x][y] = 1;
            }
        }
        
        shipList[0][0] = 0;
        shipList[0][1] = 0;
        shipList[0][2] = 0;
        shipList[1][0] = 0;
        shipList[2][0] = 0;
        
        randomPlayer.setShipTrackList(shipList);
        
                
        Position pos = randomPlayer.placeSingleShip(new Ship() {
            @Override
            public int size() {
               return 3;
            }
        }, true);
        
        assertEquals(0, pos.x);
        assertEquals(0, pos.y);
    }
}
