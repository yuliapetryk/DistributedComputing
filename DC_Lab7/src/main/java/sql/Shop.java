package sql;
import  java.sql. *;

public class Shop {
    private final Connection connection;
    private final Statement statement;

    public Shop(String url, String ip, String port)
            throws  ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        connection = DriverManager.getConnection(url, ip, port);
        statement = connection.createStatement();
    }

    public void showSections() {
        String sql = "SELECT ID_S, NAME FROM SECTIONS";
        try {
            ResultSet rs = statement.executeQuery(sql);
            System.out.println("ALL SECTIONS:");
            while (rs.next()) {
                int id = rs.getInt("ID_S");
                String name = rs.getString("NAME");
                System.out.println(">>" + id + "-" + name);
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(
                    "ERROR when getting the list of section");
            System.out.println(" >> " + e.getMessage());
        }
    }

    public void showProductInSection(int code) {
        String sql = "SELECT ID_P, NAME FROM PRODUCTS WHERE ID_S = " + code;
        try {
            ResultSet rs = statement.executeQuery(sql);
            System.out.println("ALL PRODUCTS IN SECTION " + code + ":");
            while (rs.next()) {
                int id = rs.getInt("ID_P");
                String name = rs.getString("NAME");
                System.out.println(">>" + id + "-" + name);
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(
                    "ERROR when getting the list of products");
            System.out.println(" >> " + e.getMessage());
        }
    }

    public boolean addSection(int id, String name) {
        String sql = "INSERT INTO SECTIONS (ID_S, NAME) " +
                "VALUES (" + id + ", '" + name + "')";
        try {
            statement.executeUpdate(sql);
            System.out.println("Section " + name + " successfully added");
            return true;
        } catch (SQLException e) {
            System.out.println("ERROR! Section " + name + "is NOT added!");
            System.out.println(" >> " + e.getMessage());
            return false;
        }
    }

    public boolean updateSection(int id, String name) {
        String sql = "UPDATE SECTIONS SET NAME = '" + name + "' WHERE ID_S = " + id;
        try {
            statement.executeUpdate(sql);
            System.out.println("Section " + name + " successfully updated");
            return true;
        } catch (SQLException e) {
            System.out.println("ERROR! Section " + name + "is NOT updated!");
            System.out.println(" >> " + e.getMessage());
            return false;
        }
    }

    public boolean updateProduct(int id, String name, int section, int price) {
        String sql = "UPDATE PRODUCTS SET ID_S =  " + section + ", NAME = '" + name + "', PRICE = " + price + " WHERE ID_P = " + id;

        try {
            statement.executeUpdate(sql);
            System.out.println("Product " + name + " successfully updated");
            return true;
        } catch (SQLException e) {
            System.out.println("ERROR! Product " + name + "is NOT updated!");
            System.out.println(" >> " + e.getMessage());
            return false;
        }
    }

    public boolean addProduct(int id, String name, int section, int price ) {

        String sql = "INSERT INTO PRODUCTS (ID_P, NAME, ID_S, PRICE) " +
                "VALUES (" + id + ", '" + name + "' , '" + section + "' , '" + price + "')";
        try {
            statement.executeUpdate(sql);
            System.out.println("Product " + name + " successfully added");
            return true;
        } catch (SQLException e) {
            System.out.println("ERROR! Product " + name + " is NOT added!");
            System.out.println(" >> " + e.getMessage());
            return false;
        }
    }

    public void deleteSection(int id) {
        String sql = "DELETE FROM SECTIONS WHERE ID_S =" + id;
        try {
            int c = statement.executeUpdate(sql);
            if (c > 0) {
                System.out.println("Section with code "
                        + id + " successfully deleted!");
            } else {
                System.out.println("Section with code "
                        + id + " not found!");

            }
        } catch (SQLException e) {
            System.out.println("ERROR when deleting the section from identifier " + id);
                    System.out.println(" >> " + e.getMessage());
        }
    }

    public boolean deleteProduct(int id) {
        String sql = "DELETE FROM PRODUCTS WHERE ID_P =" + id;
        try {
            int c = statement.executeUpdate(sql);
            if (c > 0) {
                System.out.println("Product with code "
                        + id + " successfully deleted!");
                return true;
            } else {
                System.out.println("Product with code "
                        + id + " not found!");

                return false;
            }
        } catch (SQLException e) {
            System.out.println("ERROR when deleting the product from identifier " + id);
            System.out.println(" >> " + e.getMessage());
            return false;
        }
    }

    public void stop() throws SQLException {
        connection.close();
    }
    public static void main(String[] args) throws Exception {
        Shop shop = new Shop("jdbc:mysql://localhost:3306/shop", "root", "06102003");
        shop.showSections();
        shop.addSection(4, "ACCESSORIES");
        shop.addProduct(11, "HAT", 4, 300);
        shop.showProductInSection(4);
        shop.updateProduct(11, "WHITE HAT", 4, 350);
        shop.showProductInSection(4);
        shop.deleteSection(7);
        shop.stop();
    }
}
