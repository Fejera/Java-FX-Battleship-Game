/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userInterface;

import core.Constants;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static javafx.scene.paint.Color.color;
import javax.swing.*;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.border.BevelBorder;


/**
 *
 * @author alyssa
 */
public class GameOptionDialog 
{
    //Variable Declarations
    private JDialog dialog;
    private JPanel gameOptionsPanel;
    private JPanel gamePanel;
    private JPanel buttonPanel;
    
    private JLabel computerAiLbl;
    private JLabel shipLayoutLbl;
    private JComboBox computerAi;
    private JComboBox shipLayout;
    private JButton saveBtn;
    private JButton canxBtn;

    private ButtonGroup gameOptions;

    // data for the JComboBox    
    String[] level = {"Normal", "Ridiculously Hard"}; 
    String[] layout = {"Manual","Automatic"};

    private String selectedLevel; 
    private String selectedLayout;
    
    // array of Players
    private Player[] playerArray;

    // Customer constructor    
    public GameOptionDialog(JFrame parent, Player[] inPlayers)
    {
        playerArray = inPlayers;
        initComponents(parent);
    }
    
    // initialize components    
    private void initComponents(JFrame parent)
    {
         //Dialog
        dialog = new JDialog(parent, true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        //Options Panels
        gamePanel= new JPanel();
        gamePanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        gameOptionsPanel = new JPanel(new GridLayout(2, 2));
        gameOptionsPanel.setBorder(BorderFactory.createTitledBorder("Game Options"));
        gameOptionsPanel.setPreferredSize(new Dimension(275, 100));
        
        //Variable to connect Buttons to a group
        gameOptions = new ButtonGroup();
        
        //Menu for user to select Computer AI Difficulty level
        computerAiLbl = new JLabel("Computer AI");
        gameOptionsPanel.add(computerAiLbl);
        computerAi = new JComboBox (level);
        computerAi.setSelectedIndex(0);
        gameOptionsPanel.add(computerAi);
        
        //Menu for user to select if they would like auto or manual place ships
        shipLayoutLbl = new JLabel("Ship Layout");
        gameOptionsPanel.add(shipLayoutLbl);
        shipLayout = new JComboBox(layout);
        shipLayout.setSelectedIndex(0);
        gameOptionsPanel.add(shipLayout);
        
        //Options Panel
        gamePanel.add(gameOptionsPanel);
        
        //Button Panel
        saveBtn = new JButton("Save");
        saveBtn.addActionListener(new SaveListener());
        
        canxBtn = new JButton("Cancel");
        canxBtn.addActionListener(new CancelListener());
        
        //Panel to show save or cancel options
        buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        buttonPanel.add(saveBtn);
        buttonPanel.add(canxBtn);
        
        //Final Setup
        dialog.setTitle("Options");
        dialog.setLayout(new BorderLayout());
        dialog.getContentPane().add(gamePanel,BorderLayout.CENTER);
        dialog.getContentPane().add(buttonPanel,BorderLayout.SOUTH);
        dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        dialog.setPreferredSize(new Dimension (300, 190));
        dialog.setMinimumSize(new Dimension(300, 190));
        dialog.setLocation(200, 200);
        dialog.setVisible(true);
    }
    private class SaveListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e) 
        {
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
