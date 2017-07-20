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
public class Destroyer extends Ship
{
    //Method that holds Destroyer information
    public Destroyer()
    {
        setShipLength(Constants.DESTROYER_LENGTH);
        setShipName("Destroyer");
        setMaxHits(Constants.DESTROYER_LENGTH);
    }
}
