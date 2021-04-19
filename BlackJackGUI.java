import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import javax.swing.*;
import java.util.Scanner;
import java.util.Random;
import java.awt.*;

/*
*   Current issue: dealer plays only occur after player is done, so not realistic.
*/

public class BlackJackGUI extends JFrame implements ActionListener {
    
    private static final long serialVersionUID = 1L;    // ??

    public static BlackJackGUI blackJackFrame;

    private final int WIDTH = 500;
    private final int HEIGHT = 600;
    
    private JButton hitButton;                      // Hit button
    private JButton standButton;                    // Stand button
    private JButton newGameButton;                  // New Game button
    private JButton resetButton;                    // Reset button NOTE: NOT IMPLEMENTED

    private JLabel playerNameLabel;                 // Label for player
    private JLabel dealerNameLabel;                 // Label for dealer
    private JLabel playerPointsLabel;               // Label for player's points
    private JLabel dealerPointsLabel;               // Label for dealer's points

    private JPanel buttonPanel;                     // Panel for Hit and Stand buttons
    private JPanel playerHandPanel;                 // Panel for player's hand
    private JPanel dealerHandPanel;                 // Panel for dealer's hand

    private JTextArea playerHandArea;               // Text Area for player's hand
    private JTextArea dealerHandArea;               // Text Area for dealer's hand
    private JTextField winStreakField;              // Field for winstreaks
    private JTextField playerPointShow;             // Field for player's points
    private JTextField dealerPointShow;             // FIeld for dealer's points
    
    private int winStreak = 0;

    boolean playGame = true, gameStart = false;     // flags for game logic, playGame = whether to compare hand, gameStart = indicates game in session

    // INITIALIZE PLAYER AND DEALER HAND
    private Hand deck = new Hand("Deck");
    private Hand dealerHand = new Hand("Dealer");
    private Hand playerHand = new Hand("Player");

