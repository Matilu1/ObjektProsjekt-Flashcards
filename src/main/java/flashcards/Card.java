package flashcards;

public class Card implements Reviewable {
    private String spørsmål;
    private String svar;
    private int score;

    public Card(String spørsmål, String svar){
        if (spørsmål == null || spørsmål.isBlank() || svar == null || svar.isBlank()){
            throw new IllegalArgumentException("Du må fylle inn både spørsmål og svar");
        }
        this.spørsmål = spørsmål;
        this.svar = svar;
        this.score = 0;
    }

    public String getSpørsmål(){
        return spørsmål;
    }

    public String getSvar(){
        return svar;
    }
//score system
    @Override
    public void markKnown(){
        score++;
    }   
    @Override
    public void markUnknown() {
        score = Math.max(0, score - 1);
    }

    public void setScore(int score){
        this.score = Math.max(0, score);
    }

    public int getScore() {
        return score;
    }
    @Override
    public MasteryLevel getMasteryLevel() {
        if (score <= 1){
            return MasteryLevel.RED;
        }
        if (score <= 3){
            return MasteryLevel.YELLOW;
        }
        return MasteryLevel.GREEN;
    }

}
