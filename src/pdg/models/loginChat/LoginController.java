package pdg.models.loginChat;


import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import pdg.models.chatwindow.ChatController;
import pdg.models.chatwindow.Listener;
import pdg.models.utilChat.ResizeHelper;
import pdg.utils.SessionManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable {
    @FXML private ImageView Defaultview;
    @FXML private ImageView Blinaview;
    @FXML private ImageView Florianiview;
    @FXML private ImageView Arbreshaview;
    @FXML public  TextField hostnameTextfield;
    @FXML private TextField portTextfield;
    @FXML private TextField usernameTextfield;
    @FXML private ChoiceBox imagePicker;
    @FXML private Label selectedPicture;
    public static ChatController con;
    @FXML private BorderPane borderPane;
    private double xOffset;
    private double yOffset;
    private Scene scene;

    private static LoginController instance;

    public LoginController() {
        instance = this;
    }

    public static LoginController getInstance() {
        return instance;
    }
    public void loginButtonAction() throws IOException {
//        String hostname = hostnameTextfield.getText();
//        int port = 9001;
//        String username = usernameTextfield.getText();
        String picture = selectedPicture.getText();

        FXMLLoader fmxlLoader = new FXMLLoader(getClass().getResource("views/ChatView.fxml"));
        Parent window = (Pane) fmxlLoader.load();
        con = fmxlLoader.<ChatController>getController();
        Listener listener = new Listener("localhost", 9001, SessionManager.user.getUsername(), picture, con);
        Thread x = new Thread(listener);
        x.start();
        this.scene = new Scene(window);
    }

    public void showScene() throws IOException {
        Platform.runLater(() -> {
            Stage stage = (Stage) hostnameTextfield.getScene().getWindow();
            stage.setResizable(true);
            stage.setWidth(1040);
            stage.setHeight(620);

            stage.setOnCloseRequest((WindowEvent e) -> {
                Platform.exit();
                System.exit(0);
            });
            stage.setScene(this.scene);
            stage.setMinWidth(800);
            stage.setMinHeight(300);
            ResizeHelper.addResizeListener(stage);
            stage.centerOnScreen();
            con.setUsernameLabel(usernameTextfield.getText());
            con.setImageLabel(selectedPicture.getText());
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imagePicker.getSelectionModel().selectFirst();
        selectedPicture.textProperty().bind(imagePicker.getSelectionModel().selectedItemProperty());
        selectedPicture.setVisible(false);

//        /* Drag and Drop */
//        borderPane.setOnMousePressed(event -> {
//            xOffset = MainLauncher.getPrimaryStage().getX() - event.getScreenX();
//            yOffset = MainLauncher.getPrimaryStage().getY() - event.getScreenY();
//            borderPane.setCursor(Cursor.CLOSED_HAND);
//        });
//
//        borderPane.setOnMouseDragged(event -> {
//            MainLauncher.getPrimaryStage().setX(event.getScreenX() + xOffset);
//            MainLauncher.getPrimaryStage().setY(event.getScreenY() + yOffset);
//
//        });
//
//        borderPane.setOnMouseReleased(event -> {
//            borderPane.setCursor(Cursor.DEFAULT);
//        });

        imagePicker.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> selected, String oldPicture, String newPicture) {
                if (oldPicture != null) {
                    switch (oldPicture) {
                        case "Default":
                            Defaultview.setVisible(false);
                            break;
                        case "Floriani":
                            Florianiview.setVisible(false);
                            break;
                        case "Blina":
                            Blinaview.setVisible(false);
                            break;
                        case "Arbresha":
                            Arbreshaview.setVisible(false);
                            break;
                    }
                }
                if (newPicture != null) {
                    switch (newPicture) {
                        case "Default":
                            Defaultview.setVisible(true);
                            break;
                        case "Floriani":
                            Florianiview.setVisible(true);
                            break;
                        case "Blina":
                            Blinaview.setVisible(true);
                            break;
                        case "Arbresha":
                            Arbreshaview.setVisible(true);
                            break;

                    }
                }
            }
        });
    }


    /* This method is used to generate the animation on the login window, It will generate random ints to determine
     * the size, speed, starting points and direction of each square.
     */



    /* Terminates Application */
    public void closeSystem(){
        Platform.exit();
        System.exit(0);
    }

    public void minimizeWindow(){
        MainLauncher.getPrimaryStage().setIconified(true);
    }

    /* This displays an alert message to the user */
    public void showErrorDialog(String message) {
        Platform.runLater(()-> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setHeaderText(message);
            alert.setContentText("Please check for firewall issues and check if the server is running.");
            alert.showAndWait();
        });

    }
}
