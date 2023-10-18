package org.kainos.ea.core;

import org.kainos.ea.cli.OrderRequest;
import org.kainos.ea.cli.ProductRequest;
import org.kainos.ea.client.GenericFailedException;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;

public class OrderValidator {

    public String isValidOrder(OrderRequest order) {
        if (order.getCustomerId() < 1) {
            return "Customer ID can't be less than 1";
        }

        Timestamp yearAgo = Timestamp.from( Instant.now().minus(Duration.ofDays(365)) );

        if (order.getOrderDate().before( yearAgo )) {
            return "Order date cannot be more than a year ago";
        }

        return null;
    }
}
