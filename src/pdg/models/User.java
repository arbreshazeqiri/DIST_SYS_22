package pdg.models;

public class User {
    private String cart;
    private String wishlist;
    private String id;
    private String username;
    private String fullname;
    private String email;
    private String password;
    private String salt;
    private String country;

    public User(String id, String username, String wishlist, String cart, String fullname, String email, String country) {
        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.email = email;
        this.wishlist = wishlist;
        this.country = country;
        this.cart = cart;
    }

    public User(String username, String fullname, String email, String password, String country) {
        this.fullname = fullname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.country = country;
    }

    public User(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullname;
    }

    public void setFullName(String fullName) {
        this.fullname = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}