package aydin.firebasedemo;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.IOException;

public class WelcomeController {
    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    void onRegisterClicked(ActionEvent event) {
        registerUser();
    }

    @FXML
    void onSignInClicked(ActionEvent event) {
        signInUser();
    }

    private void registerUser() {
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(emailField.getText())
                .setPassword(passwordField.getText())
                .setEmailVerified(false)
                .setDisabled(false);

        try {
            UserRecord userRecord = DemoApp.fauth.createUser(request);
            showAlert("Success", "User registered successfully: " + userRecord.getUid());
        } catch (FirebaseAuthException e) {
            showAlert("Error", "Failed to register: " + e.getMessage());
        }
    }

    private void signInUser() {
        try {
            DemoApp.fauth.getUserByEmail(emailField.getText());
            // If user exists, navigate to Primary View
            DemoApp.setRoot("primary");
        } catch (FirebaseAuthException | IOException e) {
            showAlert("Error", "Invalid email or password.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
