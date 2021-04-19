import java.util.Random;
import java.util.Scanner;

public class Main {

    // HIT
    public static void playHit(Hand deck, Hand hand){
        // TAKE A CARD + REMOVE THAT CARD FROM TOP OF DECK
        Card card = deck.getCard(0);
        hand.addCard(card);
        deck.removeCard(card);
    }


    // COMPARES PLAYER AND DEALER HAND
    public static void playBlackJack(Hand playerHand, Hand dealerHand){
        //TO DO: DONT SHOW DEALER HAND IF YOU LOSE AND DEALER IS OVER 21
        if (playerHand.getPoints() < 21){
            dealerHand.printAll();
            playerHand.printAll();

            if (playerHand.getPoints() > dealerHand.getPoints() || dealerHand.getPoints() > 21){
                System.out.println("You win.");
                System.out.println("");
            }
            else if (playerHand.getPoints() == dealerHand.getPoints()){
                System.out.println("Draw.");
                System.out.println("");
            }
            else{
                System.out.println("You lose.");
                System.out.println("");
            }
        }
        else if (playerHand.getPoints() == 21 && dealerHand.getPoints() != 21){
            playerHand.printAll();
            System.out.println("BLACKJACK. You win.");
            System.out.println("");
        }
        else if (playerHand.getPoints() == 21 && dealerHand.getPoints() == 21){
            dealerHand.printAll();
            playerHand.printAll();
            System.out.println("Draw.");
            System.out.println("");
        }
        else{
            playerHand.printAll();
            System.out.println("You lose.");
            System.out.println("");
        }
    }

    // CHECKS IF HAND IS 21 OR OVER, IF SO PREVENTS ANY MORE INPUT
    public static boolean check21(Hand playerHand){
        return playerHand.getPoints() >= 21;
    }

    // INTRODUCTION PROMPT
    public static void introduction(Scanner scnr){
        boolean introDone = false;
        String userInput = "";
        System.out.println("Welcome to Heimdallr BlackJack!");
        System.out.println("");
        while (!introDone){
            System.out.println("Are you ready to play?");
            System.out.println("1: Yes");
            System.out.println("2: No");
            System.out.println("");
            userInput = scnr.nextLine();
            if (userInput.equals("1")){
                introDone = true;
            }
            else if (userInput.equals("2")){
                System.out.println("Goodbye.");
                System.out.println("");
                return;
            }
            else{
                System.out.println("INVALID");
                continue;
            }
        }
        

    }

    public static void printChoices(){
        System.out.println("1: Hit");
        System.out.println("2: Stand");
        System.out.println("");
    }

    public static void playAgain(){
        System.out.println("Play again?");
        System.out.println("1: Yes");
        System.out.println("2: No");
        System.out.println("");
    }

    // METHOD FOR SHUFFLING DECK
    public static void shuffleDeck(Hand deck){
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
    public static void checkForAceOver21(Hand playerHand){
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
    public static void dealerPlay(Hand deck, Hand dealerHand){
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

    // GAMEPLAY
    public static void playBlackJackGame(Scanner scnr, Hand deck, Hand playerHand, Hand dealerHand){
        boolean playGame = false;
        String userInput = "";
        gameStart(deck, playerHand, dealerHand);
        while (!playGame){
            
            playerHand.printAll();
            printChoices();
            playGame = check21(playerHand);
            
            // CHECKS IF FIRST 2 CARDS = 21 (BETTER SOLUTION NEEDED?)
            if (playGame){
                break;
            }

            userInput = scnr.nextLine();

            if (userInput.equals("1")){
                playHit(deck, playerHand);
                checkForAceOver21(playerHand);
                playGame = check21(playerHand);
                
            }

            else if (userInput.equals("2")){
                playGame = true;
            }

            else{

            }
            dealerPlay(deck, dealerHand);
        }
        playBlackJack(playerHand, dealerHand);
    }

    public static Hand initializeDeck(Hand deck){
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

    public static void gameStart(Hand deck, Hand playerHand, Hand dealerHand){ 
        // REMOVE CURRENT HAND
        deck.removeHand();
        playerHand.removeHand();
        dealerHand.removeHand();

        // RESHUFFLE DECK
        deck = initializeDeck(deck);

        for (int i = 0; i < 2; i++){
            playHit(deck, dealerHand);
            playHit(deck, playerHand);
        }
        checkForAceOver21(playerHand);
        checkForAceOver21(dealerHand);
    }

    public static void replayGame(Scanner scnr, Hand deck, Hand playerHand, Hand dealerHand){
        boolean stopPlaying = false;
        String userInput = "";
        while (!stopPlaying){
            playAgain();
            userInput = scnr.nextLine();
            if (userInput.equals("1")){
                gameStart(deck, playerHand, dealerHand);
                playBlackJackGame(scnr, deck, playerHand, dealerHand);
                
            }
            else if (userInput.equals("2")){
                stopPlaying = true;
                System.out.println("Goodbye.");
                System.out.println("");
            }
            else{
                continue;
            }
        }
    }

    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);
        
        // INITIALIZE DECK OBJECT
        Hand deck = new Hand("Deck");

        // INITIALIZE PLAYER AND DEALER HAND
        Hand dealerHand = new Hand("Dealer");
        Hand playerHand = new Hand("Player");

        // INTRODUCTION
        introduction(scnr);

        // PLAY GAME
        playBlackJackGame(scnr, deck, playerHand, dealerHand);
        
        // REPLAY GAME
        replayGame(scnr, deck, playerHand, dealerHand);

        // TO DO: IF PLAYER OVER 21, DON'T SHOW DEALER POINTS //
    }

}
