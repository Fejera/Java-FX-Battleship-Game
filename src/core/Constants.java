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
//Class that holds all Constant information for Ships used on Board
public class Constants 
{
    //Constants for ship lengths
    public static final int CARRIER_LENGTH = 5;
    public static final int BATTLESHIP_LENGTH = 4;
    public static final int SUBMARINE_LENGTH = 3;
    public static final int DESTROYER_LENGTH = 3;
    public static final int PATROL_BOAT_LENGTH = 2;
    
    //Constants for ship reference
    public static final int CARRIER = 0;
    public static final int BATTLESHIP = 1;
    public static final int SUBMARINE = 2;
    public static final int DESTROYER = 3;
    public static final int PATROL_BOAT = 4;
    
    //Constants for ship direction
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    
     //Constants for the players in the array
    public static final int PLAYER_ONE = 0;
    public static final int PLAYER_TWO = 1;
    
    //Constants for the play mode
    public static final int HUMAN = 0;
    public static final int COMPUTER = 1;
}
