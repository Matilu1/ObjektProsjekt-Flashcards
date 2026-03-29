package flashcards;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    private static final String DATA_FOLDER= "data/";

    public static void saveSubject(Subject subject){
        String filnavn = DATA_FOLDER + subject.getFag() + ".txt";


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

    public static Subject loadSubject(String fagnavn){
        String filnavn = DATA_FOLDER + fagnavn + ".txt";
        Subject subject = new Subject(fagnavn);

        try (BufferedReader reader = new BufferedReader(new FileReader(filnavn))){
            String linje;
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
            throw new RuntimeException("Kunne ikke lagre faget" + subject.getFag(), e);

        }
        return subject;
    }

    public static List <Subject> loadAllSubjects(){
        List<Subject> subjects = new ArrayList<>();
        File mappe = new File(DATA_FOLDER);

        if (!mappe.exists()) {
            mappe.mkdir();
            return subjects;
        }

        for (File fil : mappe.listFiles()){
            if (fil.getName().endsWith(".txt")){
                String fagNavn = fil.getName().replace(".txt", "");
                subjects.add(loadSubject(fagNavn));
            }
        }
        return subjects;
    }

    public static void deleteSubject(String fagNavn){
        File fil = new File(DATA_FOLDER + fagNavn + ".txt");
        if (!fil.exists()){
            throw new IllegalArgumentException("Finner ikke faget du ønsker å slette: " + fagNavn);
        }
        fil.delete();
    }
}
