
package metalurgica;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Sale {
    private String salesCode;
    private double total;
    private int seller; //probablemente no se necesite
    private Date date;
    private List<Row> rows;
    private static int SALECODE;

    public Sale() {

    }

    public Sale(String salesCode, double total, Date date, ArrayList<Row> rows) {
        this.salesCode = salesCode;
        this.total = total;
        this.date = date;
        seller = -1;
        this.rows = rows;
    }

    public Sale(String salesCode, double total, Date date) {
        this.salesCode = salesCode;
        this.total = total;
        this.date = date;
        seller = -1;
        this.rows = new ArrayList();
    }

    public static String getSaleCode() {
        return String.valueOf(SALECODE++);
    }
    public String getCode() {
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

    public String getSalesCode() {
        return this.salesCode;
    }

    public void addRow(Row row){
        rows.add(row);
    }

    @Override
    public String toString() {
        return "Sale [salesCode=" + salesCode + ", total=" + total + ", seller=" + seller + ", date=" + date + ", rows="
                + rows + "]";
    }





    
}
