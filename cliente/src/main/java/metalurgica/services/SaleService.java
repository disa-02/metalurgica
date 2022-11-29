package metalurgica.services;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import org.json.JSONObject;
import java.text.SimpleDateFormat;  

import metalurgica.Product;
import metalurgica.Sale;

import org.json.JSONArray;  
public class SaleService {
    private static String urll = "http://localhost:8080/api/sales";
    public static List<Sale>  get(){
        List<Sale> sales = new ArrayList<>();

        try {

            URL url = new URL(urll);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            int responsecode = conn.getResponseCode();

            if (responsecode == 200) {
                String inline ="";
                //se obtiene el resultado de la request
                Scanner scanner = new Scanner(url.openStream());
                //se pasa a string
                while (scanner.hasNext()) {
                    inline += scanner.nextLine();
                }
                //Close the scanner
                scanner.close();

                //Tratamiento de los datos para crear los objetos
                JSONArray array = new JSONArray(inline);  

                for(int i=0; i < array.length(); i++)   
                {  
                    JSONObject object = array.getJSONObject(i);  

                    String salesCode = object.getString("saleCode");
                    // int seller = object.getInt("salesPerson");

                    double total = object.getDouble("total");
                    String dat = object.getString("date");
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = formatter.parse(dat);

                    Sale sale = new Sale(salesCode, total, date);
                    sales.add(sale);
                }  
            }
            
        }catch (Exception e) {
            e.printStackTrace();
        }
        return sales;
    }
}
