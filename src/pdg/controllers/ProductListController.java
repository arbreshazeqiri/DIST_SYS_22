package pdg.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import pdg.components.ErrorPopupComponent;
import pdg.components.ProductCardComponent;
import pdg.models.Product;
import pdg.repositories.ProductRepository;
import pdg.utils.SessionManager;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProductListController extends ChildController {

    @FXML
    private FlowPane productsPane;
    @FXML
    private ImageView wishlistIcon;

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

    private void showProduct(Product product) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../views/" + MainController.PRODUCT_DETAILS_VIEW + ".fxml"));

            Pane pane = loader.load();
            ProductDetailsController controller = loader.getController();
            controller.setModel(product);

            parentController.loadView(MainController.PRODUCT_DETAILS_VIEW, pane, controller);
        } catch (Exception e) {
            ErrorPopupComponent.show(String.valueOf(e));
        }
    }

    private void addProductToCart(Product product) {
        //TODO PUT METHOD ON WISHLIST ON ADDRESS localhost:3000/v1/users/62916dfe264eb322e067616e/products/6293abbbb8791022f4c32401/wishlist
    }

    private void addProductToWishlist(Product product) {
        try {
            ProductRepository.addProductToWishlist(product.getId());
            SessionManager.user.setWishlist(product.getId());
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
