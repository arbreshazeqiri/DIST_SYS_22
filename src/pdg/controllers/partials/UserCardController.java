package pdg.controllers.partials;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import pdg.models.User;

public class UserCardController implements Initializable {
    @FXML
    private Button deleteButton;
    @FXML
    private Button editButton;
    @FXML
    private CheckBox isActiveCheckBox;
    @FXML
    private Label idLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label nameLabel;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    }

    public void setUser(User user) {
        nameLabel.setText(user.getUsername());
    }

    public void setOnEditAction(EventHandler<ActionEvent> handler) {
        this.editButton.setOnAction(handler);
    }

    public void setOnDeleteAction(EventHandler<ActionEvent> handler) {
        this.deleteButton.setOnAction(handler);
    }

    public void setOnActiveAction(EventHandler<ActionEvent> handler) {
        this.isActiveCheckBox.setOnAction(handler);
    }
}
