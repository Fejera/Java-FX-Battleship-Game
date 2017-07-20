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
public class Ship implements IShip
{
    //Variable declarations
    private boolean shipPlaced;
    private boolean shipSunk;
    
    private int hitsLeft;
    private int maxNumberOfHits;
    private int shipDirection;
    private int shipLength;
    
    private int shipStartRow;
    private int shipStopRow;
    private int shipStartCol;
    private int shipStopCol;
    
    private String shipName;

    //Function to check ship location on the game board
    @Override
    public void setShipLocation(int row, int column) 
    {
        shipStartRow = row;
        shipStartCol = column;
        
        if(getShipDirection() == Constants.HORIZONTAL)
        {
            setShipStopRow(shipStartRow);
            setShipStopCol(shipStartCol + (getShipLength() - 1));
        }
        else if(getShipDirection() == Constants.VERTICAL)
        {
            setShipStopRow(shipStartRow + (getShipLength() - 1));
            setShipStopCol(shipStartCol);
        }
    }

        @Override
    public int getShipLength() 
    {
        return shipLength;
    }
    
    @Override
    public void setShipLength(int inLength)
    {
        shipLength = inLength;
    }

    @Override
    public boolean isShipSunk() 
    {
        return shipSunk;
    }
    
    @Override
    public String getShipName()
    {
        return shipName;
    }
    
    @Override
    public void setShipName(String inName)
    {
        shipName = inName;
    }
    
    @Override
    public int getHitsLeft()
    {
        return hitsLeft;
    }
    
    //Method to keep track of how many hits a ship has left
    public void minusHitsLeft()
    {
        hitsLeft--;

        if(hitsLeft == 0)
            shipSunk = true;
    }

    @Override
    public int getMaxHits()
    {
        return maxNumberOfHits;
    }
    
    @Override
    public void setMaxHits(int inHits)
    {
        maxNumberOfHits = inHits;
        hitsLeft = inHits;
    }
    
    @Override
    public int getShipDirection()
    {
        return shipDirection;
    }
    
    @Override
    public void setShipDirection(int inDirection)
    {
        shipDirection = inDirection;
    }

    /**
     * @return the shipStartRow
     */
    public int getShipStartRow() 
    {
        return shipStartRow;
    }

    /**
     * @return the shipStartCol
     */
    public int getShipStartCol() 
    {
        return shipStartCol;
    }    

    /**
     * @return the shipStopRow
     */
    public int getShipStopRow() 
    {
        return shipStopRow;
    }

    /**
     * @param shipStopRow the shipStopRow to set
     */
    public void setShipStopRow(int shipStopRow) 
    {
        this.shipStopRow = shipStopRow;
    }

    /**
     * @return the shipStopCol
     */
    public int getShipStopCol() 
    {
        return shipStopCol;
    }

    /**
     * @param shipStopCol the shipStopCol to set
     */
    public void setShipStopCol(int shipStopCol) 
    {
        this.shipStopCol = shipStopCol;
    }

    @Override
//    boolean isShipPlaced();
    public boolean isShipPlaced() 
    {
        return shipPlaced;
    }
    
    public void setShipPlaced(boolean inValue) 
    {
        shipPlaced = inValue;
    }

    public void setShipSunk(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
