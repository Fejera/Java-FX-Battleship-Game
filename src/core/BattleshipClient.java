/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import userInterface.BattleshipUI;
import userInterface.Player;
/**
 *
 * @author alyssa
 */
public class BattleshipClient 
{
    Player[] players;
    BattleshipUI ui;
    Player currentPlayer;
    
    public BattleshipClient (Player[] players, BattleshipUI ui)
    {
        this.players = players;
        this.ui = ui;
    }
    
    //Function to display which players turn it is
    public void play()
    {
        changeListener();
        
        //Check to see when player user selected to go first
        if(players[Constants.PLAYER_ONE].isIsFirst())
        {
            ui.updateTextArea("Player 1 Turn"); 
            currentPlayer = players[Constants.PLAYER_ONE];
        }
        else if (players[Constants.PLAYER_TWO].isIsFirst())
        {
            ui.updateTextArea("Player 2 Turn");
            currentPlayer = players[Constants.PLAYER_TWO];
            computerPick();
        }
        else
        {
            ui.updateTextArea("Player 1 Turn");
            currentPlayer = players[Constants.PLAYER_ONE];
        }
    }
    
    //allows game play to switch from player 1 bord to player 2 board
    private void changeListener()
    {
        for(Player player: players)
        {
            for(int row = 0; row < player.getRows(); row++)
            {
                for(int col = 0; col < player.getCols(); col++)
                {
                    player.removeListener(player.getBoard()[row][col], player.getBoardListener());
                }
            }
        }
        for(int row = 0; row < players[Constants.PLAYER_TWO].getRows(); row++)
            {
                for(int col = 0; col < players[Constants.PLAYER_TWO].getCols(); col++)
                {
                    players[Constants.PLAYER_TWO].setListener(players[Constants.PLAYER_TWO].getBoard() [row][col], new PlayerListener());
                }
            }
    }
    
    //Checks if square selected has already been picked by PLayer 1 or 2
    public class PlayerListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e) 
        {
            if(e.getSource() instanceof JButton)
            {
                JButton button = (JButton)e.getSource();
                int rowClick = (int)button.getClientProperty("row");
                int colClick = (int)button.getClientProperty("col");
                
                //Statement for when square is already picked
                if(players[Constants.PLAYER_TWO].getBoard()[rowClick][colClick].getBackground() == Color.RED ||
                   players[Constants.PLAYER_TWO].getBoard()[rowClick][colClick].getBackground() == Color.BLUE)
                {
                    ui.updateTextArea("\nAlready picked square\nPlayer 1 lose turn!\n");
                }
                //Statement for if square picked had a ship
                else if(players[Constants.PLAYER_TWO].isHit(rowClick, colClick))
                {
                    ui.updateTextArea("\nPLAYER 1 HIT A SHIP!\n");
                    players[Constants.PLAYER_TWO].getBoard()[rowClick][colClick].setBackground(Color.RED);
                }
                //Statement for if square was not previously picked or had a ship on it
                else
                {
                    players[Constants.PLAYER_TWO].getBoard()[rowClick][colClick].setBackground(Color.BLUE);
                }
                
                //Method to switch players
                switchPlayers();
            }
        } 
    }
    
    //Function to trade which Player's turn it is
    private void switchPlayers()
    {
        //Statement to go to checkForWinner function, it returns true, game ends
        if(checkForWinner())
        {
            endGame();
        }
        //Otherwise current player is switched to next player
        else if(currentPlayer == players[Constants.PLAYER_ONE])
        {
            currentPlayer = players[Constants.PLAYER_TWO];
            ui.updateTextArea("Player 2, your turn.");
            computerPick();
        }
        else if(currentPlayer == players[Constants.PLAYER_TWO])
        {
            currentPlayer = players[Constants.PLAYER_ONE];
            ui.updateTextArea("Player 1, your turn.");
        }
    }
    
    //Function to check if all five of a players ships have been sunk
    private boolean checkForWinner()
    {
        int ships = 0;
        
        for(Player player : players)
        {
            //Loop to check if all the ships have sunk
            for(Ship ship: player.getShips())
            {
                if(ship.isShipSunk())
                {
                    ships++;
                }
            }
            //Once all the ships have sunk, that player loses
            if(ships == player.getShips().size())
            {
                JOptionPane.showMessageDialog(ui, player.getUserName() + " has lost.");
                return true;    
            }
            ships = 0;
            
        }
        return false;
    }
    
    //Function to end the game when one player has all fince ships sunk
    private void endGame()
    {
        //For loop to disable board once game is over
        for(Player player : players)
        {
            for(int row = 0; row < player.getRows(); row++)
            {
                for(int col = 0; col < player.getCols(); col++)
                {
                    player.getBoard()[row][col].setEnabled(false);
                }
            }
        }
    }
    
    //Function for the Computer to pick a square on the board
    private void computerPick()
    {
        //Allows computer to choose random square
        Random random = new Random();
        int rowClick = random.nextInt(10);
        int colClick = random.nextInt(10);
        
        //Statement to check if square has already been chosen
        if(players[Constants.PLAYER_ONE].getBoard()[rowClick][colClick].getBackground() == Color.RED ||
           players[Constants.PLAYER_ONE].getBoard()[rowClick][colClick].getBackground() == Color.BLUE)
        {
            ui.updateTextArea("\nAlready picked square\nPlayer 2 lose turn!\n");
        }
        //Statment to display message and check if square had a ship in it
        else if(players[Constants.PLAYER_ONE].isHit(rowClick,colClick))
        {
            ui.updateTextArea("\nPLAYER 2 HIT A SHIP!\n");
            players[Constants.PLAYER_ONE].getBoard()[rowClick][colClick].setBackground(Color.RED);
        }
        //Statement to display blue if square was not occupied by a ship
        else
        {
            players[Constants.PLAYER_ONE].getBoard()[rowClick][colClick].setBackground(Color.BLUE);
        }
        
        //Fucntion call to swicth players
        switchPlayers();
    }
}
