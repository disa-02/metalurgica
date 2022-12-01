package metalurgica;

import java.util.List;
import java.util.Map;

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

        mapa.keySet().stream().anyMatch()
        // chequear lista de productos si hay stock

        // actualizar la lista

        // crear venta y rows y adjuntar al record
    }

    public void delete (int id) {

    }

    // public List<Product> getFiltered (Filter f) { //Pendiente crear clase Filter

    // }

}
