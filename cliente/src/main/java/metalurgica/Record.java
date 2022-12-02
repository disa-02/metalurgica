package metalurgica;

import java.util.List;

import metalurgica.services.RowService;
import metalurgica.services.SaleService;

import java.util.Date;

public class Record {
    private List<Sale> sales;
    private static Record instance = null;

    private Record() {
        match();
    }

    public static Record getInstance()
    {
        if (instance == null)
            instance = new Record();
  
        return instance;
    }

    public List<Sale> getSales(Date date) { //Para recuperar las ventas DESDE una fecha
        return null;
    }
    public void AddSales(Sale s) {
        sales.add(s);
    }

    public List<Sale> getSales() { //Para recuperar TODAS las ventas
        return this.sales;
    }

    public List<Row> getSalesHistory() {
        return null;
    }

    private void match(){
        List<Row> rows = RowService.get();
        List<Sale> sales = SaleService.get();
        for(Row row: rows){
            for(Sale sale: sales){
                if(row.getSaleCode().equals(sale.getSalesCode())){
                    sale.addRow(row);
                    break;
                }
            }
        }
        this.sales = sales;
    }
}
