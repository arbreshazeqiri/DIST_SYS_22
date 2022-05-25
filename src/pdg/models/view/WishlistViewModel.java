package pdg.models.view;

import javafx.beans.property.*;

import java.util.Date;

import pdg.models.Product;
import pdg.models.User;
import pdg.models.Wishlist;
import pdg.utils.DateHelper;

public class WishlistViewModel {
    private IntegerProperty id;
    private IntegerProperty userId;
    private IntegerProperty productId;
    private StringProperty createdAt;

    public WishlistViewModel() {
        id = new SimpleIntegerProperty();
        userId = new SimpleIntegerProperty();
        productId = new SimpleIntegerProperty();
        productId = new SimpleIntegerProperty();
        createdAt = new SimpleStringProperty();
    }

    public WishlistViewModel(Product model, User modeli) {
        this();
        this.setUserId(modeli.getId());
        this.setProductId(model.getId());
        this.setCreatedAt(model.getCreatedAt());
    }


    public WishlistViewModel(Wishlist model) {
        this();
        this.setId(model.getId());
        this.setUserId(model.getUserId());
        this.setProductId(model.getProductId());
        this.setCreatedAt(model.getCreatedDate());
    }

    private void setUserId(int userId) {
        this.userId.setValue(userId);
    }

    public int getUserId() { return this.userId.getValue(); }

    public IntegerProperty idProperty() {
        return id;
    }

    public int getId() {
        return id.getValue();
    }

    public void setId(int value) {
        id.setValue(value);
    }

    public IntegerProperty userIdProperty() { return userId; }

    public IntegerProperty productIdProperty() { return productId; }

    public int getProductId() { return productId.getValue(); }

    public void setProductId(int value) { productId.setValue(value); }

    public StringProperty createdAtProperty() {
        return createdAt;
    }

    public Date getCreatedAt() {
        try {
            return DateHelper.fromSql(createdAt.getValue());
        } catch (Exception e) {
            return null;
        }
    }

    public void setCreatedAt(String value) {
        createdAt.setValue(value);
    }

    public void setCreatedAt(Date value) {
        createdAt.setValue(DateHelper.toSqlFormat(value));
    }


    public Wishlist getModel() {
        return new Wishlist(getUserId(), getProductId());
    }
}
