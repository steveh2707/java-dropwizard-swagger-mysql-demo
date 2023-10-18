package org.kainos.ea.cli;

public class Product implements Comparable<Product> {
    private int productId;
    private String name;
    private String description;

    public Product(int productId, String name, String description, double price) {
        this.setProductId(productId);
        this.setName(name);
        this.setDescription(description);
        this.setPrice(price);
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    private double price;


    @Override
    public int compareTo(Product o) {
        return Double.compare(this.getPrice(), o.getPrice());
    }

    @Override
    public String toString() {
        return "Product name: " + this.getName() + ", Product Price: " + this.getPrice();
    }
}
