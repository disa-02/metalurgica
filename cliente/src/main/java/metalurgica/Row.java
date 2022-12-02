package metalurgica;

public class Row {
    private Product product;
    private double price;
    private int amount;
    private String saleCode; //code del sale al que pertenece

    public Row() {

    }

    public String getSaleCode() {
        return saleCode;
    }

    public Row(Product product, double price, int amount, String saleCode) {
        this.product = product;
        this.price = price;
        this.amount = amount;
        this.saleCode = saleCode;
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
