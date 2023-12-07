package Database;

import java.sql.*;

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

    public String showSections() {
        String sql = "SELECT ID_S, NAME FROM SECTIONS";
        try {
            ResultSet rs = statement.executeQuery(sql);
            result.append("ALL SECTIONS:#");
            while (rs.next()) {
                int id = rs.getInt("ID_S");
                String name = rs.getString("NAME");
                result.append(">>" + id + "-" + name).append("#");
            }
            rs.close();
        } catch (SQLException e) {
            result.append("ERROR when getting the list of section#");
            result.append(" >> " + e.getMessage()+"#");
        }
        return result.toString();
    }

    public String showProductInSection(int code) {
        String sql = "SELECT ID_P, NAME FROM PRODUCTS WHERE ID_S = " + code;
        try {
            ResultSet rs = statement.executeQuery(sql);
            result.append("ALL PRODUCTS IN SECTION " + code + ":#");
            while (rs.next()) {
                int id = rs.getInt("ID_P");
                String name = rs.getString("NAME");
                result.append(">>" + id + "-" + name + "#");
            }
            rs.close();
        } catch (SQLException e) {
            result.append("ERROR when getting the list of products#");
            result.append(" >> " + e.getMessage()+"#");
        }
        return result.toString();
    }

    public String addSection(int id, String name) {
        String sql = "INSERT INTO SECTIONS (ID_S, NAME) " +
                "VALUES (" + id + ", '" + name + "')";
        try {
            statement.executeUpdate(sql);
            result.append("Section ").append(name).append(" successfully added#");
            return result.toString();
        } catch (SQLException e) {
            result.append("ERROR! Section ").append(name).append("is NOT added!#");
            result.append(" >> ").append(e.getMessage()).append("#");
            return result.toString();
        }
    }

    public String updateSection(int id, String name) {
        String sql = "UPDATE SECTIONS SET NAME = '" + name + "' WHERE ID_S = " + id;
        try {
            statement.executeUpdate(sql);
            result.append("Section ").append(name).append(" successfully updated#");
            return result.toString();
        } catch (SQLException e) {
            result.append("ERROR! Section ").append(name).append("is NOT updated!#");
            result.append(" >> ").append(e.getMessage()).append("#");
            return result.toString();
        }
    }

    public String updateProduct(int id, String name, int section, int price) {
        String sql = "UPDATE PRODUCTS SET ID_S =  " + section + ", NAME = '" + name + "', PRICE = " + price + " WHERE ID_P = " + id;
        try {
            statement.executeUpdate(sql);
            result.append("Product ").append(name).append(" successfully updated#");
            return result.toString();
        } catch (SQLException e) {
            result.append("ERROR! Product ").append(name).append("is NOT updated!#");
            result.append(" >> ").append(e.getMessage()).append("#");
            return result.toString();
        }
    }

    public String addProduct(int id, String name, int section, int price) {
        String sql = "INSERT INTO PRODUCTS (ID_P, NAME, ID_S, PRICE) " +
                "VALUES (" + id + ", '" + name + "' , '" + section + "' , '" + price + "')";
        try {
            statement.executeUpdate(sql);
            result.append("Product ").append(name).append(" successfully added#");
            return result.toString();
        } catch (SQLException e) {
            result.append("ERROR! Product ").append(name).append(" is NOT added!#");
            result.append(" >> ").append(e.getMessage()).append("#");
            return result.toString();
        }
    }

    public String deleteSection(int id) {
        String sql = "DELETE FROM SECTIONS WHERE ID_S =" + id;
        try {
            int c = statement.executeUpdate(sql);
            if (c > 0) {
                result.append("Section with code ").append(id).append(" successfully deleted!#");
            } else {
                result.append("Section with code ").append(id).append(" not found!#");

            }
        } catch (SQLException e) {
            result.append("ERROR when deleting the section from identifier ").append(id).append("#");
            result.append(" >> ").append(e.getMessage()).append("#");
        }
        return result.toString();
    }

    public String deleteProduct(int id) {
        String sql = "DELETE FROM PRODUCTS WHERE ID_P =" + id;
        try {
            int c = statement.executeUpdate(sql);
            if (c > 0) {
                result.append("Product with code ").append(id).append(" successfully deleted!#");
                return result.toString();
            } else {
                result.append("Product with code ").append(id).append(" not found!#");

                return result.toString();
            }
        } catch (SQLException e) {
            result.append("ERROR when deleting the product from identifier ").append(id).append("#");
            result.append(" >> ").append(e.getMessage()).append("#");
            return result.toString();
        }
    }

    public String showProductCountInSection(int code) {
        String sql = "SELECT COUNT(*) AS PRODUCT_COUNT FROM PRODUCTS WHERE ID_S = " + code;
        try {
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                int productCount = rs.getInt("PRODUCT_COUNT");
                result.append("NUMBER OF PRODUCTS IN SECTION ").append(code).append(": ").append(productCount).append("#");
            }
            rs.close();
        } catch (SQLException e) {
            result.append("ERROR when getting the product count#");
            result.append(" >> ").append(e.getMessage()).append("#");
        }
        return result.toString();
    }

    public String getProductByName(String name) {
        String sql = "SELECT * FROM PRODUCTS WHERE NAME = '" + name +"'";
        try {
            ResultSet rs = statement.executeQuery(sql);
            result.append("Product with name ").append(name).append(" has :#");
            while (rs.next()) {
                int id = rs.getInt("ID_P");
                String nameProduct = rs.getString("NAME");
                int section = rs.getInt("ID_S");
                int price = rs.getInt("PRICE");
                result.append("Id: ").append(id).append("#Section: ").append(section).append("#Price: ").append(price).append("#");
            }
            rs.close();
        } catch (SQLException e) {
            result.append("ERROR when getting the product#");
            result.append(" >> ").append(e.getMessage()).append("#");
        }
        return result.toString();
    }

    public void stop() throws SQLException {
        connection.close();
    }

}
