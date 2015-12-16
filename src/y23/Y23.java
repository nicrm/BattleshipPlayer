/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package y23;

import battleship.interfaces.BattleshipsPlayer;
import tournament.player.PlayerFactory;

/**
 *
 * @author Nicolai
 */

public class Y23 implements PlayerFactory<BattleshipsPlayer>
{

    public Y23(){}
    
    
    @Override
    public BattleshipsPlayer getNewInstance()
    {
        return new RandomPlayer();
    }

    @Override
    public String getID()
    {
        return "Y23";
    }

    @Override
    public String getName()
    {
        return "Yellow 23";
    }   
}
