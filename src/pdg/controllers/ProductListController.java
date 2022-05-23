package pdg.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import pdg.components.ErrorPopupComponent;
import pdg.components.PaginationComponent;
import pdg.components.ProductCardComponent;
import pdg.models.Product;
import pdg.repositories.ProductRepository;

public class ProductListController extends ChildController {
    private final int PAGE_SIZE = 10;

    private PaginationComponent paginationComponent;

    @FXML
    private FlowPane productsPane;
    @FXML
    private HBox paginationPane;

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        super.initialize(url, bundle);
        try {
            paginationComponent = new PaginationComponent(productCount(), PAGE_SIZE);
            paginationComponent.render(paginationPane, (page) -> {
                try {
                    showProducts(page);
                } catch (Exception e) {
                    ErrorPopupComponent.show(String.valueOf(e));
                }
            });

            showProducts(0);
        } catch (Exception e) {
            ErrorPopupComponent.show(String.valueOf(e));
        }
    }

    private int productCount() throws Exception {
        return ProductRepository.count();
    }

    private void showProducts(int page) throws Exception {
        productsPane.getChildren().clear();
        List<Product> products = ProductRepository.getAll(PAGE_SIZE, page);
        ProductCardComponent productCard = new ProductCardComponent();
        for (Product product: products) {
            productsPane.getChildren().add(productCard.getContent(product, e -> showProduct(product), e -> removeProduct(product)));
        }
    }

    private void removeProduct(Product product) {
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

//
//    private void removeUser(User user) {
//        try {
//            UserRepository.remove(user.getUsername());
//
//            int currPage = paginationComponent.getCursor();
//            paginationComponent = new PaginationComponent(userCount(), PAGE_SIZE);
//            paginationComponent.render(paginationPane, (page) -> {
//                try {
//                    showUsers(page);
//                } catch (Exception e) {
//                    ErrorPopupComponent.show(String.valueOf(e));
//                }
//            });
//
//            paginationComponent.forceCursor(currPage);
//            showUsers(currPage);
//        } catch (Exception e) {
//            ErrorPopupComponent.show(String.valueOf(e));
//        }
//    }

//    private void showUser(User user) {
//        try {
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(getClass().getResource("../views/" + MainController.USER_DETAILS_VIEW + ".fxml"));
//
//            Pane pane = loader.load();
//            UserDetailsController controller = loader.getController();
//            controller.setModel(user);
//
//            parentController.loadView(MainController.USER_DETAILS_VIEW, pane, controller);
//        } catch (Exception e) {
//            ErrorPopupComponent.show(String.valueOf(e));
//        }
//    }

//    private void changeUserState(User user) {
//        try {
//            user.setActive(!user.getActive());
//            UserRepository.update(user);
//        } catch (Exception e) {
//            ErrorPopupComponent.show(e);
//        }
//    }

    @Override
    public void loadLangTexts(ResourceBundle langBundle) {
        // TODO Auto-generated method stub

    }
}
