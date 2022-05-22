package pdg.components;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pdg.controllers.partials.FindProductController;

public class FindProductComponent {
    public String showDialog() throws Exception {
        final StringBuilder sb = new StringBuilder("");
        Stage stage = new Stage();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("../views/partials/find-product.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        FindProductController controller = loader.getController();
        controller.setOnKeyPressedAction(val -> {
            sb.append(val);
            stage.close();
        });

        stage.setTitle("Find product");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UTILITY);
        stage.setScene(scene);
        stage.showAndWait();

        return sb.toString();
    }
}