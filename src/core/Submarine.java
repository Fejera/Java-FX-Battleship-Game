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
public class Submarine extends Ship
{
    //Method that holds Submarine information
    public Submarine()
    {
        setShipLength(Constants.SUBMARINE_LENGTH);
        setShipName("Submarine");
        setMaxHits(Constants.SUBMARINE_LENGTH); 
    }
}
