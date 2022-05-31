package pdg.repositories;

import org.json.JSONArray;
import org.json.JSONObject;
import pdg.models.Product;
import pdg.utils.SessionManager;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    public static int count() throws Exception {
        return getAll().size();
    }

    public static List<Product> getAll() throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/v1/product"))
                .setHeader("Authorization", "Bearer " + SessionManager.user.getToken())
                .setHeader("limit", "100")
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
        return parseRes(response.body());
    }

    private static List<Product> parseRes(String res) throws Exception {
        JSONObject myjson1 = new JSONObject(res);
        JSONArray myjson = myjson1.getJSONArray("results");
        List<Product> list = new ArrayList<>();
        for (int i = 0; i < myjson.length(); i++) {
            list.add(parseResLine(myjson.getJSONObject(i).toString()));
        }
        return list;
    }

    private static Product parseResLine(String res) {
        JSONObject myjson = new JSONObject(res);
        String id = myjson.getString("id");
        String title = myjson.getString("title");
        String description = myjson.getString("description");
        String image = myjson.getString("image");
        String price = myjson.getString("price");
        String qty = myjson.getString("quantity");
        return new Product(id, title, description, image, price, qty);
    }

    public static void addProductToWishlist(String productId) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:3000/v1/users/" + SessionManager.user.getId() + "/products/" + productId + "/wishlist"))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(""))
                    .setHeader("Authorization", "Bearer " + SessionManager.user.getToken())
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            SessionManager.user.setWishlist(SessionManager.user.getWishlist());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addProductToCart(String productId) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:3000/v1/users/" + SessionManager.user.getId() + "/products/" + productId + "/cart"))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(""))
                    .setHeader("Authorization", "Bearer " + SessionManager.user.getToken())
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            SessionManager.user.setCart(SessionManager.user.getCart());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}