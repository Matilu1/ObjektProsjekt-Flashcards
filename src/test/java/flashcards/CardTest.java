package flashcards;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class CardTest {
    @Test
    public void testConstructor(){
        Card card = new Card("Funker dette spørsmålet?", "Funker dette svaret?");
        assertEquals("Funker dette spørsmålet?", card.getSpørsmål());
        assertEquals("Funker dette svaret?", card.getSvar());

    }

    @Test
    public void testConstructorThrows(){
        assertThrows(IllegalArgumentException.class, () -> {new Card("", "Hei");}
    );
            
    }

    @Test
    public void testGetScore(){
        Card card = new Card("Funker dette spørsmålet?", "Funker dette svaret?");
        assertEquals(0, card.getScore());
        card.setScore(3);
        assertEquals(3, card.getScore());
    }

    @Test
    public void testMarkKnown(){
        Card card = new Card("Funker dette spørsmålet?", "Funker dette svaret?");
        card.markKnown();
        assertEquals(1, card.getScore());
    }

    @Test 
    public void testMarkUnknown(){
        Card card = new Card("Funker dette spørsmålet?", "Funker dette svaret?");
        card.setScore(5);
        card.markUnknown();
        assertEquals(4, card.getScore());

        card.setScore(0);
        card.markUnknown();
        assertEquals(0, card.getScore());

    }

    
}
