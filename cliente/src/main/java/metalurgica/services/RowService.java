package metalurgica.services;


import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.json.JSONObject;

import metalurgica.Product;
import metalurgica.Row;

import org.json.JSONArray;  


public class RowService {

    private static String urll = "http://localhost:8080/api/roows";
    public static List<Row>  get(){
        List<Row> rows = new ArrayList<>();

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
                    String productString = object.get("product").toString();
                    Product product = new Product();
                    if(!productString.equals("null")){
                        JSONObject productJson = new JSONObject(productString);
                        int id = productJson.getInt("id");
                        String name = productJson.getString("name");
                        Double price = productJson.getDouble("sellPrice");
                        int amount = productJson.getInt("stock");
                        product = new Product(id, name, price, amount);
                    }
                    Double price = object.getDouble("subTotal");
                    int amount = object.getInt("amountProduct");
                    String saleString = object.get("sale").toString();
                    String saleCode ;
                    if(saleString.equals("null"))
                        saleCode = " ";
                    else{
                        JSONObject productJson = new JSONObject(saleString);

                        saleCode = productJson.getString("saleCode");

                    }
                    Row row = new Row(product,price,amount);

                    rows.add(row);
                }  
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return rows;
    }
}