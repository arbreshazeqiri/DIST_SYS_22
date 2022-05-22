package pdg.controllers;

import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import pdg.components.ErrorPopupComponent;
import pdg.models.Product;
import pdg.models.view.ProductViewModel;

public class ProductDetailsController extends ChildController {
    private Product originalModel;
    private ProductViewModel viewModel;

    @FXML
    private TextField descriptionField;
    @FXML
    private TextField priceField;


    public void setModel(Product model) {
        originalModel = model;
        viewModel = new ProductViewModel(model);

        descriptionField.setText((String) viewModel.getDescription());
        priceField.setText(viewModel.getPrice().toString());

        descriptionField.textProperty().bindBidirectional(viewModel.descriptionProperty());
        priceField.textProperty().bindBidirectional(viewModel.priceProperty());
    }

    @FXML
    private void onCancelClick(ActionEvent event) {
        try {
            parentController.loadView(MainController.PRODUCT_LIST_VIEW);
        } catch (Exception e) {
            ErrorPopupComponent.show(String.valueOf(e));
        }
    }

    @Override
    public void loadLangTexts(ResourceBundle langBundle) {
        //TODO Auto-generated method stub

    }
}
