package pdg.components;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import pdg.controllers.partials.ProductCardController;
import pdg.models.Product;

public class ProductCardComponent {
    public Node getContent(Product product, EventHandler<ActionEvent> editHandler, EventHandler<ActionEvent> deleteHandler)
            throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("../views/partials/product-card.fxml"));

        Node node = loader.load();

        ProductCardController controller = loader.getController();
        controller.setProduct(product);
        controller.setOnEditAction(editHandler);
        controller.setOnDeleteAction(deleteHandler);
//      controller.setOnActiveAction(activeHandler);

        return node;
    }
}
