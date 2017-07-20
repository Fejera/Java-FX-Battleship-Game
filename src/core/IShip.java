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
public interface IShip 
{
    public static final int MAX_SHIPS = 5;
    
    //Method Signature
    public boolean isShipPlaced();
    public void setShipLocation(int row, int column);
    
    public int getShipLength();
    public void setShipLength(int inLength);
    
    public boolean isShipSunk();
    
    public String getShipName();
    public void setShipName(String inName);
    
    public int getHitsLeft();
    public int getMaxHits();
    public void setMaxHits(int inHits);
    
    public int getShipDirection();
    public void setShipDirection(int inDirection);
}
