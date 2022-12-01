package metalurgica;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductStorage {
    private ProductStorage ps = this; //no se si es asi
    private List<Product> products;
    private static ProductStorage instance = null;

    private ProductStorage() {

    }

    public static ProductStorage getInstance()
    {
        if (instance == null)
            instance = new ProductStorage();
  
        return instance;
    }

    public void save (Product p) {

    }

    public Product getProduct (int id) {
        for (Product product : this.products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    public void makeSale(Map<Product,Integer> mapa) {

        if ( mapa.keySet().stream().anyMatch(x -> mapa.get(x) > x.getAmount())){
            System.err.println("Se esta haciendo una orden de compra mayor al stock de un producto");
        }else {
            double price = 0.;
            ArrayList<Row> rows = new ArrayList<>();
            double total = 0.;
            for ( Product product : mapa.keySet()){
                rows.add( new Row(product, product.getPrice(), mapa.get(product)));
                product.subtractAmount(mapa.get(product));
                total =+ product.getAmount() * product.getPrice();
            }
            Sale sale = new Sale(Sale.getSaleCode(), total , Date.from(Instant.from(LocalDateTime.now())), rows);
            Record.getInstance().AddSales(sale);
        }

        // chequear lista de productos si hay stock

        // actualizar la lista

        // crear venta y rows y adjuntar al record
    }

    public void delete (int id) {

    }

    // public List<Product> getFiltered (Filter f) { //Pendiente crear clase Filter

    // }

}
