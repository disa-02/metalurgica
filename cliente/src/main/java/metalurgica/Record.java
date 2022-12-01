package metalurgica;

import java.util.List;

import metalurgica.services.SaleService;

import java.util.Date;

public class Record {
    private List<Sale> sales;
    private static Record instance = null;

    private Record() {
        
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
        return null;
    }

    public List<Row> getSalesHistory() {
        return null;
    }
}
