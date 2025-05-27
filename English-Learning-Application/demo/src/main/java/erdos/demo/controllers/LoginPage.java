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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class LoginPage {
    @FXML
    public TextField usernameField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public Button loginButton;
    @FXML
    public Button signUpButton;
    @FXML
    public Text warningMessage;

    @FXML
    public void loginPressed(ActionEvent actionEvent) throws IOException {
        if(usernameField.getText().equals("") || passwordField.getText().equals("")) {
            warningMessage.setText("Please enter a valid username or password");
        }
        else{
            UserService service = new UserService();
            Set<User> users = service.readUsers();
            boolean passwordOrUsernameWrong = false;

            if(users.isEmpty()) {
                warningMessage.setText("Register at first");
            }
            else {
                warningMessage.setText("");
                for (User user : users) {
                    if(user.getUsername().equals(usernameField.getText()) && user.getPassword().equals(passwordField.getText())) {

                        User.setCurrentUser(user);
                        passwordOrUsernameWrong = true;

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/erdos/demo/main-page.fxml"));
                        Parent root = loader.load();
                        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        stage.setScene(new Scene(root));
                        stage.show();
                    }
                }
                if(!passwordOrUsernameWrong) {
                    warningMessage.setText("User or password incorrect");
                }
            }
        }
    }

    @FXML
    public void signupPressed(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/erdos/demo/registration-page.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
