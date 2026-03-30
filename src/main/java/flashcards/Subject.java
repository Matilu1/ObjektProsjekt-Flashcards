package flashcards;
//logikk klasse
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Subject {
    //private
    private String fag;
    private ArrayList<Card> cardDeck;

    //public
    //kontruktør
    public Subject(String subject){
        if (subject == null || subject.isBlank()|| subject.contains("|")){
            throw new IllegalArgumentException("Faget må ha et navn og kan ikke inneholde |");
        }
        this.fag = subject;
        cardDeck = new ArrayList<>();
    }
    //fjerne kort fra arraylisten som er bunken
    public boolean removeCard(Card card) {
        if (card == null) {
            throw new IllegalArgumentException("Kan ikke fjerne et tomt kort");
        }
        if (!cardDeck.contains(card)) {
            throw new IllegalArgumentException("Kortet finnes ikke i kortstokken for faget: " + fag);
        }
        return cardDeck.remove(card);
    }

    //legge til nye kort
    public Card addCard(Card card) {
        if (card == null) {
            throw new IllegalArgumentException("Kan ikke legge til et null-kort");
        }
        if (cardDeck.contains(card)) {
            throw new IllegalArgumentException("Kortet finnes allerede i kortstokken for faget: " + fag);
        }
        cardDeck.add(card);
        return card;
    }

    
    public int getCardCount() {
        return cardDeck.size();
    }

    public String getFag() {
        return fag;
    }

    //ønsket ikke at de skulle kunne endres, derfor unmod
    public List<Card> getCardDeck() {
        return Collections.unmodifiableList(cardDeck);
    }



}

