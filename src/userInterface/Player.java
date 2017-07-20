/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userInterface;

import core.Battleship;
import core.Carrier;
import core.Constants;
import core.Destroyer;
import core.PatrolBoat;
import core.Ship;
import core.Submarine;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JOptionPane;


/**
 *
 * @author alyssa
 */
public class Player 
{
    //Class Variables
    private Color shipColor;
    private boolean isFirst;
    private int currentShip;
    private int currentShipLength;
    private int currentDirection;
    private JButton[][] buttonBoard;
    private ArrayList<Ship> ships;
    
    //Instance Variable
    private String userName;
    
    private BattleshipUI parent;
    
    private final static int rows = 10;
    private final static int cols = 10;
    
    //Variables for Ships
    private Battleship battleship;
    private Carrier carrier;
    private Destroyer destroyer;
    private PatrolBoat patrolBoat;
    private Submarine submarine;
    
    private BoardListener boardListener;
    private int playmode;

    public Player()
    {
    }
    
    //Connects Class Playaer to Parent Battleship UI
    public Player (String name, BattleshipUI inparent)
    {
        parent = inparent;
        userName = name;
        
        initObjects();
        initComponents();
    }
    
    //Sets Class BattleshipUI as Parent to this Class
    public void setParent(BattleshipUI inParent)
    {
        parent = inParent;
    }
    
    //Initiates Objects or Ships within Class
    private void initObjects()
    {
        ships = new ArrayList<Ship>();
        
        //When User selelcts a ship ths links to Ship Class for Ship Info
        carrier = new Carrier();
        ships.add(Constants.CARRIER, carrier);
        battleship = new Battleship();
        ships.add(Constants.BATTLESHIP, battleship);
        submarine = new Submarine();
        ships.add(Constants.SUBMARINE, submarine);
        destroyer = new Destroyer();
        ships.add(Constants.DESTROYER, destroyer);
        patrolBoat = new PatrolBoat();
        ships.add(Constants.PATROL_BOAT, patrolBoat);
        
        boardListener = new BoardListener();     
    }

    //Grid Button For Loop 
    private void initComponents()
    {
        buttonBoard = new JButton[10][10];
         
        for(int i=0; i < 10; i++)
        {
            for(int j=0; j < 10; j++)
            {
                buttonBoard[i][j] = new JButton();
                buttonBoard[i][j].putClientProperty("row", i);
                buttonBoard[i][j].putClientProperty("col", j);
                buttonBoard[i][j].setOpaque(true);
                setListener(buttonBoard[i][j], boardListener);
                
            }
        }
    }
    
    //Returns variable boardListener to Parent BattleshipUI
    public BoardListener getBoardListener()
    {
        return boardListener;
    }
    
    public void setListener(JButton inButton, ActionListener inListener)
    {
        inButton.addActionListener(inListener);
    }
    
    public void removeListener(JButton inButton, ActionListener inListnener)
    {
        inButton.removeActionListener(inListnener);
    }
    
    //Getters and Setters for PlayMode
    public int getPlayMode()
    {
        return playmode;
    }
    public void setPlayMode(int playmode)
    {
        this.playmode = playmode;
    }
    
    public String getUserName()
    {
        return userName;
    }
    
    //Method to return buttonBoard array back to BattleshipUI
    public JButton[][] getBoard()
    {
        return buttonBoard;
    }
    
    /**
     * @param buttonBoard the buttonBoard to set
     */
    public void setButtonBoard(JButton[][] buttonBoard) 
    {
        this.buttonBoard = buttonBoard;
    }

    /**
     * @return the shipColor
     */
    public Color getShipColor() 
    {
        return shipColor;
    }

    /**
     * @param shipColor the shipColor to set
     */
    public void setShipColor(Color shipColor) 
    {
        this.shipColor = shipColor;
    }

    /**
     * @return the isFirst
     */
    public boolean isIsFirst() 
    {
        return isFirst;
    }

    /**
     * @param isFirst the isFirst to set
     */
    public void setIsFirst(boolean isFirst) 
    {
        this.isFirst = isFirst;
    }

    /**
     * @return the currentShip
     */
    public int getCurrentShip() 
    {
        return currentShip;
    }

