package org.kainos.ea.api;

import org.kainos.ea.cli.Order;
import org.kainos.ea.cli.Product;
import org.kainos.ea.cli.ProductRequest;
import org.kainos.ea.client.*;
import org.kainos.ea.core.ProductValidator;
import org.kainos.ea.db.ProductDao;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class ProductService {
    private final ProductDao productDao = new ProductDao();
    private ProductValidator productValidator = new ProductValidator();

    public List<Product> getAllProducts() throws FailedToGetProductsException {

        try {
            List<Product> productList = productDao.getAllProducts();

            return productList;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new FailedToGetProductsException();
        }
    }

    public Product getProductById(int id) throws FailedToGetProductException, ProductDoesNotExistException {

        try {
            Product product = productDao.getProductById(id);

            if (product == null) {
                throw new ProductDoesNotExistException();
            }
            return product;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new FailedToGetProductException();
        }
    }

    public int createProduct(ProductRequest product) throws FailedToCreateProductException, InvalidProductException {
        try {
            String validationMessage = productValidator.isValidProduct(product);

            if (validationMessage != null) {
                throw new InvalidProductException(validationMessage);
            }

            int id = productDao.createProduct(product);

            if (id == -1) {
                throw new FailedToCreateProductException();
            }

            return id;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new FailedToCreateProductException();
        }
    }

    public void updateProduct(int id, ProductRequest product) throws ProductDoesNotExistException, FailedToCreateProductException, InvalidProductException {
        try {
            String validationMessage = productValidator.isValidProduct(product);

            if (validationMessage != null) {
                throw new InvalidProductException(validationMessage);
            }

            Product productToBeUpdated = productDao.getProductById(id);

            if (productToBeUpdated == null) {
                throw new ProductDoesNotExistException();
            }

            productDao.updateProduct(id, product);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new FailedToCreateProductException();
        }
    }

    public void deleteProduct(int id) throws ProductDoesNotExistException, GenericSQLException {
        try {
            Product productToBeDeleted = productDao.getProductById(id);

            if (productToBeDeleted == null) {
                throw new ProductDoesNotExistException();
            }

            productDao.deleteProduct(id);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new GenericSQLException("Failed to delete product");
        }
    }

}
