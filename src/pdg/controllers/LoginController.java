package pdg.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.json.JSONObject;
import pdg.components.ErrorPopupComponent;
import pdg.models.User;
import pdg.utils.SessionManager;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ResourceBundle;


public class LoginController extends BaseController {

    @FXML
    private Label loginMessageLabel;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    private static String url = "http://localhost:3000/v1/auth/login";

    @FXML
    private void onLoginButtonClick(ActionEvent event) {
        try {
            if (username.getText().isBlank() == false && password.getText().isBlank() == false) {
                validateLogin(event);
            } else {
                loginMessageLabel.setText("Username or Password is empty!");
            }
        } catch (Exception e) {
            ErrorPopupComponent.show(e.toString());
        }
    }

    public static Integer loginUser(String username, String password) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            String input = "{ \"username\":\"" + username + "\", \"password\":\"" + password + "\" }";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:3000/v1/auth/login"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(input))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void validateLogin(ActionEvent event) {
        try {
            if (loginUser(username.getText(), password.getText()) == 200) {
                User user = findByUsername(username.getText());
                FXMLLoader loader = new FXMLLoader();
                SessionManager.user = user;
                loader.setLocation(getClass().getResource("../views/main-screen.fxml"));
                Parent root = loader.load();
                MainController controller = loader.getController();
                controller.loadView(MainController.PROFILE_VIEW);
                Scene scene = new Scene(root);

                Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                primaryStage.setScene(scene);
                primaryStage.show();
            } else {
                loginMessageLabel.setText("Wrong credentials!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static User findByUsername(String username) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/v1/users/" + username))
                .setHeader("Authorization", "Bearer " +
                        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI2MjkzMmJlNzM2NDk2ZTVkYTg2ZDFjZWMiLCJpYXQiOjE2NTM4MTIxOTksImV4cCI6MTY4NTM2OTc5OSwidHlwZSI6ImFjY2VzcyJ9._A6yqeEor5zRfurLr_nwk4jEEufRZkkjwVe9vJyqJSo")
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());
        return parseReslogin(response.body());
    }

    private static User parseReslogin(String res) throws Exception {
        System.out.println(res);
        JSONObject myjson = new JSONObject(res);
        System.out.println(myjson);
        String id = myjson.getString("id");
        String username = myjson.getString("username");
        String fullname = myjson.getString("fullname");
        String email = myjson.getString("email");
        String country = myjson.getString("country");
        return new User(id, username, null, fullname, email, country);
    }


    @FXML
    private void onSignupButtonClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../views/signup.fxml"));
            Pane root = loader.load();
            SignupController controller = loader.getController();
            controller.setView2(controller.SIGN_UP_VIEW);

            Scene scene = new Scene(root);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            ErrorPopupComponent.show(e.toString());
        }
    }

    @FXML
    private Button loginButt;

    @FXML
    private Button signUpButt;

    private ChildController childController = null;

    @Override
    public void loadLangTexts(ResourceBundle langBundle) {
        String logInUsername = langBundle.getString("log_in_username");
        String logInPassword = langBundle.getString("log_in_password");
        String logInButton = langBundle.getString("log_in_button");
        String signUpButton = langBundle.getString("sign_up_button");
        try {
            username.setPromptText(logInUsername);
            password.setPromptText(logInPassword);
            loginButt.setText(logInButton);
            signUpButt.setText(signUpButton);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (this.childController != null) {
            this.childController.loadLangTexts(langBundle);
        }
    }
}