    public BlackJackGUI(){
        GridBagConstraints layoutConst = null;
        setSize(WIDTH, HEIGHT);
        setTitle("BlackJack");

        // Panels to hold JElements
        buttonPanel = new JPanel();
        playerHandPanel = new JPanel();
        dealerHandPanel = new JPanel();

        // Create labels
        playerNameLabel = new JLabel("Player");
        dealerNameLabel = new JLabel ("Dealer");
        playerPointsLabel = new JLabel("Points");
        dealerPointsLabel = new JLabel("Points");

        // Create button and action listener
        hitButton = new JButton("Hit");
        standButton = new JButton("Stand");
        newGameButton = new JButton("New Game");
        resetButton = new JButton("Reset");

        hitButton.addActionListener(this);
        standButton.addActionListener(this);
        newGameButton.addActionListener(this);
        resetButton.addActionListener(this);

        // JTextFields
        playerHandArea = new JTextArea(12, 15);
        playerHandArea.setEditable(false);

        dealerHandArea = new JTextArea(12, 15);
        dealerHandArea.setEditable(false);

        playerPointShow = new JTextField(2);
        playerPointShow.setEditable(false);

        dealerPointShow = new JTextField(2);
        dealerPointShow.setEditable(false);

        winStreakField = new JTextField(2);
        winStreakField.setEditable(false);
        winStreakField.setBackground(Color.white);

        // PANEL DETAILS
        playerHandPanel.setBackground(Color.gray);
        dealerHandPanel.setBackground(Color.gray);

        playerHandPanel.add(playerHandArea);
        playerHandPanel.add(playerNameLabel);
    
        dealerHandPanel.add(dealerHandArea);
        dealerHandPanel.add(dealerNameLabel);

        buttonPanel.add(hitButton);
        buttonPanel.add(standButton);

        // Use GridBagLayout
        setLayout(new GridBagLayout());
   
        // Player Hand
        layoutConst = new GridBagConstraints();                    
        //layoutConst.insets = new Insets(10, 10, 10, 10);  // (TOP, RIGHT, BOTTOM, LEFT)
        layoutConst.gridx = 0;
        layoutConst.gridy = 1;
        layoutConst.weightx = 1.0;
        layoutConst.weighty = 1.0;
        layoutConst.anchor = GridBagConstraints.CENTER;
        add(playerHandPanel, layoutConst);                  // add playerHandPanel with the set layout
        
        // Dealer Hand
        layoutConst = new GridBagConstraints();
        layoutConst.insets = new Insets(50, 10, 10, 10);
        layoutConst.gridx = 0;
        layoutConst.gridy = 0;
        layoutConst.weightx = 1.0;
        layoutConst.weighty = 1.0;
        layoutConst.anchor = GridBagConstraints.CENTER;
        add(dealerHandPanel, layoutConst);

        // PLAYER POINTS FIELD
        layoutConst = new GridBagConstraints();
        //layoutConst.insets = new Insets(10, 10, 10, 1);
        layoutConst.gridx = 1;
        layoutConst.gridy = 1;
        layoutConst.weightx = 1.0;
        layoutConst.weighty = 1.0;
        layoutConst.anchor = GridBagConstraints.LINE_START;
        add(playerPointShow, layoutConst);

        // PLAYER POINTS LABEL
        layoutConst = new GridBagConstraints();
        layoutConst.insets = new Insets(10, 1, 10, 100);
        layoutConst.gridx = 2;
        layoutConst.gridy = 1;
        layoutConst.anchor = GridBagConstraints.CENTER;
        add(playerPointsLabel, layoutConst);

        // DEALER POINTS FIELD
        layoutConst = new GridBagConstraints();
        //layoutConst.insets = new Insets(10, 10, 10, 1);
        layoutConst.gridx = 1;
        layoutConst.gridy = 0;
        layoutConst.weightx = 1.0;
        layoutConst.weighty = 1.0;
        layoutConst.anchor = GridBagConstraints.LINE_START;
        add(dealerPointShow, layoutConst);

        // DEALER POINTS LABEL
        layoutConst = new GridBagConstraints();
        layoutConst.insets = new Insets(10, 1, 10, 100);
        layoutConst.gridx = 2;
        layoutConst.gridy = 0;
        layoutConst.anchor = GridBagConstraints.CENTER;
        add(dealerPointsLabel, layoutConst);

        // NEW GAME BUTTON
        layoutConst = new GridBagConstraints();
        layoutConst.insets = new Insets(10, 10, 10, 10);
        layoutConst.gridx = 0;
        layoutConst.gridy = 0;
        layoutConst.anchor = GridBagConstraints.NORTHWEST;
        layoutConst.weightx = 1.0;
        layoutConst.weighty = 1.0;
        add(newGameButton, layoutConst);

        // HIT / STAND BUTTONS
        layoutConst = new GridBagConstraints();
        layoutConst.insets = new Insets(10, 10, 30, 10);
        layoutConst.gridx = 2;
        layoutConst.gridy = 3;
        layoutConst.weightx = 1.0;
        layoutConst.weighty = 1.0;
        layoutConst.ipady = 10;
        layoutConst.anchor = GridBagConstraints.NORTH;
        //hitButton.setPreferredSize(new Dimension(50,50));
        add(buttonPanel, layoutConst);

        //WINSTREAK FIELD
        layoutConst = new GridBagConstraints();
        layoutConst.insets = new Insets(10, 10, 10, 10);
        layoutConst.gridx = 3;
        layoutConst.gridy = 0;
        layoutConst.weightx = 1.0;
        layoutConst.weighty = 1.0;
        layoutConst.anchor = GridBagConstraints.NORTHEAST;
        add(winStreakField, layoutConst);

        winStreakField.setText(Integer.toString(winStreak));    // Show initial score = 0
        playerHandArea.setText("Press: \n\'New Game\'\nto start playing.");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent event){
        String cardsInHand = "";
        String dealerCardsInHand = "";
        
        // NEW GAME BUTTON CLICKED
        if (event.getSource() == newGameButton){   
            newGame();
        }
        
        // HIT BUTTON STARTS NEW GAME WHEN GAME IS FINISHED
        if (event.getSource() == hitButton && !gameStart){
            newGame();
        }
        
        else if (event.getSource() == hitButton && !playGame){
            playHit(deck, playerHand);
            checkForAceOver21(playerHand);
            playGame = check21(playerHand);
        }

        // STAND BUTTON CLICKED
        if (event.getSource() == standButton && !gameStart){
            newGame();
        }

        else if (event.getSource() == standButton && !playGame){
            playGame = true;
        }

        if (event.getSource() == resetButton){
            playGame = false;
            gameStart = false;
            winStreak = 0;
        }

        // ADD PLAYER HAND IN STRING
        for (int i = 0; i < playerHand.hand.size(); i++){
            cardsInHand += playerHand.hand.get(i) + " \n";
        }

        // DISPLAY PLAYER HAND AND POINTS WHEN GAME IS IN SESSION
        if (gameStart){
            playerHandArea.setText(cardsInHand);
            playerPointShow.setText(Integer.toString(playerHand.getPoints()));
        }

        // IF IT IS TIME TO COMPARE HANDS, SHOW DEALER DETAILS AND PLAY BLACKJACK
        if (playGame && gameStart){
            dealerPlay(deck, dealerHand);
            for (int i = 0; i < dealerHand.hand.size(); i++){
                dealerCardsInHand += dealerHand.hand.get(i) + " \n";
            }

            if (playerHand.getPoints() <= 21){
                dealerHandArea.setText(dealerCardsInHand);
                dealerPointShow.setText(Integer.toString(dealerHand.getPoints()));
            }
            
            playBlackJack(playerHand, dealerHand);
            winStreakField.setText(Integer.toString(winStreak));

            playGame = false;
            gameStart = false;
        }    
    }

