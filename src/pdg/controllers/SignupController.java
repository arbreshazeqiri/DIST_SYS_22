package pdg.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pdg.components.ErrorPopupComponent;
import pdg.models.User;
import pdg.utils.SessionManager;

import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ResourceBundle;

import static javax.swing.JOptionPane.showMessageDialog;

public class SignupController extends ChildController {
    public final String SIGN_UP_VIEW = "signup";
    public final String LOG_IN_VIEW2 = "login";

    private static final String VIEW_PATH = "../views";
    private static String url = "http://localhost:3000/v1/auth/register";


    @FXML
    private Label suUser;
    @FXML
    private Label suFull;
    @FXML
    private Label suEmail_;
    @FXML
    private Label suPass;
    @FXML
    private Label suConPass;
    @FXML
    private Label suCount;
    @FXML
    private Button cancelButton;
    @FXML
    private Button RegisterId;
    @FXML
    private VBox contentPane2;

    @FXML
    private ComboBox choiceBox;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField fullnameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Label registerMessageLabel;
    @FXML
    private Label registerMessageLabel1;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> list = FXCollections.observableArrayList();
        list.addAll("Albania", "Kosovo");
        choiceBox.setItems(list);
        super.initialize(url, rb);
    }

    @FXML
    private void onRegisterButtonClick(ActionEvent event) {
        try {
            if (!usernameField.getText().isBlank() && !fullnameField.getText().isBlank() && !emailField.getText().isBlank() && !passwordField.getText().isBlank() && !confirmPasswordField.getText().isBlank() && !choiceBox.getSelectionModel().isEmpty()) {
                if (passwordField.getText().equals(confirmPasswordField.getText())) {
                    if (passwordField.getLength() > 7) {
                        if(passwordField.getText().matches("^(?=.*[0-9])(?=.*[a-zA-Z])[a-zA-Z0-9]+$")){
                        registerMessageLabel1.setText("");
                        emailValidation(event);
                        registerMessageLabel1.setText("");
                        }
                        else {
                            registerMessageLabel1.setText("Password must contain at least one number and one letter.");
                        }
                    } else {
                        registerMessageLabel1.setText("Password must be at least 8 characters.");
                    }
                } else {
                    registerMessageLabel1.setText("Passwords do not match.");
                }
            } else {
                registerMessageLabel.setText("All fields must be filled.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void emailValidation(ActionEvent eventi) throws Exception {
        try {
            User user = new User(usernameField.getText(), fullnameField.getText(), emailField.getText().toLowerCase(), passwordField.getText(), choiceBox.getValue().toString());
            switch (insertUser(user)) {
                case 409:
                    registerMessageLabel.setText("Email is already taken.");
                    emailField.requestFocus();
                    break;
                case 410:
                    registerMessageLabel.setText("Username is already taken.");
                    usernameField.requestFocus();
                    break;
                case 400:
                    registerMessageLabel.setText("Email pattern is incorrect.");
                    emailField.requestFocus();
                    break;
                case 429:
                    registerMessageLabel.setText("You've sent too many requests. Try again later.");
                    break;
                case 201:
                default:
                    SessionManager.user = user;
                    showMessageDialog(null, "Registration: successful. Login with your new account!");
                    Parent root = FXMLLoader.load(getClass().getResource(viewPath2("login")));
                    Scene scene = new Scene(root);

                    Stage primaryStage = (Stage) ((Node) eventi.getSource()).getScene().getWindow();
                    primaryStage.setScene(scene);
                    primaryStage.show();
                    break;
            }
        } catch (Exception e) {
            ErrorPopupComponent.show(e.toString());
        }
    }

    public static Integer insertUser(User user) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            String input = "{ \"username\":\"" + user.getUsername() + "\", \"fullname\":\"" + user.getFullName()
                    + "\", \"email\":\"" + user.getEmail() + "\", \"password\":\"" + user.getPassword() +
                    "\", \"country\":\"" + user.getCountry() + "\" }";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:3000/v1/auth/register"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(input))
                    .build();


            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
            return response.statusCode();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @FXML
    private void onCancelButtonClick(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(viewPath2("login")));
            Scene scene = new Scene(root);

            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setView2(String view) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(viewPath2(view)));
        Pane pane = null;
        switch (view) {
            case LOG_IN_VIEW2:
                pane = loader.load();
                contentPane2.setAlignment(Pos.CENTER);
                break;
            case SIGN_UP_VIEW:
                pane = loader.load();
                contentPane2.setAlignment(Pos.TOP_LEFT);
                break;
            default:
                throw new Exception("ERR_VIEW_NOT_FOUND");
        }

        contentPane2.getChildren().clear();
        contentPane2.getChildren().add(pane);
        VBox.setVgrow(pane, Priority.ALWAYS);
    }

    private String viewPath2(String view) {
        return VIEW_PATH + "/" + view + ".fxml";
    }

    private ChildController childController = null;

    @Override
    public void loadLangTexts(ResourceBundle langBundle) {
        String suUsername = langBundle.getString("su_username");
        String suFullName = langBundle.getString("su_full_name");
        String suEmail = langBundle.getString("su_email");
        String suPassword = langBundle.getString("su_password");
        String suConfirmPassword = langBundle.getString("su_confirm_password");
        String suCountry = langBundle.getString("su_country");
        String suCancelButton = langBundle.getString("su_cancel_button");
        String suRegisterButton = langBundle.getString("su_register_button");
        try {
            suUser.setText(suUsername);
            suFull.setText(suFullName);
            suEmail_.setText(suEmail);
            suPass.setText(suPassword);
            suConPass.setText(suConfirmPassword);
            suCount.setText(suCountry);
            cancelButton.setText(suCancelButton);
            RegisterId.setText(suRegisterButton);
            choiceBox.setPromptText(suCountry);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (this.childController != null) {
            this.childController.loadLangTexts(langBundle);
        }
    }
}

