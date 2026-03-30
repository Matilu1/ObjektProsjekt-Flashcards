package flashcards;
//fil klassen
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    //alt vil lagres i datafolder (inkludert i repoet)
    private static final String DATA_FOLDER= "data/";
    //lagrer det som tekstfiler, slik at de enkelt kan bli endret på både fra inne i koden og eksternt.
    public static void saveSubject(Subject subject){
        String filnavn = DATA_FOLDER + subject.getFag() + ".txt";

        //skriveren, deler opp i tre deler per linje, en linje per kort, spørsmål|svar|score
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filnavn))){
            for (Card card : subject.getCardDeck()){
                writer.write(card.getSpørsmål() + "|" + card.getSvar() + "|" + card.getScore());
                writer.newLine();
            }
        }
        catch(IOException e) {
            throw new RuntimeException("Kunne ikke lagre faget" + subject.getFag(), e);

        }
    }
    //henter faget som ønskes, senere trykkes på
    public static Subject loadSubject(String fagnavn){
        String filnavn = DATA_FOLDER + fagnavn + ".txt";
        Subject subject = new Subject(fagnavn);
        //igjen en linje per kort
        try (BufferedReader reader = new BufferedReader(new FileReader(filnavn))){
            String linje;
            //splitter hver string på linje |, derfor ikke mulig å inkludere | i spørsmål eller svar
            while ((linje = reader.readLine()) != null){
                String[] deler = linje.split("\\|");
                String spørsmål = deler[0];
                String svar = deler[1];
                int score = Integer.parseInt(deler[2]);

                Card card = new Card(spørsmål, svar); 
                card.setScore(score);
                subject.addCard(card);
            }
        }
        catch(IOException e) {
            throw new RuntimeException("Kunne ikke finne faget" + subject.getFag(), e);

        }
        return subject;
    }

    //henter alle fagene på en gang
    public static List <Subject> loadAllSubjects(){
        List<Subject> subjects = new ArrayList<>();
        File mappe = new File(DATA_FOLDER);

        //feilhåndtering
        if (!mappe.exists()) {
            mappe.mkdir();
            return subjects;
        }

        //passer på at vi henter ut alle lagrede fag hver gang, ingenting skal end med txt når det er navnet og ikke filen lenger
        for (File fil : mappe.listFiles()){
            if (fil.getName().endsWith(".txt")){
                String fagNavn = fil.getName().replace(".txt", "");
                subjects.add(loadSubject(fagNavn));
            }
        }
        return subjects;
    }

    //sletter fagfiler ved hjelp av knappen i kontrolløren
    public static void deleteSubject(String fagNavn){
        File fil = new File(DATA_FOLDER + fagNavn + ".txt");
        if (!fil.exists()){
            throw new IllegalArgumentException("Finner ikke faget du ønsker å slette: " + fagNavn);
        }
        fil.delete();
    }
}
