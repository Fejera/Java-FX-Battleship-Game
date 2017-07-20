/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userInterface;

import core.BattleshipClient;
import core.Constants;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.BevelBorder;


/**
 *
 * @author alyssa
 */
public class BattleshipUI extends JFrame 
{
    //Variable declarations
    private JMenuBar menuBar;    
    private JMenu gameMenu;
    private JMenu optionMenu;
    private JMenuItem playerPlayer;
    private JMenuItem playerComputer;
    private JMenuItem computerComputer;	
    private JMenuItem exit;	
    private JMenuItem game;
    private JMenuItem player;
    
    //Buttons
    private JButton deploy;
    
    //Lay out the UI
    private JPanel shipLayoutPanel;
    private static JPanel playerOnePanel;
    private static JPanel playerTwoPanel;
    private static JPanel statusPanel;
    private static JScrollPane scrollPane;
    private static JTextArea textArea;
    
    private JComboBox shipCb;
    private JComboBox directionCb;
    private JButton[][] buttonArray;

    // Data arrays for various components on the UI
    final private String[] rowLetters = {" ","A","B","C","D","E","F","G","H","I","J"};
    final private String[] columnNumbers = {" ","1","2","3","4","5","6","7","8","9","10"};
    final private String[] ships = {"Carrier","Battleship","Submarine","Destroyer", "Patrol Boat"};
    final private String[] direction = {"Horizontal","Vertical"};
    
    //Event Handlers
    private GameListener gameListener;
    private OptionsListener optionListener;
    private DeployListener deploylistener;
    
    private Player playerOne;
    private Player playerTwo;
    private Player[] players = new Player[2];
    
    private BattleshipClient gameControl;

    //Constructor to initiate game frame and player methods
    public BattleshipUI()
    {
        initObjects();  
        initComponents(); 
    }
    
    //Returns this code to parent BattleshipGame
    private JFrame getThisParent()
    {
        return this;
    }
    
    //Method to initialize players
    private void initObjects()
    {
        //player variable declarations for new player
        playerOne = new Player("Player 1", this);
        playerTwo = new Player("Player 2", this );
        
        //Assigns player variables to spot in players array
        players[Constants.PLAYER_ONE]= playerOne;
        players[Constants.PLAYER_TWO]= playerTwo;
       
        for(Player player : players)
        {
            System.out.println(player.getUserName() + " is playing in the game");
        }
        
        //Set defaults for initial game selection assuming it is human vs. computer
        players[Constants.PLAYER_ONE].setPlayMode(Constants.HUMAN);
        players[Constants.PLAYER_TWO].setPlayMode(Constants.COMPUTER);
        
        if(players[Constants.PLAYER_ONE].getPlayMode() == Constants.COMPUTER)
        {
            players[Constants.PLAYER_ONE].autoLayout();
        }
        
        if(players[Constants.PLAYER_TWO].getPlayMode() == Constants.COMPUTER)
        {
            players[Constants.PLAYER_TWO].autoLayout();
        }
        
        gameControl = new BattleshipClient(players, this);
       
    }
    
    //Method to initialize frame or componets of game
    private void initComponents()
    {
        //Dimensions for game window
        this.setTitle("Battleship");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension (500,500));
        this.setMinimumSize(new Dimension (500, 500));
        
