package pdg.repositories;

import pdg.models.Product;
import pdg.utils.DateHelper;
import pdg.utils.DbHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductRepository {
    public static int count() throws Exception {
        Connection conn = DbHelper.getConnection();
        ResultSet res = conn.prepareStatement("SELECT COUNT(*) FROM products").executeQuery();
        res.next();
        return res.getInt(1);
    }
//
//    public static List<Product> getAll(int pageSize, int page) throws Exception {
//            HttpClient client = HttpClient.newHttpClient();
//            HttpRequest request = HttpRequest.newBuilder()
//                    .uri(URI.create("http://localhost:3000/v1/prod/" + username))
//                    .setHeader("Authorization", "Bearer " +
//                            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI2MjkzMmJlNzM2NDk2ZTVkYTg2ZDFjZWMiLCJpYXQiOjE2NTM4MTIxOTksImV4cCI6MTY4NTM2OTc5OSwidHlwZSI6ImFjY2VzcyJ9._A6yqeEor5zRfurLr_nwk4jEEufRZkkjwVe9vJyqJSo")
//                    .build();
//
//            HttpResponse<String> response = client.send(request,
//                    HttpResponse.BodyHandlers.ofString());
//            return parseReslogin(response.body());
//        }
//
//
//
//        List<Product> list = new ArrayList<>();
//        while (res.next()) {
//            list.add(parseRes(res));
//        }
//        return list;
//    }

    public static Product find(int id) throws Exception {
        PreparedStatement stmt = DbHelper.getConnection().prepareStatement("SELECT * FROM products WHERE id = ? LIMIT 1");
        stmt.setInt(1, id);

        ResultSet res = stmt.executeQuery();
        if (!res.next()) return null;
        return parseRes(res);
    }

    public static Product findByAddress (String address) throws Exception {
        PreparedStatement stmt = DbHelper.getConnection()
                .prepareStatement("SELECT * FROM products WHERE image LIKE ? ORDER BY id ASC");
        stmt.setString(1, address + '%');

        ResultSet res = stmt.executeQuery();
        Product list = new Product();
        return list;
    }

    public static List<Product> find(String text) throws Exception {
        PreparedStatement stmt = DbHelper.getConnection()
                .prepareStatement("SELECT * FROM products WHERE title LIKE ? ORDER BY id ASC");
        stmt.setString(1, text + '%');

        ResultSet res = stmt.executeQuery();
        List<Product> list = new ArrayList<>();
        while (res.next()) {
            list.add(parseRes(res));
        }
        return list;
    }

    public static Product create(Product model) throws Exception {
        String query = "INSERT INTO products (title, description, image, price, qty) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmt = DbHelper.getConnection().prepareStatement(query);
        stmt.setString(1, model.getTitle());
        stmt.setString(2, model.getDescription());
        stmt.setString(3, model.getImage());
        stmt.setDouble(4, model.getPrice());
        stmt.setDouble(5, model.getQty());

        if (stmt.executeUpdate() != 1)
            throw new Exception("ERR_NO_ROW_CHANGE");

        stmt = DbHelper.getConnection().prepareStatement("SELECT * FROM products ORDER BY createdAt DESC LIMIT 1");
        ResultSet res = stmt.executeQuery();
        res.next();
        return parseRes(res);
    }

    private static Product parseRes(ResultSet res) throws Exception {
        int id = res.getInt("id");
        String title = res.getString("title");
        String description = res.getString("description");
        String image = res.getString("image");
        Double price = res.getDouble("price");
        Integer qty = res.getInt("qty");
        Date createdAt = DateHelper.fromSql(res.getString("createdAt"));
        Date updatedAt = DateHelper.fromSql(res.getString("createdAt"));
        return new Product(id, title, description, image, price, qty, createdAt, updatedAt);
    }
}