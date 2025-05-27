package erdos.demo.controllers;

import erdos.demo.models.users.User;
import erdos.demo.models.words.Word;
import erdos.demo.services.WordService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainPage implements Initializable {
    @FXML
    public Button logOutButton;
    @FXML
    private Button startButton;
    @FXML
    private Button addButton;
    @FXML
    private Button changeButton;
    @FXML
    private Text progress;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        User user = User.getCurrentUser();
        WordService<Word> wordService = new WordService<>();
        try {
            progress.setText(user.getSizeOfProgress()*100/wordService.getSizeOfWords() + "%");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void startButtonPressed(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/erdos/demo/learn-page.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void addButtonPressed(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/erdos/demo/add-cards-page.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void changeButtonPressed(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/erdos/demo/edit-cards-page.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void logOutPressed(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/erdos/demo/login-page.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}