package pdg.repositories;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import pdg.models.Product;
import pdg.models.Wishlist;
import pdg.utils.DateHelper;
import pdg.utils.DbHelper;
import pdg.utils.SessionManager;

public class WishlistRepository {
    public static int count() throws Exception {
        Connection conn = DbHelper.getConnection();
        ResultSet res = conn.prepareStatement("SELECT COUNT(*) FROM wishlist").executeQuery();
        res.next();
        return res.getInt(1);
    }

    public static List<Wishlist> getAll(int pageSize, int page) throws Exception {
        PreparedStatement stmt = DbHelper.getConnection()
                .prepareStatement("SELECT * FROM wishlist WHERE user_id = ? LIMIT ? OFFSET ?");
        stmt.setInt(1, SessionManager.user.getId());
//        System.out.println(SessionManager.user.getId());
        stmt.setInt(2, pageSize);
        stmt.setInt(3, page * pageSize);

        ResultSet res = stmt.executeQuery();
        List<Wishlist> list = new ArrayList<>();
        while (res.next()) {
            list.add(parseRes(res));
        }
        return list;
    }

    public static Wishlist find(int id) throws Exception {
        PreparedStatement stmt = DbHelper.getConnection().prepareStatement("SELECT * FROM wishlist WHERE id = ? LIMIT 1");
        stmt.setInt(1, id);

        ResultSet res = stmt.executeQuery();
        if (!res.next()) return null;
        return parseRes(res);
    }

    public static List<Wishlist> find(String text) throws Exception {
        PreparedStatement stmt = DbHelper.getConnection()
                .prepareStatement("SELECT * FROM wishlist WHERE title LIKE ? ORDER BY id ASC");
        stmt.setString(1, text + '%');

        ResultSet res = stmt.executeQuery();
        List<Wishlist> list = new ArrayList<>();
        while (res.next()) {
            list.add(parseRes(res));
        }
        return list;
    }

    public static Wishlist create(Wishlist model) throws Exception {
        String query = "INSERT INTO wishlist (user_id, product_id) VALUES (?, ?)";
        PreparedStatement stmt = DbHelper.getConnection().prepareStatement(query);
        stmt.setInt(1, model.getUserId());
        stmt.setInt(2, model.getProductId());

        if (stmt.executeUpdate() != 1)
            throw new Exception("ERR_NO_ROW_CHANGE");

        stmt = DbHelper.getConnection().prepareStatement("SELECT * FROM wishlist ORDER BY createdAt DESC LIMIT 1");
        ResultSet res = stmt.executeQuery();
        res.next();
        return parseRes(res);
    }

    public static boolean remove(int id) throws Exception {
        String query = "DELETE FROM products WHERE id = ?";
        PreparedStatement stmt = DbHelper.getConnection().prepareStatement(query);
        stmt.setInt(1, id);
        return stmt.executeUpdate() == 1;
    }

    private static Wishlist parseRes(ResultSet res) throws Exception {
        int id = res.getInt("id");
        int userId = res.getInt("user_id");
        int productId = res.getInt("product_id");
        Date createdAt = DateHelper.fromSql(res.getString("created_date"));
        return new Wishlist(userId, productId);
    }
}