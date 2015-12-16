/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package y23;

import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Nicolai
 */
public class Shooter {
    
        private final int SHOT_FLAG = 1;

    private final static Random rnd = new Random();
    private int sizeX;
    private int sizeY;
    private int[][] shootingTrackList;
    private Position lastShot;
    ArrayList<Position> shootingArea;
    private int enemyShipsToFuck;

    public void initializeShooterGameboard(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        
        shootingTrackList = new int[sizeX][sizeY];
        shootingArea = new ArrayList();
    }

    public int[][] getShootingTrackList() {
        return shootingTrackList;
    }

    public void setShootingTrackList(int[][] shootingTrackList) {
        this.shootingTrackList = shootingTrackList;
    }

    public Position getNextShootingPosition() {
        if (!shootingArea.isEmpty()) {
            int randomIndex = rnd.nextInt(shootingArea.size());
            lastShot = shootingArea.remove(randomIndex);
            if (shootingTrackList[lastShot.x][lastShot.y] == SHOT_FLAG) {
                getNextShootingPosition();
            }else{
                System.out.println("shooting at position: ["+lastShot.x+ "]["+lastShot.y+"]");
            }
        } else {
            int x = rnd.nextInt(sizeX);
            int y = rnd.nextInt(sizeY);

            while (shootingTrackList[x][y] == SHOT_FLAG) {
                x = rnd.nextInt(sizeX);
                y = rnd.nextInt(sizeY);
            }
            lastShot = new Position(x, y);
            System.out.println("shooting at random position");
        }
        shootingTrackList[lastShot.x][lastShot.y] = SHOT_FLAG;
        return lastShot;
    }

    public void hitFeedBack(boolean hit, Fleet enemyShips) {
        if (enemyShips.getNumberOfShips() == enemyShipsToFuck - 1) {
            shootingArea.clear();
            System.out.println("ship sunk");
        }

        enemyShipsToFuck = enemyShips.getNumberOfShips();

        if (hit) {
            if (!(lastShot.y - 1 < 0)) {
                Position north = new Position(lastShot.x, lastShot.y - 1);
                shootingArea.add(north);
            }
            if (!(lastShot.x - 1 < 0)) {
                Position west = new Position(lastShot.x - 1, lastShot.y);
                shootingArea.add(west);
            }
            if (!(lastShot.y + 1 > sizeY)) {
                Position south = new Position(lastShot.x, lastShot.y + 1);
                shootingArea.add(south);
            }
            if (!(lastShot.x + 1 > sizeX)) {
                Position east = new Position(lastShot.x + 1, lastShot.y);
                shootingArea.add(east);
            }
            System.out.println("positions added to shooting area");
        }
    }

    public boolean potentialShip() {
        return false;
    }
}
