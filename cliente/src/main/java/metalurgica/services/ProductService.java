package metalurgica.services;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import metalurgica.Product;

public class ProductService {
    private static String urlGet = "http://localhost:8080/api/products";
    private static String urlPost = "http://localhost:8080/api/products";


    public static List<Product>  get(){

        List<Product> products = new ArrayList<>();

        try {

            URL url = new URL(urlGet);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            int responsecode = conn.getResponseCode();
            if (responsecode == 200) {
                String inline ="";
                Scanner scanner = new Scanner(url.openStream());

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
                     int id = object.getInt("id");
                     String name = object.getString("name");
                     double price = object.getDouble("sellPrice");
                     int amount = object.getInt("stock");
                     Product product = new Product(id, name, price, amount);
                     products.add(product);
                 }
            }
        }catch (Exception e) {
            e.printStackTrace();
        } 
        return products;  
    }

    public static void post(Product product){
        JSONObject pJson = product.getJson();
        try {

            URL url = new URL(urlGet + "/1");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            //conn.setDoInput(true);
  conn.setRequestProperty("User-Agent", "Mozilla/5.0");
  conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
  conn.setRequestProperty("Content-Type","application/json");
 
            conn.setDoOutput(true);
            
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.writeBytes(pJson.toString());
            wr.flush();
            wr.close();

            conn.connect();
            int responsecode = conn.getResponseCode();
            System.out.println(responsecode);
    }catch (Exception e) {
        e.printStackTrace();
    } 
}
}
