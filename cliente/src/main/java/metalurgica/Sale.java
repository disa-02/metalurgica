
package metalurgica;

import java.util.Date;
import java.util.List;

public class Sale {
    private int salesCode;
    private double total;
    private int seller; //probablemente no se necesite
    private Date date;
    private List<Row> rows;

    public Sale() {

    }

    public int getCode() {
        return this.salesCode;
    }

    public double getTotal() {
        return this.total;
    }

    public Date getDate() {
        return this.date;
    }

    public List<Row> getRows() {
        return this.rows;
    }




    
}
