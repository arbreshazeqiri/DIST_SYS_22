package pdg.controllers.partials;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.json.JSONArray;
import pdg.models.Product;
import pdg.utils.SessionManager;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;


public class ProductCardController implements Initializable {
    @FXML
    private Button cartButton;
    @FXML
    private Button wishlistButton;
    @FXML
    private Label nameLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Label stockLabel;
    @FXML
    private ImageView fotoja;
    @FXML
    private ImageView wishlistIcon;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    }
    String address = "src/pdg/resources/images/";

    public void setProduct(Product product){
        nameLabel.setText(product.getDescription());
        priceLabel.setText(product.getPrice() + "€");
        address = address.concat(product.getImage()).concat(".jpg");
        File f = new File(address);
        fotoja.setImage(new Image(f.toURI().toString()));
        stockLabel.setText("STOCK: " + product.getQty());

        File fo;
        JSONArray wishlist = SessionManager.user.getWishlist();
        if(wishlist.toString().contains(product.getId())){
            fo = new File("src/pdg/resources/images/fullheart.png");
        }
        else{
            fo = new File("src/pdg/resources/images/heart.png");
        }
        wishlistIcon.setImage(new Image(fo.toURI().toString()));
    }

//
    public void setOnWishlistAction(EventHandler<ActionEvent> handler) {
        this.wishlistButton.setOnAction(handler);
    }

    public void setOnCartAction(EventHandler<ActionEvent> handler) {
        this.cartButton.setOnAction(handler);
    }

}
