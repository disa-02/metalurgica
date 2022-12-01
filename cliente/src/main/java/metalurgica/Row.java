package metalurgica;

public class Row {
    private Product product;
    private double price;
    private double subtotal;
    private int amount;

    public Row() {

    }

    public Row(Product product, double price, int amount) {
        this.product = product;
        this.price = price;
        this.amount = amount;
        this.subtotal = amount * price;
    }

    public Product getProduct() {
        return this.product;
    }

    public double getPrice() {
        return this.price;
    }

    public int getAmount() {
        return this.amount;
    }

    @Override
    public String toString() {
        return "Row [product=" + product + ", price=" + price + ", amount=" + amount + "]";
    }



    
}
