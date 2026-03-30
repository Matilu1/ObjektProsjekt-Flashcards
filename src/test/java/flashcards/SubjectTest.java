package flashcards;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class SubjectTest {
    @Test
    public void testConstructor(){
        Subject subject = new Subject("testFag");
        assertEquals("testFag", subject.getFag());
    }

    @Test
    public void testConstructorThrows(){
        assertThrows(IllegalArgumentException.class, () -> new Subject(""));
    }

    @Test
    public void testAddAndRemoveCard(){
        Subject subject = new Subject("testFag");
        Card card = new Card("Spørsmål?", "Svar");
        subject.addCard(card);
        assertEquals(1, subject.getCardCount());
        subject.removeCard(card);
        assertEquals(0, subject.getCardCount());
        
        assertThrows(IllegalArgumentException.class,() -> subject.removeCard(card));
    }

}
