package pdg.components;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import pdg.controllers.partials.UserCardController;
import pdg.models.User;

public class UserCardComponent {
    public Node getContent(User user, EventHandler<ActionEvent> editHandler, EventHandler<ActionEvent> deleteHandler)
            throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("../views/partials/user-card.fxml"));

        Node node = loader.load();

        UserCardController controller = loader.getController();
        controller.setUser(user);
        controller.setOnEditAction(editHandler);
        controller.setOnDeleteAction(deleteHandler);
//        controller.setOnActiveAction(activeHandler);

        return node;
    }
}
