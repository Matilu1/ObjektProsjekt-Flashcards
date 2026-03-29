package flashcards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Subject {
    private String fag;
    private ArrayList<Card> cardDeck;

    public Subject(String subject){
        if (subject == null || subject.isBlank()){
            throw new IllegalArgumentException("Faget må ha et navn");
        }
        this.fag = subject;
        cardDeck = new ArrayList<>();
    }

    public boolean removeCard(Card card) {
        if (card == null) {
            throw new IllegalArgumentException("Kan ikke fjerne et tomt kort");
        }
        if (!cardDeck.contains(card)) {
            throw new IllegalArgumentException("Kortet finnes ikke i kortstokken for faget: " + fag);
        }
        return cardDeck.remove(card);
    }

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

    public List<Card> getCardDeck() {
        return Collections.unmodifiableList(cardDeck);
    }



}

