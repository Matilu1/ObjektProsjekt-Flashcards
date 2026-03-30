package flashcards;
//usikker på hvor viktig denne endte opp med å være, men bruker den på en eller annen måte og tør ikke slette den nå
public enum MasteryLevel {
    RED("Kan ikke"),
    YELLOW("Kan delvis"),
    GREEN("Kan");

    private final String label;

    MasteryLevel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static MasteryLevel fromScore(int score){
        if (score <= 1) {
            return RED;
        }
        if (score <= 3) {
            return YELLOW;
        }
        return GREEN;
    }
}