    //Automatically places Computers ships on board
    public void autoLayout()
    {
        Random random = new Random();
        
        //For loop to cycle through all 5 ships for computer
        for (int ship = 0; ship < getShips().size(); ship++)
        {
            setCurrentShip(ship);
            setCurrentDirection(random.nextInt(2));
     
//            //Statement to show computers ships on the screen for testing code
//            if(getPlayMode () == Constants.COMPUTER)
//                setShipColor(Color.GREEN);
            
            //Variable declarations for random clicks
            int rowClick = random.nextInt(getRows());
            int colClick = random.nextInt(getCols());
            
            //Loop to ensure ships are not placed in ocuupied sqaures
            while(!getShips().get(ship).isShipPlaced())
            {
                if(isValid(rowClick, colClick) && !isOccupied(rowClick, colClick))
                {
                    placeShip(rowClick, colClick);
                    getShips().get(ship).setShipPlaced(true);
                }
                else
                {
                    rowClick = random.nextInt(getRows());
                    colClick = random.nextInt(getCols());
                    setCurrentDirection(random.nextInt(2));
                }
            }
        }
    }
    
    /**
     * @param currentShip the currentShip to set
     */
    //constructor to set currentShip to the ship being selected
    public void setCurrentShip(int currentShip) 
    {
        this.currentShip = currentShip;
        
        //Sets length for ship selected
        if(currentShip == Constants.BATTLESHIP)
            setCurrentShipLength(Constants.BATTLESHIP_LENGTH);
        else if(currentShip == Constants.CARRIER)
            setCurrentShipLength(Constants.CARRIER_LENGTH);
        else if(currentShip == Constants.DESTROYER)
            setCurrentShipLength(Constants.DESTROYER_LENGTH);
        else if(currentShip == Constants.PATROL_BOAT)
            setCurrentShipLength(Constants.PATROL_BOAT_LENGTH);
        else if(currentShip == Constants.SUBMARINE)
            setCurrentShipLength(Constants.SUBMARINE_LENGTH);   
    }

    /**
     * @return the currentShipLength
     */
    public int getCurrentShipLength() 
    {
        return currentShipLength;
    }
     /**
     * @return the rows
     */
    public static int getRows() 
    {
        return rows;
    }

    /**
     * @return the cols
     */
    public static int getCols() 
    {
        return cols;
    } 
    /**
     * @param currentShipLength the currentShipLength to set
     */
    //Sets ship length based on which ship is selected
    public void setCurrentShipLength(int currentShipLength) 
    {
        this.currentShipLength = currentShipLength;   
        
        if(currentShip == Constants.BATTLESHIP)
            currentShipLength = Constants.BATTLESHIP_LENGTH;
        else if(currentShip == Constants.CARRIER)
            currentShipLength = Constants.CARRIER_LENGTH;
        else if(currentShip == Constants.DESTROYER)
            currentShipLength = Constants.DESTROYER_LENGTH;
        else if(currentShip == Constants.PATROL_BOAT)
            currentShipLength = Constants.PATROL_BOAT_LENGTH;
        else if(currentShip == Constants.SUBMARINE)
            currentShipLength = Constants.SUBMARINE_LENGTH;
    }
    
    /**
     * @return the currentDirection
     */
    public int getCurrentDirection() 
    {
        return currentDirection;
    }

    /**
     * @param currentDirection the currentDirection to set
     */
    public void setCurrentDirection(int currentDirection) 
    {
        this.currentDirection = currentDirection;
    }

    /**
     * @return the buttonBoard
     */
    public JButton[][] getButtonBoard() 
    {
        return buttonBoard;
    }

    /**
     * @param buttonBoard the buttonBoard to set
     */
    
    //Returns Battlehship information to Parent Class BattleshipUI
    public Battleship getBattleship()
    {
        return battleship;
    }
    
    //Returns Carrier information to Parent Class BattleshipUI
    public Carrier getCarrier()
    {
        return carrier;
    }
    
    //Returns Destroyer information to Parent Class BattleshipUI
    public Destroyer getDestroyer()
    {
        return destroyer;
    }
    
