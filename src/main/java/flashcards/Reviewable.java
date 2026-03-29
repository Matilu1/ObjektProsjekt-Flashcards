package flashcards;

public interface Reviewable {
    void markKnown();
    void markUnknown();
    int getScore();
    MasteryLevel getMasteryLevel();
}