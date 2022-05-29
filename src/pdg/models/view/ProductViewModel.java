package pdg.models.view;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import pdg.models.Product;
import pdg.utils.DateHelper;

import java.util.Date;

public class ProductViewModel {
    private StringProperty id;
    private StringProperty title;
    private StringProperty description;
    private StringProperty image;
    private StringProperty price;
    private StringProperty qty;
    private StringProperty createdAt;
    private StringProperty updatedAt;

    public ProductViewModel() {
        id = new SimpleStringProperty();
        title = new SimpleStringProperty();
        description = new SimpleStringProperty();
        image = new SimpleStringProperty();
        price = new SimpleStringProperty();
        qty = new SimpleStringProperty();
        createdAt = new SimpleStringProperty();
        updatedAt = new SimpleStringProperty();
    }

    public ProductViewModel(Product model) {
        this();
        this.setId(model.getId());
        this.setTitle(model.getTitle());
        this.setDescription(model.getDescription());
        this.setImage(model.getImage());
        this.setPrice(model.getPrice());
        this.setQty(model.getQty());
    }

    public StringProperty idProperty() {
        return id;
    }

    public String getId() {
        return id.getValue();
    }

    public void setId(String value) {
        id.setValue(value);
    }

    public StringProperty titleProperty() {
        return title;
    }

    public String getTitle() {
        return title.getValue();
    }

    public void setTitle(String value) {
        title.setValue(value);
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public String getDescription() {
        return description.getValue();
    }

    public void setDescription(String value) {
        description.setValue(value);
    }

    public StringProperty imageProperty() {
        return image;
    }

    public String getImage() {
        return image.getValue();
    }

    public void setImage(String value) {
        image.setValue(value);
    }

    public Property priceProperty() {
        return price;
    }

    public String getPrice() {
        return price.getValue();
    }

    public void setPrice(String value) {
        price.setValue(value);
    }

    public StringProperty qtyProperty() {
        return qty;
    }

    public String getQty() {
        return qty.getValue();
    }

    public void setQty(String value) {
        qty.setValue(value);
    }

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

    public StringProperty updatedAtProperty() {
        return updatedAt;
    }

    public Date getUpdatedAt() {
        try {
            return DateHelper.fromSql(updatedAt.getValue());
        } catch (Exception e) {
            return null;
        }
    }

    public void setUpdatedAt(String value) {
        updatedAt.setValue(value);
    }

    public void setUpdatedAt(Date value) {
        updatedAt.setValue(DateHelper.toSqlFormat(value));
    }

    public Product getModel() {
        return new Product(getId(), getTitle(), getDescription(), getImage(), getPrice(), getQty());
    }

}
