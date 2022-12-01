package metalurgica;

import java.util.List;

public class Product {
    private int id;
    private String name;
    private double price;
    private int amount;
    private List<Material> conformation;



   

    public Product(int id, String name, double price, int amount) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.amount = amount;
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
    public double getAmount() { return this.amount;}

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
    public void subtractAmount(int amount) {
        this.amount =- amount;
    }

    @Override
    public String toString() {
        return "Product [id=" + id + ", name=" + name + ", price=" + price + ", amount=" + amount + "]";
    }
    

}