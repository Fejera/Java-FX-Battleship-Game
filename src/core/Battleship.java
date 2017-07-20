/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

/**
 *
 * @author alyssa
 */
public class Battleship extends Ship 
{
    //Method that holds Battleship information
    public Battleship()
    {
        setShipLength(Constants.BATTLESHIP_LENGTH);
        setShipName("Battleship");
        setMaxHits(Constants.BATTLESHIP_LENGTH);
    }
}
