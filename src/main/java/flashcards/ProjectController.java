package flashcards;
//kontrolløren til lag 1
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;

public class ProjectController {
    //private 
    @FXML
    private VBox subjectList;

    private List<Subject> subjects;
    @FXML 
    private Button addSubjectButton;

    @FXML
    private TextField subjectNameField;

    @FXML
    private Label error;

    //public
    @FXML
    public void initialize(){
        subjects = FileHandler.loadAllSubjects();
        showAllSubjects();
        error.setVisible(false);

    }
    //lager de nye fagene, setter opp nye knapper for hvert fag
    public void showAllSubjects(){
        subjectList.getChildren().clear();;
        for (Subject subject : subjects){
            HBox rad = new HBox(0
            );

            Button fagknapp = new Button(subject.getFag());
            Button slettknapp = new Button("Slett");

            fagknapp.setPrefWidth(400.0);
            HBox.setHgrow(fagknapp, javafx.scene.layout.Priority.ALWAYS);

            fagknapp.setOnAction(e -> openSubject(subject));
            slettknapp.setOnAction(e -> deleteSubject(subject));

            rad.getChildren().addAll(fagknapp, slettknapp);
            subjectList.getChildren().add(rad);


        }
    }
    //fjerner faget
    public void deleteSubject(Subject subject){
         subjects.remove(subject);
        FileHandler.deleteSubject(subject.getFag());
        showAllSubjects();
    }
    //legger til faget via knappene mine
    @FXML
    private void addSubject(){
        String fagnavn = subjectNameField.getText().trim();
        //sjekker ting, krav: kan ikke være tom og kan ikke være noe som allerede eksisterer
        boolean finnesAllerede = subjects.stream()
        .anyMatch(s -> s.getFag().equalsIgnoreCase(fagnavn));
        if (fagnavn.isBlank() || finnesAllerede) {
            //subjectNameField.clear(); //burde denne cleare eller ikke? folk er uenige
            error.setVisible(true);
            return;
        }
            
        //Lager det nye fagnavnet som et subject i lista og clearer ut
        Subject subject = new Subject(fagnavn);
        error.setVisible(false);
        subjects.add(subject);
        FileHandler.saveSubject(subject);
        subjectNameField.clear();
        showAllSubjects();
    }
    //hopper til neste lag, skjer hvis man trykker på knappen med fagnavnet på
    public void openSubject(Subject subject){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Lag2.fxml"));
            Parent root = loader.load();

            DeckController deckController = loader.getController();
            deckController.setSubject(subject);

            Stage stage = (Stage) subjectList.getScene().getWindow();
            stage.setScene(new Scene(root));
        }catch(IOException e){
            throw new RuntimeException("Kunne ikke åpne faget" + subject.getFag(), e);
        }
    }
}

