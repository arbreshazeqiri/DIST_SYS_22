package pdg.components;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import pdg.controllers.partials.ProductCardController;
import pdg.controllers.partials.WishlistProductCardController;
import pdg.models.Product;
import pdg.models.Wishlist;

public class WishlistProductCardComponent {

    public Node getContent(Wishlist wishlist, EventHandler<ActionEvent> editHandler, EventHandler<ActionEvent> deleteHandler)
            throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("../views/partials/wishlist-product-card.fxml"));

        Node node = loader.load();

        WishlistProductCardController controller = loader.getController();
        controller.setWishlistProduct(wishlist);
//        controller.setOnEditAction(editHandler);
//        controller.setOnDeleteAction(deleteHandler);
//      controller.setOnActiveAction(activeHandler);

        return node;
    }
}
