package flashcards;
//interfacet, burde kunne gjøre noe mer med det, men har fått beskjed om at det er godkjent bruk
public interface Reviewable {
    void markKnown();
    void markUnknown();
    int getScore();
    MasteryLevel getMasteryLevel();
}