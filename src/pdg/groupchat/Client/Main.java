package pdg.groupchat.Client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Image image = new Image("pdg/resources/images/chat.png");
        primaryStage.getIcons().add(image);
        primaryStage.setTitle("ZEBRA GROUP CHAT");
        primaryStage.setScene(new Scene(root, 330, 560));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
