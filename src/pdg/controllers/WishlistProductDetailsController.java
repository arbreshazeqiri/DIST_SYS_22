package pdg.controllers;

import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import pdg.components.ErrorPopupComponent;
import pdg.models.Product;
import pdg.models.Wishlist;
import pdg.models.view.ProductViewModel;
import pdg.models.view.WishlistViewModel;

public class WishlistProductDetailsController extends ChildController {
    private Wishlist originalModel;
    private WishlistViewModel viewModel;

    @FXML
    private TextField userIdField;
    @FXML
    private TextField productIdField;
    @FXML
    private TextField createdAtField;


    public void setModel(Wishlist model) {
        originalModel = model;
        viewModel = new WishlistViewModel(model);

        userIdField.setText(String.valueOf(viewModel.getUserId()));
        productIdField.setText(String.valueOf(viewModel.getProductId()));
        createdAtField.setText(String.valueOf(viewModel.getCreatedAt()));

    }

    @FXML
    private void onCancelClick(ActionEvent event) {
        try {
            parentController.loadView(MainController.WISHLIST_PRODUCT_LIST_VIEW);
        } catch (Exception e) {
            ErrorPopupComponent.show(String.valueOf(e));
        }
    }

    @Override
    public void loadLangTexts(ResourceBundle langBundle) {
        //TODO Auto-generated method stub

    }
}
