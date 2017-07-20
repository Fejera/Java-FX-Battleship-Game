package userInterface;

import core.Constants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.*;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.border.BevelBorder;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author alyssa
 */
public class PlayerOptionDialog
{
    //Variable Declarations
    private JLabel shipColorLbl; 
    private JLabel firstPlayerLbl;
    private JPanel playerOptionsPanel;
    private JPanel optionsPanel;
    private JPanel buttonPanel;
    private JComboBox shipColor;
    private JComboBox firstPlayer;
    private JButton saveBtn;
    private JButton canxBtn;
    private JRadioButton player1;
    private JRadioButton player2;
    private ButtonGroup playerOptions;
    private JDialog dialog;
    private Player[] playerArray;
    private static Color[] color = {Color.CYAN, Color.GREEN, Color.MAGENTA, 
                                    Color.ORANGE, Color.PINK, Color.WHITE, 
                                    Color.YELLOW};

    // data for JComboBox    
    private static String[] colors = {"Cyan", "Green", "Magenta", 
                                      "Orange", "Pink", "Red", "White", "Yellow"};
    private static String[] players = {"Player 1", "Player 2", "Random"};
    
    private String selectedColor;
    private String selctedPlayer;
    
    private Player currentPlayer;
    
    // Custom constructor    
    public PlayerOptionDialog(JFrame parent, Player[] inPlayers)
    {
        playerArray = inPlayers;
        initComponents(parent);
    }

    // initialize UI
    private void initComponents(JFrame parent)
    {
        //Dialog
        dialog = new JDialog(parent, true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        //Options Panels
        optionsPanel= new JPanel();
        optionsPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        playerOptionsPanel = new JPanel(new GridLayout(3, 2));
        playerOptionsPanel.setBorder(BorderFactory.createTitledBorder("Player Options"));
        playerOptionsPanel.setPreferredSize(new Dimension(275, 125));
        
        //Button group for player to selct options per player
        playerOptions = new ButtonGroup();
        player1 = new JRadioButton("Player 1"); //JRadio allows for only 1 selection
        player1.addActionListener(new PlayerListener());
        player2 = new JRadioButton("Player 2");
        player2.addActionListener(new PlayerListener());
        playerOptions.add(player1);
        playerOptions.add(player2);
        playerOptionsPanel.add(player1);
        playerOptionsPanel.add(player2);
        
        //Menu to allow user to choose color for ships
        shipColorLbl = new JLabel("Ship Color");
        playerOptionsPanel.add(shipColorLbl);
        shipColor = new JComboBox (colors);
        shipColor.setSelectedIndex(0);
        playerOptionsPanel.add(shipColor);
        
        //Allows use to choose who will play first
        firstPlayerLbl = new JLabel("Who plays first?");
        playerOptionsPanel.add(firstPlayerLbl);
        firstPlayer = new JComboBox(players);
        firstPlayer.setSelectedIndex(0);
        playerOptionsPanel.add(firstPlayer);
        
        //Options Panel
        optionsPanel.add(playerOptionsPanel);
        
        //Button Panel
        saveBtn = new JButton("Save");
        saveBtn.addActionListener(new SaveListener());
        
        canxBtn = new JButton("Cancel");
        canxBtn.addActionListener(new CancelListener());
        
        //Panel to display save and cancel buttons
        buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        buttonPanel.add(saveBtn);
        buttonPanel.add(canxBtn);
        
        //Final Setup
        dialog.setTitle("Options");
        dialog.setLayout(new BorderLayout());
        dialog.getContentPane().add(optionsPanel,BorderLayout.CENTER);
        dialog.getContentPane().add(buttonPanel,BorderLayout.SOUTH);
        dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        dialog.setPreferredSize(new Dimension (300, 225));
        dialog.setMinimumSize(new Dimension(300, 225));
        dialog.setLocation(200, 200);
        dialog.setVisible(true); 
    }
    
    private void initObjects()
    {
        currentPlayer = new Player();
    }
    
    private class PlayerListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e) 
        {
            //which Radio Button is selected
            if(player1.isSelected())
            {
                int colorIndex = Arrays.asList(color).indexOf(playerArray[Constants.PLAYER_ONE].getShipColor());
                shipColor.setSelectedIndex(colorIndex);
                
                
                if(playerArray[Constants.PLAYER_ONE].isIsFirst())
                {
                    firstPlayer.setSelectedIndex(Constants.PLAYER_ONE);
                }
                else
                {
                    firstPlayer.setSelectedIndex(Constants.PLAYER_TWO);
                }
                
                currentPlayer = playerArray[Constants.PLAYER_ONE];
            }
            else if(player2.isSelected())
            {
                int colorIndex = Arrays.asList(color).indexOf(playerArray[Constants.PLAYER_TWO].getShipColor());
                shipColor.setSelectedIndex(colorIndex);
                
                
                if(playerArray[Constants.PLAYER_ONE].isIsFirst())
                {
                    firstPlayer.setSelectedIndex(Constants.PLAYER_ONE);
                }
                else
                {
                   firstPlayer.setSelectedIndex(Constants.PLAYER_TWO);
                }
                
                currentPlayer = playerArray[Constants.PLAYER_TWO];
            }

        }       
    }
    
    private class SaveListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e) 
        {
            if(player1.isSelected())
            {
                playerArray[Constants.PLAYER_ONE].setShipColor(color[shipColor.getSelectedIndex()]);
                boolean isFirst = firstPlayer.getSelectedIndex() == 0 ? true : false;
                playerArray[Constants.PLAYER_ONE].setIsFirst(isFirst);
                playerArray[Constants.PLAYER_TWO].setIsFirst(!isFirst);
            }
            else if(player2.isSelected())
            {
                playerArray[Constants.PLAYER_TWO].setShipColor(color[shipColor.getSelectedIndex()]);
                boolean isFirst = firstPlayer.getSelectedIndex() == 0 ? true : false;
                playerArray[Constants.PLAYER_TWO].setIsFirst(isFirst);
                playerArray[Constants.PLAYER_ONE].setIsFirst(!isFirst);
            }
            
            dialog.dispose();
        }   
        
    }
    private class CancelListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e) 
        {
            dialog.dispose();
        }
        
    }
}