    //Returns PatrolBoat information to Parent Class BattleshipUI
    public PatrolBoat getPatrolBoat()
    {
        return patrolBoat;
    }
    
    //Returns Submarine information to Parent Class BattleshipUI
    public Submarine getSubmarine()
    {
        return submarine;
    }
    
    //Places and removes ship as User decides where they want ships deployed
    private void placeShip (int rowClick, int colClick)
    {
        if(getCurrentShip() == Constants.BATTLESHIP && getBattleship().isShipPlaced())
        {
            removeShip(getBattleship());
        }
        else if(getCurrentShip() == Constants.CARRIER && getCarrier().isShipPlaced())
        {
            removeShip(getCarrier());
        }
        else if(getCurrentShip() == Constants.DESTROYER && getDestroyer().isShipPlaced())
        {
            removeShip(getDestroyer());
        }
        else if(getCurrentShip() == Constants.PATROL_BOAT && getPatrolBoat().isShipPlaced())
        {
            removeShip(getPatrolBoat());
        }
        else if(getCurrentShip() == Constants.SUBMARINE && getSubmarine().isShipPlaced())
        {
            removeShip(getSubmarine());
        }
        
        //Switch statement to change grid color based on where user places ship 
        switch( getCurrentDirection() )
        {

            case Constants.VERTICAL:
            {
                for(int row = rowClick; row < (rowClick + getCurrentShipLength()); row++)
                {
                    if(getPlayMode()==Constants.HUMAN)
                    {
                        buttonBoard[row][colClick].setBackground(getShipColor());
                    }
                    else
                        buttonBoard[row][colClick].setBackground(getShipColor());
                        //change to null for real play
                }
                
                break;
            }
            case Constants.HORIZONTAL:
            {
                for(int col = colClick; col < (colClick + getCurrentShipLength()); col++)
                {
                    if(getPlayMode()==Constants.HUMAN)
                    {
                        buttonBoard[rowClick][col].setBackground(getShipColor());
                    }
                    else
                        buttonBoard[rowClick][col].setBackground(getShipColor());
                        //change to null for real play
                }
                
                break;
            }
        }
        
        //If/Else statement chain to check if all ships are placed on board
        if(getCurrentShip() == Constants.BATTLESHIP)
        {
            getBattleship().setShipDirection(getCurrentDirection());
            getBattleship().setShipLocation(rowClick, colClick);
            getBattleship().setShipPlaced(true);
        }
        else if(getCurrentShip() == Constants.CARRIER)
        {
            getCarrier().setShipDirection(getCurrentDirection());
            getCarrier().setShipLocation(rowClick, colClick);
            getCarrier().setShipPlaced(true);
        }
        else if(getCurrentShip() == Constants.DESTROYER)
        {
            getDestroyer().setShipDirection(getCurrentDirection());
            getDestroyer().setShipLocation(rowClick, colClick);
            getDestroyer().setShipPlaced(true);
        }
        else if(getCurrentShip() == Constants.PATROL_BOAT)
        {
            getPatrolBoat().setShipDirection(getCurrentDirection());
            getPatrolBoat().setShipLocation(rowClick, colClick);
            getPatrolBoat().setShipPlaced(true);
        }
        else if(getCurrentShip() == Constants.SUBMARINE)
        {
            getSubmarine().setShipDirection(getCurrentDirection());
            getSubmarine().setShipLocation(rowClick, colClick);
            getSubmarine().setShipPlaced(true);
        }
        
        System.out.println("Length " + getCurrentShipLength());
        System.out.println("Vert or Hori: " + getCurrentDirection());
        //Calls method is ReadytoDeploy when all ships are placed
        if(getUserName().equals("Player 1"))
        {
            isReadyToDeploy();
        }

    }

    //Method to check if ships are ready to deploy
    private void isReadyToDeploy()
    {
        //When all the ships are placed return true to enable deploy button
        if(getBattleship().isShipPlaced() == true &&
           getCarrier().isShipPlaced() == true &&
           getDestroyer().isShipPlaced() == true &&
           getPatrolBoat().isShipPlaced() == true &&
           getSubmarine().isShipPlaced() == true)
        {
            parent.setDeployEnabled(true);
        }
    }
    
