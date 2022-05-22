package pdg.controllers.partials;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public class FindProductController implements Initializable {
    @FXML
    private TextField searchField;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    }

    public void setOnKeyPressedAction(SearchSubmittedHandler handler) {
        searchField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                handler.run(searchField.getText());
            }
        });
    }

    public interface SearchSubmittedHandler {
        public void run(String text);
    }
}