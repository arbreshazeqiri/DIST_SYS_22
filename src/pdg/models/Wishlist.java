package pdg.models;


import java.sql.Timestamp;
import java.util.Date;

//Defining the table

public class Wishlist {

    private Integer id;

    private Integer userId;

    private Integer productId;

    private Date createdDate;

    private Product product;


    public Wishlist(Integer userId, Integer productId) {
        this.userId = userId;
        this.productId=productId;
        //storing the current data & time in created_date column
        this.createdDate = new Date();
    }

    //Setter & Getters

    public Integer getId() {
        return id;
    }

    public Integer getProductId() {
        return productId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }
}