    public void newGame(){
        // RESETS SCORE IF NEW GAME CLICKED DURING GAME
        if (gameStart && !playGame){
            winStreak = 0;
            winStreakField.setText(Integer.toString(winStreak));
        }
        
        // CLEAN TEXT FOR DEALER
        dealerHandArea.setText("");
        dealerPointShow.setText("");
        
        gameStart = true;
        playGame = false;

        gameStart(deck, playerHand, dealerHand);

        playerPointShow.setText(Integer.toString(playerHand.getPoints()));
    }

    public void playHit(Hand deck, Hand hand){
        Card card = deck.getCard(0);                        // Gives card variable the first card from deck
        hand.addCard(card);                                 // Add card to hand
        deck.removeCard(card);                              // Remove card from top of deck
    }

    // TODO: Figure out a better if-else branch for outcomes
    // COMPARES PLAYER AND DEALER HAND
    public void playBlackJack(Hand playerHand, Hand dealerHand){
        if (playerHand.getPoints() < 21){
            if (playerHand.getPoints() > dealerHand.getPoints() || dealerHand.getPoints() > 21){
                JOptionPane.showMessageDialog(new JFrame(), "You win!");
                winStreak++;
                
            }
            else if (playerHand.getPoints() == dealerHand.getPoints()){
                JOptionPane.showMessageDialog(new JFrame(), "Draw!");
            }
            else{
                JOptionPane.showMessageDialog(new JFrame(), "You lose!");
                winStreak = 0;
            }
        }

        else if (playerHand.getPoints() == 21 && dealerHand.getPoints() != 21){
            JOptionPane.showMessageDialog(new JFrame(), "BLACKJACK! You win!");
            winStreak++;
            
        }

        else if (playerHand.getPoints() == 21 && dealerHand.getPoints() == 21){
            JOptionPane.showMessageDialog(new JFrame(), "Draw!");
        }

        else{
            JOptionPane.showMessageDialog(new JFrame(), "You lose!");
            winStreak = 0;
        }
    }

    // CHECKS IF HAND IS 21 OR OVER, IF SO PREVENTS ANY MORE INPUT
    public boolean check21(Hand playerHand){
        return playerHand.getPoints() >= 21;
    }

    // METHOD FOR SHUFFLING DECK
    public void shuffleDeck(Hand deck){
        Card temp;
        int randPlace = 0;
        Random rand = new Random(); // rand.nextInt(52) (get index 0 - 51)
        
        for (int i = 0; i < deck.hand.size(); i++){
            temp = deck.getCard(i);
            randPlace = rand.nextInt(52);
            deck.hand.set(i, deck.getCard(randPlace));
            deck.hand.set(randPlace, temp);
        }
    }

    // CHECK IF THERE ARE ACES AND POINTS ARE OVER 21, IF SO CHANGES ACE TO 1
    public void checkForAceOver21(Hand playerHand){
        for (int i = 0; i < playerHand.hand.size(); i++){
            if (playerHand.getCard(i).getName().equals("Ace") && playerHand.getCard(i).getValue() == 11 && playerHand.getPoints() > 21){
                playerHand.changeAceValue(i);
                break;
            }
            else{
                continue;
            }
        }
    }

    // DEALERS PLAY, IF LESS THAN 16 HITS, OTHERWISE STANDS
    public void dealerPlay(Hand deck, Hand dealerHand){
        boolean dealerDone = false;
        while (!dealerDone){
            if (dealerHand.getPoints() < 16){
                playHit(deck, dealerHand);
                checkForAceOver21(dealerHand);
            }
            else {
                dealerDone = true;
            }
        }
    }

    public Hand initializeDeck(Hand deck){
        // SET UP DECK
        String[] cardNames = {"Ace", "Two", "Three", "Four", "Five", "Six", "Seven", 
        "Eight", "Nine", "Ten", "Jack", "Queen", "King"};
        String[] cardSuits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        int[] cardValues = {11, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10};

        String cardName = "";
        String cardSuit = "";
        int cardValue = 0;

        // INITIALIZE DECK (52 CARDS)
        for (int i = 0; i < cardNames.length; i++){
            cardName = cardNames[i];
            cardValue = cardValues[i];
            for (int j = 0; j < cardSuits.length; j++){
                cardSuit = cardSuits[j];
                Card card = new Card(cardName, cardSuit, cardValue);
                deck.addCard(card);
            }
        }

        shuffleDeck(deck);
        shuffleDeck(deck);   
        return deck;
    }

    public void gameStart(Hand deck, Hand playerHand, Hand dealerHand){ 
        // CLEAR HAND OF CURRENT CARDS
        deck.removeHand();
        playerHand.removeHand();
        dealerHand.removeHand();

        deck = initializeDeck(deck);

        for (int i = 0; i < 2; i++){
            playHit(deck, dealerHand);
            playHit(deck, playerHand);
        }
        checkForAceOver21(playerHand);
        checkForAceOver21(dealerHand);
        playGame = check21(playerHand);
    }
    public static void main(String[] args) {
        blackJackFrame = new BlackJackGUI();
    }
}
