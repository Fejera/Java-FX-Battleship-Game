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
public class PatrolBoat extends Ship
{
    //Method that holds PatrolBoat information
    public PatrolBoat()
    {
        setShipLength(Constants.PATROL_BOAT_LENGTH);
        setShipName("Patrol Boat");
        setMaxHits(Constants.PATROL_BOAT_LENGTH);  
    }
}
