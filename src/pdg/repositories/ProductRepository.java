package pdg.repositories;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import pdg.models.Product;
import pdg.utils.DateHelper;
import pdg.utils.DbHelper;

public class ProductRepository {
    public static int count() throws Exception {
        Connection conn = DbHelper.getConnection();
        ResultSet res = conn.prepareStatement("SELECT COUNT(*) FROM products").executeQuery();
        res.next();
        return res.getInt(1);
    }

    public static List<Product> getAll(int pageSize, int page) throws Exception {
        PreparedStatement stmt = DbHelper.getConnection()
                .prepareStatement("SELECT * FROM products ORDER BY id ASC LIMIT ? OFFSET ?");
        stmt.setInt(1, pageSize);
        stmt.setInt(2, page * pageSize);

        ResultSet res = stmt.executeQuery();
        List<Product> list = new ArrayList<>();
        while (res.next()) {
            list.add(parseRes(res));
        }
        return list;
    }

    public static Product find(int id) throws Exception {
        PreparedStatement stmt = DbHelper.getConnection().prepareStatement("SELECT * FROM products WHERE id = ? LIMIT 1");
        stmt.setInt(1, id);

        ResultSet res = stmt.executeQuery();
        if (!res.next()) return null;
        return parseRes(res);
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

    public static Product update(Product model) throws Exception {
        String query = "UPDATE products SET title = ?, description = ?, image = ?, price = ?, qty = ?, updatedAt = CURRENT_TIMESTAMP WHERE id = ?";
        PreparedStatement stmt = DbHelper.getConnection().prepareStatement(query);
        stmt.setString(1, model.getTitle());
        stmt.setString(2, model.getDescription());
        stmt.setString(3, model.getImage());
        stmt.setDouble(4, model.getPrice());
        stmt.setDouble(5, model.getQty());
        stmt.setInt(6, model.getId());

        if (stmt.executeUpdate() != 1)
            throw new Exception("ERR_NO_ROW_CHANGE");

        return find(model.getId());
    }

    public static boolean remove(int id) throws Exception {
        String query = "DELETE FROM products WHERE id = ?";
        PreparedStatement stmt = DbHelper.getConnection().prepareStatement(query);
        stmt.setInt(1, id);
        return stmt.executeUpdate() == 1;
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