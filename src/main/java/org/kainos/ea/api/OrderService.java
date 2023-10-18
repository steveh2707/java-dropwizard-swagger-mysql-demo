package org.kainos.ea.api;

import org.checkerframework.checker.units.qual.C;
import org.kainos.ea.cli.Order;
import org.kainos.ea.cli.OrderRequest;
import org.kainos.ea.cli.Product;
import org.kainos.ea.cli.ProductRequest;
import org.kainos.ea.client.*;
import org.kainos.ea.core.OrderValidator;
import org.kainos.ea.db.CustomerDao;
import org.kainos.ea.db.OrderDao;

import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;
import java.sql.Date;

public class OrderService {
    private final OrderDao orderDao = new OrderDao();
    private final CustomerDao customerDao = new CustomerDao();
    private final OrderValidator orderValidator = new OrderValidator();

    public List<Order> getAllOrders() throws FailedToGetOrdersException {

        try {
            List<Order> orderList = orderDao.getAllOrders();

            Map<Integer, Long> map = orderList
                    .stream()
                    .collect(
                            Collectors.groupingBy(
                                            Order::getCustomerId,
                                            Collectors.counting()
                                    )
                    );
            System.out.println(map);

            System.out.println("CustomerID with most orders: "+Collections.max(map.entrySet(), Map.Entry.comparingByValue()).getKey());
            System.out.println("CustomerID with least orders: "+Collections.min(map.entrySet(), Map.Entry.comparingByValue()).getKey());

            return orderList;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new FailedToGetOrdersException();
        }
    }

    public Order getOrderById(int id) throws FailedToGetOrderException, OrderDoesNotExistException {

        try {
            Order order = orderDao.getOrderById(id);

            if (order == null) {
                throw new OrderDoesNotExistException();
            }
            return order;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new FailedToGetOrderException();
        }
    }

    public int createOrder(OrderRequest order) throws FailedToCreateOrderException, InvalidOrderException, GenericFailedException {
        try {
            String validationMessage = orderValidator.isValidOrder(order);
            if (validationMessage != null) {
                throw new InvalidOrderException(validationMessage);
            }

            if (!customerDao.checkCustomerExists(order.getCustomerId())) {
                throw new GenericFailedException("Customer does not exist");
            }

            int id = orderDao.createOrder(order);

            if (id == -1) {
                throw new FailedToCreateOrderException();
            }

            return id;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new FailedToCreateOrderException();
        }
    }

    public void updateOrder(int id, OrderRequest order) throws InvalidOrderException, GenericFailedException, FailedToCreateOrderException {
        try {
            String validationMessage = orderValidator.isValidOrder(order);
            if (validationMessage != null) {
                throw new InvalidOrderException(validationMessage);
            }

            Order orderToBeUpdated = orderDao.getOrderById(id);

            if (orderToBeUpdated == null) {
                throw new GenericFailedException("Order does not exist");
            }

            orderDao.updateOrder(id, order);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new FailedToCreateOrderException();
        }
    }


    public void deleteOrder(int id) throws OrderDoesNotExistException, GenericSQLException {
        try {
            Order orderToBeDeleted = orderDao.getOrderById(id);

            if (orderToBeDeleted == null) {
                throw new OrderDoesNotExistException();
            }

            orderDao.deleteOrder(id);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new GenericSQLException("Failed to delete order");
        }
    }
}
