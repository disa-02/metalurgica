package metalurgica;

import java.util.HashMap;
import java.util.Map;

import metalurgica.services.ProductService;
import metalurgica.services.RowService;
import metalurgica.services.SaleService;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        // La API ya posee un producto de ID 1, nombre "modificado" y stock 5.
        ProductStorage ps = ProductStorage.getInstance();
        Record r = Record.getInstance();

        Map<Product,Integer> map = new HashMap<>();
        Product p = ps.getProduct(1);
        
        // Vamos a hacer una venta de 3 unidades de "modificado".
        map.put(p, 3);
        ps.makeSale(map);

        // Finalmente podemos sincronizar la modificacion de Producto en la base de datos con el metodo "update"
        ProductService.update(p);

        // System.out.println("Rows");
        // System.out.println(RowService.get());
        // System.out.println();
        // System.out.println();
        // System.out.println("Sales");
        // System.out.println(SaleService.get());
        // System.out.println( "Hello World!" );
        //  System.out.println(ProductService.get());
        // ProductStorage.getInstance();
        // Record.getInstance().getSales();
        // Product p = new Product(1, "modificado", 0, 5);

        // ProductService.post(p);
    }
}
