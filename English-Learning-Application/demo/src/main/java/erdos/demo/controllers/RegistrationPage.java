package erdos.demo.controllers;

import erdos.demo.models.users.User;
import erdos.demo.services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class RegistrationPage {
    @FXML
    public TextField usernameField;
    @FXML
    public Text warningMessage;
    @FXML
    public TextField passwordField;
    @FXML
    public Text successfullyRegistered;

    @FXML
    public void signupPressed(ActionEvent actionEvent) throws IOException {
        if(usernameField.getText().equals("") || passwordField.getText().equals("")) {
            successfullyRegistered.setText("");
            warningMessage.setText("Please fill all the fields");
        }
        else {
            warningMessage.setText("");
            User user = new User(usernameField.getText(), passwordField.getText());
            UserService service = new UserService();
            service.saveUser(user);
            successfullyRegistered.setText("Successfully registered");
        }
    }

    @FXML
    public void goBackPressed(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/erdos/demo/login-page.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
