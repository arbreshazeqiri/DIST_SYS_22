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
import org.json.JSONArray;
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

    public void validateLogin(ActionEvent event) {
        try {
            User user = loginUser(username.getText(), password.getText());
            if (user != null) {
                FXMLLoader loader = new FXMLLoader();
                SessionManager.user = user;
                user.setPassword(password.getText());
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

    public static User loginUser(String username, String password) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            String input = "{ \"username\":\"" + username + "\", \"password\":\"" + password + "\" }";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:3000/v1/auth/login"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(input))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() == 200) {
                return parseReslogin(response.body());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static User parseReslogin(String res) throws Exception {
        JSONObject myjson1 = new JSONObject(res);
        JSONObject myjson = myjson1.getJSONObject("user");
        String id = myjson.getString("id");
        String username = myjson.getString("username");
        String fullname = myjson.getString("fullname");
        String email = myjson.getString("email");
        String country = myjson.getString("country");
        JSONArray wishList = myjson.getJSONArray("wishlist");
        JSONArray cartList = myjson.getJSONArray("cart");
        JSONObject tokens = myjson1.getJSONObject("tokens");
        JSONObject access = tokens.getJSONObject("access");
        String token = access.getString("token");
        return new User(id, username, wishList, cartList, fullname, email, country, token);
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