        //Change look of UI for MAC
        try
        {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
        //Variable declarations for Top Menu Bar
        menuBar = new JMenuBar();
        gameMenu = new JMenu("Game");
        gameMenu.setMnemonic('G');
        optionMenu = new JMenu("Options");
        optionMenu.setMnemonic('O');
        
        //Adding Top Menu categories to Menu Bar
        menuBar.add(gameMenu);
        menuBar.add(optionMenu);
        this.setJMenuBar(menuBar);
        
        //Variable pointer to GameListener Class
        gameListener = new GameListener();
        
        //Menu Item Declarations for Top Bar Menu: Game
        playerPlayer = new JMenuItem("Player vs. Player");
        playerPlayer.addActionListener(gameListener);
        playerPlayer.setEnabled(false);
        gameMenu.add(playerPlayer);
        
        playerComputer = new JMenuItem("Player vs. Computer");
        playerComputer.addActionListener(gameListener);
        playerComputer.setSelected(true);
        gameMenu.add(playerComputer);
        
        computerComputer = new JMenuItem("Computer vs. Computer");
        computerComputer.addActionListener(gameListener);
        computerComputer.setEnabled(false);
        gameMenu.add(computerComputer);
        
        //Exit Menu Item Declaration for Top Bar Menu: Game
        exit = new JMenuItem("Exit");
        exit.addActionListener(new ExitListener());
        gameMenu.add(exit);
        
        //Variable pointer to OptionListener Class
        optionListener = new OptionsListener();
        
        //Menu Item Declarations for Top Bar Menu: Options
        game = new JMenuItem("Game Options");
        game.addActionListener(optionListener);
        optionMenu.add(game);
        
        player = new JMenuItem("Player Options");
        player.addActionListener(optionListener);
        optionMenu.add(player);
  
        //button
        deploylistener = new DeployListener();
        deploy = new JButton ("Deploy Ships");
        deploy.setEnabled(false);
        deploy.addActionListener(deploylistener);
        
        //Declaration for Ship Drop-down Menu
        shipCb = new JComboBox();
        for(String ship: ships)
        {
            shipCb.addItem(ship);
        }
        
        shipCb.addActionListener(new ShipListener());
        shipCb.setSelectedIndex(0);
        
        directionCb = new JComboBox(direction);
        directionCb.addActionListener(new DirectionListener());
        directionCb.setSelectedIndex(0);
        
        //UI for Ship Selection and Layout
        shipLayoutPanel = new JPanel(new FlowLayout());
        shipLayoutPanel.setBorder(BorderFactory.createTitledBorder("Select Ship and Direction"));
        shipLayoutPanel.add(shipCb);
        shipLayoutPanel.add(directionCb);
        shipLayoutPanel.add(deploy);        
       
                // Status panel setup
        statusPanel = new JPanel();
        statusPanel.setBorder(BorderFactory.createTitledBorder("Game Status"));
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(180, 350));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        statusPanel.add(scrollPane);
        
        //Grid Dimension Layout
        //playerOnePanel = new JPanel();
        playerOnePanel = new JPanel(new GridLayout(11, 11));
        playerOnePanel.setMinimumSize(new Dimension(400, 400));
        playerOnePanel.setPreferredSize(new Dimension(400, 400));
        playerOnePanel.setBorder(BorderFactory.createTitledBorder("Player One"));

        buttonArray = new JButton[10][10];
        buttonArray = playerOne.getBoard();

        //Grid Loop
        for(int row=0; row < 11; row++)
        {
            for(int col=0; col < 11; col++)
            {
                if(row == 0)
                {
                    JLabel colLabel = new JLabel(columnNumbers[col]);
                    colLabel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                    playerOnePanel.add(colLabel);
                }
                else if(row > 0 && col == 0)
                {
                    JLabel rowLabel = new JLabel(rowLetters[row]);
                    rowLabel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                    playerOnePanel.add(rowLabel);
                }
                else
                {
                    playerOnePanel.add(buttonArray[row-1][col-1]);
                }
            }
        }
        
        //Grid Dimension Layout
        //playerTwoPanel = new JPanel();
        playerTwoPanel = new JPanel(new GridLayout(11, 11));
        playerTwoPanel.setMinimumSize(new Dimension(400, 400));
        playerTwoPanel.setPreferredSize(new Dimension(400, 400));
        playerTwoPanel.setBorder(BorderFactory.createTitledBorder("Player Two"));

        JButton[][] playerTwoArray = playerTwo.getBoard();
        playerTwoArray = playerTwo.getBoard();

        //Grid Loop
        for(int row=0; row < 11; row++)
        {
            for(int col=0; col < 11; col++)
            {
                if(row == 0)
                {
                    JLabel colLabel = new JLabel(columnNumbers[col]);
                    colLabel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                    playerTwoPanel.add(colLabel);
                }
                else if(row > 0 && col == 0)
                {
                    JLabel rowLabel = new JLabel(rowLetters[row]);
                    rowLabel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                    playerTwoPanel.add(rowLabel);
                }
                else
                {
                    playerTwoPanel.add(playerTwoArray[row-1][col-1]);
                }
            }
        }
        
