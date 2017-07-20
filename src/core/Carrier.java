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
public class Carrier extends Ship
{
    //Method that holds Carrier information
    public Carrier()
    {
        setShipLength(Constants.CARRIER_LENGTH);
        setShipName("Carrier");
        setMaxHits(Constants.CARRIER_LENGTH);
    }
}
