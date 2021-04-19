public class Card {
    private String name;    // Ace, One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King
    private String suit;    // Hearts, Diamonds, Clubs, Spades
    private int value;  // 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11

    public Card(){
        name = "None";
        suit = "None";
        value = -1;
    }

    public Card (String name, String suit, int value){
        this.name = name;
        this.suit = suit;
        this.value = value;
    }

    // SETTERS
    public void setName(String name){
        this.name = name;
    }

    public void setSuit(String suit){
        this.suit = suit;
    }

    public void setValue(int value){
        this.value = value;
    }

    // GETTERS
    public String getName(){
        return name;
    }

    public String getSuit(){
        return suit;
    }

    public int getValue(){
        return value;
    }
    
    @Override
    public String toString(){
        return name + " of " + suit + " (" + getValue() + ")";
    }

}
