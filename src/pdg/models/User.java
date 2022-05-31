package pdg.models;

import org.json.JSONArray;
import org.json.JSONObject;
import pdg.utils.SessionManager;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class User {
    private JSONArray cart;
    private JSONArray wishlist;
    private String id;
    private String username;
    private String fullname;
    private String email;
    private String password;
    private String country;
    private String token;

    public User(String id, String username, JSONArray wishlist, JSONArray cart, String fullname, String email, String country, String token) {
        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.email = email;
        this.wishlist = wishlist;
        this.country = country;
        this.cart = cart;
        this.token = token;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public JSONArray getWishlist() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/v1/users/" + SessionManager.user.getId()))
                .setHeader("Authorization", "Bearer " + SessionManager.user.getToken())
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
        JSONObject myjson1 = new JSONObject(response.body());
        JSONArray wishList = myjson1.getJSONArray("wishlist");
//        setWishlist(wishList);
        return wishList;
    }

    public JSONArray getCart() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/v1/users/" + SessionManager.user.getId()))
                .setHeader("Authorization", "Bearer " + SessionManager.user.getToken())
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
        JSONObject myjson1 = new JSONObject(response.body());
        JSONArray cart = myjson1.getJSONArray("cart");
//        setCart(cart);
        return cart;
    }

//    public void addToWishlist(String productId) {
//        wishlist.put(productId);
//    }
//
//    public void removeFromWishlist(String productId) {
//        List<String> valueList = new ArrayList<>();
//        for (int i = 0; i < wishlist.length(); i++) {
//            valueList.add(wishlist.getString(i));
//        }
//        int index = valueList.indexOf(productId);
//        wishlist.remove(index);
//    }

//    public void addToCart(String productId) { cart.put(productId); }
//
//    public void removeFromCart(String productId) {
//        List<String> valueList = new ArrayList<>();
//        for(int i=0;i< cart.length();i++){
//            valueList.add(cart.getString(i));
//        }
//        int index = valueList.indexOf(productId);
//        cart.remove(index);
//    }

    public void setCart(JSONArray cart) {
        this.cart = cart;
    }

    public void setWishlist(JSONArray wishlist) {
        this.wishlist = wishlist;
    }


    public String getPassword() {
        return password;
    }

    public String getCountry() {
        return country;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setPassword(String text) {
        this.password = text;
    }
}