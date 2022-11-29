package metalurgica;

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
        System.out.println("Rows");
        System.out.println(RowService.get());
        System.out.println();
        System.out.println();
        System.out.println("Sales");
        System.out.println(SaleService.get());
        System.out.println( "Hello World!" );
    }
}
