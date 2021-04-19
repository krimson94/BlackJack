import java.util.ArrayList;
import java.util.Arrays;

public class Hand {
    private String playerName;
    private int points = 0;
    ArrayList<Card> hand = new ArrayList<Card>();

    public Hand(){
        playerName = "Unknown";
    }

    public Hand(String playerName){
        this.playerName = playerName;
    }

    public void addCard(Card card){
        hand.add(card);
    }
    
    public void removeCard(Card card){
        hand.remove(card);
    }
    // Remove hand / RESET HAND / GAME FINISH
    public void removeHand(){
        hand.clear();
    }

    // CHANGES ACE VALUE 11 to ACE VALUE 1
    public void changeAceValue(int i){
        hand.get(i).setValue(1);
    }
    
    public int getPoints(){
        points = 0;
        for (int i = 0; i < hand.size(); i++){
            points += hand.get(i).getValue();
        }
        return points;
    }

    public Card getCard(int index){
        return hand.get(index);
    }

    // PRINT HAND IN FORMAT: CARD (VALUE)
    public void printHand(){
        System.out.println(playerName + "\'s hand:");
        for (int i = 0; i < hand.size(); i++){
            if (i == hand.size() - 1){
                System.out.printf("%s", hand.get(i));
            }
            else{
            System.out.printf("%s, ", hand.get(i));
            }
        }
        System.out.println("");
        System.out.println("");
    }

    // PRINT TOTAL POINTS
    public void printValue(){
        System.out.println("Points: " + getPoints());
        System.out.println("");
    }

    public void printAll(){
        printHand();
        printValue();
    }
    


}