        //Calling Declared arguments to UI
        this.add(shipLayoutPanel, BorderLayout.NORTH);
        this.add(playerOnePanel, BorderLayout.WEST);
        this.setVisible(true);
    }
    
    //Enables the Deploy button when all ships are placed on board
    public void setDeployEnabled(boolean inValue)
    {
        deploy.setEnabled(inValue);
    }
    //Listener for Player Options in Top Bar Menu: Game
    private class GameListener implements ActionListener
    {
        //Method used to determine which Menu Item the user selected
        @Override
        public void actionPerformed(ActionEvent ae) 
        {
            //Conditional Statement to test for selected Menu Item
            if(ae.getActionCommand().equals("Player vs. Player"))
            {
                players[Constants.PLAYER_ONE].setPlayMode(Constants.HUMAN);
                players[Constants.PLAYER_TWO].setPlayMode(Constants.HUMAN);
            }
            else if(ae.getActionCommand().equals("Player vs. Computer"))
            {
                players[Constants.PLAYER_ONE].setPlayMode(Constants.HUMAN);
                players[Constants.PLAYER_TWO].setPlayMode(Constants.COMPUTER);
            }
            else if(ae.getActionCommand().equals("Computer vs Computer"))
            {
                players[Constants.PLAYER_ONE].setPlayMode(Constants.COMPUTER); 
                players[Constants.PLAYER_TWO].setPlayMode(Constants.COMPUTER);        
            }
        }     
    }
    
    //Adds a line in between display messages
    public void updateTextArea(String data)
    {
        textArea.append(data + "\n");
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }
    
    //Listener for exit choice in Top Bar Menu: Game 	
    private class ExitListener implements ActionListener
    {
        @Override
	public void actionPerformed(ActionEvent e)
	{
            //Asks user if they are sure they want to exit game
            int r = JOptionPane.showConfirmDialog(null,"Are you sure you would "
                    + "like to exit Battleship?", "Exit?", JOptionPane.YES_NO_OPTION);
            
            //If user selects yes
            if (r == JOptionPane.YES_OPTION)
		System.exit(0);	
	}	
    }
    
    //Listener for game setup options in Top Bar Menu: Options    
    private class OptionsListener implements ActionListener
    {
        GameOptionDialog gameOptions;
        PlayerOptionDialog playerOptions;
        
        //Method used to determine which Menu Item the user selected
        @Override
        public void actionPerformed(ActionEvent ae) 
        {

            //Conditional Statement to test for selected Menu Item
            if (ae.getActionCommand().equals("Game Options"))
            {
                //Calling action to GameOptionDialog Package Pop-up
                gameOptions = new GameOptionDialog(getThisParent(), players);
            }
            else if (ae.getActionCommand().equals("Player Options"))
            {
                //Calling action to PlayerOptionDialog Package Pop-up
                playerOptions = new PlayerOptionDialog(getThisParent(), players);
            }
        }              
    }
    
    //Listener for which ship is selected from Combo Box
    private class ShipListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            int index = shipCb.getSelectedIndex();
            
            if(shipCb.getSelectedItem().equals("Carrier"))
                playerOne.setCurrentShip(Constants.CARRIER);
            else if(shipCb.getSelectedItem().equals("Battleship"))
                playerOne.setCurrentShip(Constants.BATTLESHIP);
            else if(shipCb.getSelectedItem().equals("Submarine"))
                playerOne.setCurrentShip(Constants.SUBMARINE);
            else if(shipCb.getSelectedItem().equals("Destroyer"))
                playerOne.setCurrentShip(Constants.DESTROYER);
            else if(shipCb.getSelectedItem().equals("Patrol Boat"))
                playerOne.setCurrentShip(Constants.PATROL_BOAT);   
        }
    }
    
    //Listener for the user to select Ship Directon
    private class DirectionListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            if(directionCb.getSelectedItem().equals("Horizontal"))
            {
                playerOne.setCurrentDirection(Constants.HORIZONTAL);
            }
            else if(directionCb.getSelectedItem().equals("Vertical"))
            {
                playerOne.setCurrentDirection(Constants.VERTICAL);
            }
        } 
    }
    
    //Listener to for StatusPane to show to screen
    private class DeployListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e) 
        {
            getThisParent().setMinimumSize(new Dimension(1000, 500));
            getThisParent().add(statusPanel, BorderLayout.CENTER);
            getThisParent().add(playerTwoPanel, BorderLayout.EAST);
            
            //Displays start message to Game Status Pane
            updateTextArea("Let's Play!");
            
            setDeployEnabled(false);
            shipCb.setEnabled(false);
            directionCb.setEnabled(false);
            
            //Method call to initiate game play
            gameControl.play();
        }
    }
}


