package erdos.demo.controllers;

import erdos.demo.models.users.User;
import erdos.demo.models.words.Word;
import erdos.demo.services.WordService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class EditCardsPage implements Initializable {
    @FXML
    public Button goBackButton;
    @FXML
    public VBox mainPane;

    WordService wordService = new WordService();

    @FXML
    public void goBackPressed(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/erdos/demo/main-page.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        LinkedList<Word> words = new LinkedList<>();
        try {
            words = wordService.readWords();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (Word word : words) {
            addWordCard(word, words);
            mainPane.setSpacing(5);
        }
    }

    private void addWordCard(Word word, LinkedList<Word> words) {
        // Карточка слова — горизонтальный контейнер
        HBox card = new HBox(10);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setPadding(new Insets(15));
        card.setSpacing(10);
        card.setStyle("""
        -fx-background-color: linear-gradient(to right, #ffffff, #f9f9f9);
        -fx-background-radius: 12;
        -fx-border-color: #dddddd;
        -fx-border-radius: 12;
        -fx-border-width: 1;
        -fx-effect: dropshadow(one-pass-box, rgba(0,0,0,0.08), 4, 0, 2, 2);
    """);

        // Стили текста
        String labelStyle = "-fx-font-size: 16; -fx-text-fill: #333333;";
        String separatorStyle = "-fx-text-fill: #bbbbbb; -fx-font-size: 16;";

        // Элементы текста
        Label englishLabel = new Label(word.getEnglish());
        englishLabel.setStyle("-fx-font-weight: bold; " + labelStyle);

        Label sep1 = new Label("|");
        sep1.setStyle(separatorStyle);

        Label russianLabel = new Label(word.getRussian());
        russianLabel.setStyle(labelStyle);

        Label sep2 = new Label("|");
        sep2.setStyle(separatorStyle);

        Label typeLabel = new Label(word.getClass().getSimpleName());
        typeLabel.setStyle("-fx-font-size: 14; -fx-text-fill: #777777;");

        // Кнопка удаления
        Button deleteButton = new Button("✕");
        deleteButton.setTooltip(new Tooltip("Удалить слово"));
        deleteButton.setCursor(Cursor.HAND);
        deleteButton.setStyle("""
        -fx-background-color: transparent;
        -fx-text-fill: #ff5555;
        -fx-font-size: 18;
    """);
        deleteButton.setOnMouseEntered(e -> deleteButton.setStyle("-fx-text-fill: #cc0000; -fx-background-color: transparent; -fx-font-size: 18;"));
        deleteButton.setOnMouseExited(e -> deleteButton.setStyle("-fx-text-fill: #ff5555; -fx-background-color: transparent; -fx-font-size: 18;"));
        deleteButton.setOnAction(e -> {
            words.remove(word);
            User.getCurrentUser().minusProgress(word);
            wordService.saveList(words);
            mainPane.getChildren().remove(card);
        });

        // Spacer для выравнивания
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Добавляем всё в карточку
        card.getChildren().addAll(englishLabel, sep1, russianLabel, sep2, typeLabel, spacer, deleteButton);

        // Добавляем карточку на главный контейнер
        mainPane.getChildren().add(card);
    }

}