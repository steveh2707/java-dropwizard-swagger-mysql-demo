package org.kainos.ea.db;

import java.sql.*;

public class CustomerDao {

    public boolean checkCustomerExists(int id) throws SQLException {
        Connection c = DatabaseConnector.getConnection();

//        String selectStatement = "SELECT CustomerID FROM Customer WHERE CustomerID = ?;";
//        PreparedStatement st = c.prepareStatement(selectStatement, Statement.RETURN_GENERATED_KEYS);
//
//        st.setInt(1, id);
//
//        st.executeUpdate();

//        ResultSet rs = st.getGeneratedKeys();

        Statement st = c.createStatement();
        ResultSet rs = st.executeQuery("SELECT CustomerID FROM Customer WHERE CustomerID = " + id);

        if (rs.next()) {
            return true;
        }

        return false;
    }
}
