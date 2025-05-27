package erdos.demo.controllers;

import erdos.demo.models.words.*;
import erdos.demo.services.WordService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class AddCardsPage {
    @FXML
    public Button goBackButton;
    @FXML
    public TextField englishField;
    @FXML
    public TextField russianField;
    @FXML
    public Button saveButton;
    @FXML
    public ComboBox types;
    @FXML
    public Text nonSavedMessage;
    @FXML
    public Text savedMessage;
    WordService<Word> wordService = new WordService<>();

    @FXML
    public void goBackPressed(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/erdos/demo/main-page.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void saveButtonPressed(ActionEvent actionEvent) throws IOException {
        emptyTexts();
        if (!englishField.getText().isEmpty() && !russianField.getText().isEmpty() && types.getSelectionModel().getSelectedItem() != null) {
            savedMessage.setText("Successfully saved!");

            String english = englishField.getText();
            String russian = russianField.getText();

            if(Objects.equals(types.getSelectionModel().getSelectedItem().toString(), "Adjective")) {
                Adjective adjective = new Adjective(english, russian);
                wordService.saveWord(adjective);
            }
            else if(Objects.equals(types.getSelectionModel().getSelectedItem().toString(), "Verb")){
                Verb verb = new Verb(english, russian);
                wordService.saveWord(verb);
            }
            else if(Objects.equals(types.getSelectionModel().getSelectedItem().toString(), "Noun")){
                Noun noun = new Noun(english, russian);
                wordService.saveWord(noun);
            }
            else if(Objects.equals(types.getSelectionModel().getSelectedItem().toString(), "Adverb")){
                Adverb adverb = new Adverb(english, russian);
                wordService.saveWord(adverb);
            }
        }
        else{
            emptyTexts();
            nonSavedMessage.setText("Please fill all fields");
        }
    }

    private void emptyTexts(){
        savedMessage.setText("");
        nonSavedMessage.setText("");
    }
}
