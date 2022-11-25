package main;

import java.util.List;

public class Product {
    private int id;
    private String name;
    private double price;
    private int amount;
    private List<Material> conformation;



    public Product() {

    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public double getPrice() {
        return this.price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

}