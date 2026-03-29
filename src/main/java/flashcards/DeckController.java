package flashcards;
import java.io.IOException;
import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Dialog;
import javafx.scene.control.ButtonType;

public class DeckController {
    private Subject subject;
    private List<Card> deck;
    private int currentIndex;
    private boolean showingFront;
    @FXML private Label cardlabel;
    @FXML private Button canButton;
    @FXML private Button cantButton;
    @FXML private Button flipButton;
    @FXML private Label scoreLabel;


    public void setSubject(Subject subject){
        this.subject = subject;
        this.deck = subject.getCardDeck();
        this.currentIndex = 0;
        this.showingFront = true;
        if( deck.isEmpty()){
            cardlabel.setText("Bunken er tom, start med å lage nye kort for bunken.");
            canButton.setDisable(true);
            cantButton.setDisable(true);
            flipButton.setDisable(true);
        } else{cardlabel.setText(deck.get(0).getSpørsmål());
            updateCardColor();
        }
        
    }


    @FXML
    public void handleNewCard(){
        Dialog <Card> dialog = new Dialog<>();
        dialog.setTitle("Nytt kort");
    
        TextField spørsmålFelt = new TextField();
        spørsmålFelt.setPromptText("Spørsmål:");
        TextField svarFelt = new TextField();
        svarFelt.setPromptText("Svar:");

        VBox innhold = new VBox(10, spørsmålFelt, svarFelt);
        dialog.getDialogPane().setContent(innhold);

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(knapp -> {
            if (knapp == ButtonType.OK) {
                String spørsmål = spørsmålFelt.getText().trim();
                String svar = svarFelt.getText().trim();

                if (!svar.isBlank() && !spørsmål.isBlank()){
                    return new Card(spørsmål, svar);
                }
            }
            return null;
        })
;

        dialog.showAndWait().ifPresent(card -> {
        subject.addCard(card);
        FileHandler.saveSubject(subject);
        if (deck.size() == 1) {
            canButton.setDisable(false);
            cantButton.setDisable(false);
            flipButton.setDisable(false);
        }
        cardlabel.setText(deck.get(currentIndex).getSpørsmål());
        updateCardColor();
    });
       
    }

    @FXML   
    public void handleCan(){
        Card currentCard = deck.get(currentIndex);
        currentCard.markKnown();
        FileHandler.saveSubject(subject);
        nextCard();
    }
    @FXML
    public void handleCant(){
        Card currentCard = deck.get(currentIndex);
        currentCard.markUnknown();
        FileHandler.saveSubject(subject);
        nextCard();
    }

    @FXML
    public void handleFlip(){
        if (showingFront){
            showingFront = false;
            cardlabel.setText(deck.get(currentIndex).getSvar());
        }
        else{
            showingFront = true;
            cardlabel.setText(deck.get(currentIndex).getSpørsmål());
        }

    }

    public void nextCard(){
        currentIndex = (currentIndex + 1) % deck.size();
        showingFront = true;
        cardlabel.setText(deck.get(currentIndex).getSpørsmål());
        updateCardColor();
        }

    @FXML 
    public void handleBack(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Lag1.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) cardlabel.getScene().getWindow();
            stage.setScene(new Scene(root));
        }catch(IOException e){
            throw new RuntimeException("greide ikke gå tilbake", e);
        }
    }

    private void updateCardColor() {
    MasteryLevel level = deck.get(currentIndex).getMasteryLevel();
    switch (level) {
        case RED    -> scoreLabel.setStyle("-fx-background-color: #ff6b6b;");
        case YELLOW -> scoreLabel.setStyle("-fx-background-color: #ffd93d;");
        case GREEN  -> scoreLabel.setStyle("-fx-background-color: #6bcb77;");
    }
}
}
