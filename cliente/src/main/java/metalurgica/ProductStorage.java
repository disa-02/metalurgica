package metalurgica;

import java.util.List;

public class ProductStorage {
    private ProductStorage ps = this; //no se si es asi
    private List<Product> products;

    public ProductStorage() {

    }

    public ProductStorage getInstance() {
        return this; //no se si es asi
    }

    public void save (Product p) {

    }

    public Product getProduct (int id) {

        return null;
    }

    public void delete (int id) {

    }

    // public List<Product> getFiltered (Filter f) { //Pendiente crear clase Filter

    // }

}
