package pdg.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pdg.models.Product;
import pdg.repositories.CartRepository;
import pdg.utils.SessionManager;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static java.lang.Double.valueOf;

public class CheckoutController extends ChildController {

    @FXML
    private TextField fullnameField;
    @FXML
    private TextField emailField;
    @FXML
    private Label totalLabel;


    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        fullnameField.setText(SessionManager.user.getFullName().toUpperCase());
        emailField.setText(SessionManager.user.getEmail());
        fullnameField.setDisable(true);
        emailField.setDisable(true);
        Double total = 0.0;
        try {
            List<Product> checkoutItems = CartRepository.getAll();
            for(int i=0; i<checkoutItems.size(); i++){
                total += valueOf(checkoutItems.get(i).getPrice());
            }
            totalLabel.setText(total+ "EUR");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}