package Database;

import Data.Product;
import Data.Section;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseService {
    private final Connection connection;
    private final Statement statement;
    private final StringBuilder result;

    public DatabaseService(String url, String ip, String port)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        connection = DriverManager.getConnection(url, ip, port);
        statement = connection.createStatement();
        result = new StringBuilder();
    }

    public ArrayList<Section> showSections() {
        String sql = "SELECT * FROM SECTIONS";
        try {
            ResultSet rs = statement.executeQuery(sql);
            ArrayList<Section> sections = new ArrayList<Section>();
            while (rs.next()) {
                sections.add(new Section(rs.getInt("ID_S"), rs.getString("NAME")));
            }
            rs.close();
            return sections;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Product> showProductInSection(int code) {
        String sql = "SELECT ID_P, NAME FROM PRODUCTS WHERE ID_S = " + code;
        try {
            ResultSet rs = statement.executeQuery(sql);
            ArrayList<Product> products = new ArrayList<Product>();
            while (rs.next()) {
                products.add(new Product(rs.getInt("ID_P"), rs.getString("NAME"), rs.getInt("ID_S"), rs.getInt("PRICE")));
            }
            rs.close();
            return products;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addSection(int id, String name) {
        String sql = "INSERT INTO SECTIONS (ID_S, NAME) " +
                "VALUES (" + id + ", '" + name + "')";
        try {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateSection(int id, String name) {
        String sql = "UPDATE SECTIONS SET NAME = '" + name + "' WHERE ID_S = " + id;
        try {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateProduct(int id, String name, int section, int price) {
        String sql = "UPDATE PRODUCTS SET ID_S =  " + section + ", NAME = '" + name + "', PRICE = " + price + " WHERE ID_P = " + id;
        try {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addProduct(int id, String name, int section, int price) {
        String sql = "INSERT INTO PRODUCTS (ID_P, NAME, ID_S, PRICE) " +
                "VALUES (" + id + ", '" + name + "' , '" + section + "' , '" + price + "')";
        try {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteSection(int id) {
        String sql = "DELETE FROM SECTIONS WHERE ID_S =" + id;
        try {
             statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteProduct(int id) {
        String sql = "DELETE FROM PRODUCTS WHERE ID_P =" + id;
        try {
             statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int showProductCountInSection(int code) {
        String sql = "SELECT COUNT(*) AS PRODUCT_COUNT FROM PRODUCTS WHERE ID_S = " + code;
        try {
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                return rs.getInt("PRODUCT_COUNT");
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public Product getProductByName(String name) {
        String sql = "SELECT * FROM PRODUCTS WHERE NAME = '" + name +"'";
        try {
            ResultSet rs = statement.executeQuery(sql);
            rs.next();
            Product product = new Product(rs.getInt("ID_P"), rs.getString("NAME"), rs.getInt("ID_S"), rs.getInt("PRICE"));
            rs.close();
            return product;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
