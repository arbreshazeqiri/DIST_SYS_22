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
import pdg.components.WishlistProductCardComponent;
import pdg.models.Wishlist;
import pdg.repositories.WishlistRepository;

public class WishlistProductController extends ChildController {
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
            paginationComponent = new PaginationComponent(wishlistProductCount(), PAGE_SIZE);
            paginationComponent.render(paginationPane, (page) -> {
                try {
                    showWishlistProducts(page);
                } catch (Exception e) {
                   e.printStackTrace();
                }
            });
            showWishlistProducts(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private int wishlistProductCount() throws Exception {
        return WishlistRepository.count();
    }

    private void showWishlistProducts(int page) throws Exception {
        productsPane.getChildren().clear();
        List<Wishlist> wishlists = WishlistRepository.getAll(PAGE_SIZE, page);
        WishlistProductCardComponent wishlistProductCard = new WishlistProductCardComponent();
        for (Wishlist wishlist: wishlists) {
            productsPane.getChildren().add(wishlistProductCard.getContent(wishlist, e -> showWishlistProduct(wishlist), e -> removeWishlistProduct(wishlist)));
        }
    }


    private void removeWishlistProduct(Wishlist wishlist) {
    }

    private void showWishlistProduct(Wishlist wishlist) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../views/" + MainController.WISHLIST_PRODUCT_DETAILS_VIEW + ".fxml"));

            Pane pane = loader.load();
            WishlistProductDetailsController controller = loader.getController();
            controller.setModel(wishlist);

            parentController.loadView(MainController.WISHLIST_PRODUCT_DETAILS_VIEW, pane, controller);
        } catch (Exception e) {
            e.printStackTrace();
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
