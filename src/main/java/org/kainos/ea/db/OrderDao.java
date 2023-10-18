package org.kainos.ea.db;

import org.kainos.ea.cli.Order;
import org.kainos.ea.cli.OrderRequest;
import org.kainos.ea.cli.Product;
import org.kainos.ea.cli.ProductRequest;

import java.io.FileInputStream;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;



public class OrderDao {

    public List<Order> getAllOrders() throws SQLException {
        Connection c = DatabaseConnector.getConnection();

        Statement st = c.createStatement();

        ResultSet rs = st.executeQuery("SELECT OrderID, CustomerID, OrderDate, DispatchDate, ArrivedDate FROM `Order`;");

        List<Order> orderList = new ArrayList<>();

        while (rs.next()) {
//            LocalDate birthDate = rs.getDate("birth_date").toLocalDate();
            Order order = new Order(
                    rs.getInt("OrderID"),
                    rs.getInt("CustomerID"),
                    rs.getTimestamp("OrderDate"),
                    rs.getDate("DispatchDate"),
                    rs.getDate("ArrivedDate")
            );

            orderList.add(order);
        }

        return orderList;
    }

    public Order getOrderById(int id) throws SQLException {
        Connection c = DatabaseConnector.getConnection();
        Statement st = c.createStatement();

        ResultSet rs = st.executeQuery("SELECT OrderID, CustomerID, OrderDate, DispatchDate, ArrivedDate FROM `Order` WHERE OrderID=" + id);

        while (rs.next()) {
            return new Order(
                    rs.getInt("OrderID"),
                    rs.getInt("CustomerID"),
                    rs.getTimestamp("OrderDate"),
                    rs.getDate("DispatchDate"),
                    rs.getDate("ArrivedDate")
            );
        }

        return null;
    }


    public int createOrder(OrderRequest order) throws SQLException {
        Connection c = DatabaseConnector.getConnection();

        String insertStatement = "INSERT INTO `Order` (CustomerID, OrderDate) VALUES (?, ?);";
        PreparedStatement st = c.prepareStatement(insertStatement, Statement.RETURN_GENERATED_KEYS);

        st.setInt(1, order.getCustomerId());
        st.setTimestamp(2, order.getOrderDate());

        st.executeUpdate();
        ResultSet rs = st.getGeneratedKeys();

        if (rs.next()) {
            return rs.getInt(1);
        }

        return -1;
    }

    public void updateOrder(int id, OrderRequest order) throws SQLException {
        Connection c = DatabaseConnector.getConnection();

        String updateStatement = "UPDATE `Order` SET CustomerID = ?, OrderDate = ? WHERE OrderID = ?;";
        PreparedStatement st = c.prepareStatement(updateStatement);

        st.setInt(1, order.getCustomerId());
        st.setTimestamp(2, order.getOrderDate());
        st.setInt(3, id);

        st.executeUpdate();
    }

    public void deleteOrder(int id) throws SQLException {
        Connection c = DatabaseConnector.getConnection();

        String deleteStatement = "DELETE FROM `Order` WHERE OrderID = ?";
        PreparedStatement st = c.prepareStatement(deleteStatement);

        st.setInt(1,id);
        st.executeUpdate();
    }
}