    //Checks to see if placed selected on board is a valid spot for ship selected
    private boolean isValid(int rowClick, int colClick)
    {
        switch( getCurrentDirection() )
        {
            case Constants.VERTICAL:
            {
                if( (rowClick + getCurrentShipLength()) > getRows() )
                {
                    return false;
                }
                
                break;
            }
            case Constants.HORIZONTAL:
            {
                if( (colClick + getCurrentShipLength()) > getCols() )
                {
                    return false;
                }
                
                break;
            }
        }
        return true;
    }
    
    //Stops user from placing a ship that will not fit in selected spot
    private boolean isOccupied(int rowClick, int colClick)
    {
        switch( getCurrentDirection() )
        {
            case Constants.VERTICAL:
            {
                for(int row = rowClick; row < rowClick + getCurrentShipLength(); row++)
                {
                    if(getButtonBoard()[row][colClick].getBackground() == shipColor)
                    {
                        return true;
                    }
                }
                
                break; 
            }
            case Constants.HORIZONTAL:
            {
                for(int col = colClick; col < colClick + getCurrentShipLength(); col++)
                {
                    if(getButtonBoard()[rowClick][col].getBackground() == shipColor)
                    {
                        return true;
                    }
                }
                
                break; 
            }
        }
        return false;
    }
    
    //Method that removes ship as user changes ship position on board
    private void removeShip (Ship inShip)
    {
        switch( inShip.getShipDirection() )
        {
            case Constants.VERTICAL:
            {
                for(int row = inShip.getShipStartRow(); row < (inShip.getShipStartRow() + inShip.getShipLength()); row ++)
                {
                    buttonBoard[row][inShip.getShipStartCol()].setBackground(null);
                }
                
                break;
            }
            case Constants.HORIZONTAL:
            {
                for(int col = inShip.getShipStartCol(); col < (inShip.getShipStartCol() + inShip.getShipLength()); col ++)
                {
                    buttonBoard[inShip.getShipStartRow()][col].setBackground(null);
                }
                
                break;
            }
        }
        
        inShip.setShipPlaced(false);
    }
    
    //ArrayList for all ships
    public ArrayList<Ship> getShips()
    {
        return ships;
    }
    
    //Listener to show user if selected spot on board is valid or not
    //and promopts user to change ship placement
    public class BoardListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            if( e.getSource() instanceof JButton )
            {
                //type casting
                JButton button = (JButton)e.getSource();
                int rowClick = (int)button.getClientProperty("row");
                int colClick = (int)button.getClientProperty("col");
                
                //checking if ship will fit
                if(!isValid(rowClick, colClick))
                {
                    JOptionPane.showMessageDialog(null, "Ship will not fit in "
                            + "selected location, select a different location", 
                            "Try again",JOptionPane.ERROR_MESSAGE); 
                }
                //Check if space is occupied
                else if (isOccupied(rowClick, colClick))
                {
                    JOptionPane.showMessageDialog(null, "One or more spaces are "
                            + "occupied, select again",
                            "Try again",JOptionPane.ERROR_MESSAGE);
                }
                //Place the ship
                else
                {
                    placeShip(rowClick, colClick);
                }
            }
        } 
    }
    
    //Function to check if square selected is occupied by a ship
    public boolean isHit(int rowClick, int colClick)
    {
        for(Ship ship: ships)
        {
            //Chain of for loops to loop through rows and coloumns of game board
            for(int row = ship.getShipStartRow(); row <= ship.getShipStopRow(); row ++)
            {
                for(int col = ship.getShipStartCol(); col <= ship.getShipStopCol(); col ++)
                {
                    if(row == rowClick && col == colClick)
                    {
                        //if there is a ship in the sqaure then keep track of how many hits are left till its sunk
                        ship.minusHitsLeft();
                        
                        //If the ship is sunk display to user which ship has sunk
                        if(ship.isShipSunk() == true)
                        {
                            //ship.setShipSunk(true);
                            parent.updateTextArea("\n" + ship.getShipName() + " is sunk! ");
                          
                        }
                        return true;
                    } 
                }
            }
        }
        return false;
    }
}