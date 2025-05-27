package erdos.demo.controllers;

import erdos.demo.models.users.User;
import erdos.demo.models.words.Word;
import erdos.demo.services.UserService;
import erdos.demo.services.WordService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.Stack;

public class LearnPage implements Initializable {
    @FXML
    public Label englishWord;
    @FXML
    public Label russianWord;
    @FXML
    public Button previousButton;
    @FXML
    public Button idkButton;
    @FXML
    public Button nextButton;
    @FXML
    public Button knowButton;
    @FXML
    public Button goBackButton;
    @FXML
    public Text mainText;
    @FXML
    public Text warningText;
    @FXML
    public Label type;

    Stack<Word> history = new Stack<>();
    WordService<Word> wordService = new WordService<>();
    Queue<Word> queue = wordService.readWords();
    private Word currentWord;
    private User currentUser = User.getCurrentUser();
    private UserService userService = new UserService();
    private int currentHistoryIndex = -1;
    public LearnPage() throws IOException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(currentUser.getSizeOfProgress());
        try {
            queue = wordService.readWords();
            showNextWord();
        } catch (IOException e) {
            mainText.setText("Error loading words");
        }
    }

    private void showNextWord() {
        // Если мы идём по истории
        if (currentHistoryIndex != -1 && currentHistoryIndex < history.size() - 1) {
            currentHistoryIndex++;
            currentWord = history.get(currentHistoryIndex);
            updateDisplay(currentWord);
        }
        // Если история закончилась — берём из очереди
        else if (!queue.isEmpty()) {
            currentWord = queue.poll();
            history.push(currentWord);
            updateDisplay(currentWord);
            currentHistoryIndex = -1; // выходим из режима истории
        } else {
            mainText.setText("Finish");
        }
    }

    private void updateDisplay(Word word) {
        englishWord.setText(word.getEnglish());
        russianWord.setText("********");
        type.setText(word.getClass().getSimpleName());
    }

    @FXML
    public void previousButtonPressed(ActionEvent actionEvent) {
        if (history.size() > 1) {
            if (currentHistoryIndex == -1) {
                currentHistoryIndex = history.size() - 2; // текущий был последний, отступаем на 1
            } else if (currentHistoryIndex > 0) {
                currentHistoryIndex--;
            } else {
                warningText.setText("You're at the start.");
                return;
            }

            currentWord = history.get(currentHistoryIndex);
            updateDisplay(currentWord);
            warningText.setText("");
        } else {
            warningText.setText("You're at the start.");
        }
    }

    @FXML
    public void nextButtonPressed(ActionEvent actionEvent) {
        if (russianWord.getText().equals("********")) {
            warningText.setText("Please choose \"I know\" or \"I don't know\"");
        } else {
            showNextWord(); // здесь логика уже всё решает
            warningText.setText("");
        }
    }


    @FXML
    public void knowButtonPressed(ActionEvent actionEvent) throws IOException {
        russianWord.setText(currentWord.getRussian());
        userService.saveUserProgress(currentUser, currentWord);
    }

    @FXML
    public void idkButtonPressed(ActionEvent actionEvent) {
        russianWord.setText(currentWord.getRussian());
    }

    @FXML
    public void goBackPressed(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/erdos/demo/main-page.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}