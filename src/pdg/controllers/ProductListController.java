package pdg.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import pdg.components.ErrorPopupComponent;
import pdg.components.ProductCardComponent;
import pdg.models.Product;
import pdg.repositories.ProductRepository;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProductListController extends ChildController {

    @FXML
    private FlowPane productsPane;

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        super.initialize(url, bundle);
        try {
            showProducts();
        } catch (Exception e) {
            ErrorPopupComponent.show(String.valueOf(e));
        }
    }

    public void showProducts() throws Exception {
        productsPane.getChildren().clear();
        List<Product> products = ProductRepository.getAll();
        ProductCardComponent productCard = new ProductCardComponent();
        for (Product product : products) {
            productsPane.getChildren()
                    .add(productCard.getContent(product, e -> addProductToWishlist(product), e -> addProductToCart(product)));
        }
    }

    private void addProductToWishlist(Product product) {
        try {
            ProductRepository.addProductToWishlist(product.getId());
            showProducts();
        } catch (Exception e) {
            ErrorPopupComponent.show(String.valueOf(e));
        }
    }

    private void addProductToCart(Product product) {
        try {ProductRepository.addProductToCart(product.getId());
            showProducts();
        } catch (Exception e) {
            ErrorPopupComponent.show(String.valueOf(e));
        }
    }

    @Override
    public void loadLangTexts(ResourceBundle langBundle) {
        // TODO Auto-generated method stub
    }
}
