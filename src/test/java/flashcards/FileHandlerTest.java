package flashcards;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileHandlerTest {

    private static final String TEST_FOLDER = "test-data/";

    @BeforeEach
    public void setUp() {
        // trenger en testmappen før hver test
        new File(TEST_FOLDER).mkdir();
    }

    @AfterEach
    public void tearDown(){
        File mappe = new File(TEST_FOLDER);
        for (File fil : mappe.listFiles()) {
            fil.delete();
        }
        mappe.delete();
    }
    
    @Test
    public void testSaveAndLoad(){
        Subject subject = new Subject("testfag");
        subject.addCard(new Card("spørsmål","svar"));

        FileHandler.saveSubject(subject);

        Subject lastet = FileHandler.loadSubject("testfag");
        assertEquals("testfag", lastet.getFag());
        assertEquals(1, lastet.getCardCount());
    }
}
