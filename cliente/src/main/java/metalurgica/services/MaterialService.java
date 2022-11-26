package metalurgica.services;

import metalurgica.Material;
import org.json.JSONArray;
import org.json.JSONObject;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MaterialService {

    private static String urll = "http://localhost:8080/api/materials";
    public static List<Material> get(){
        List<Material> materials = new ArrayList<>();
        try {
            //Crear la url
            URL url = new URL(urll);
            //Conectar la url y enviar el get
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            //respuesta
            int responsecode = conn.getResponseCode();
            //Si fue exitosa
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
                    String materialString = object.get("material").toString();
                    Material material = new Material();
                    if(!materialString.equals("null")){
                        JSONObject productJson = new JSONObject(materialString);
                        int id = productJson.getInt("id");
                        String name = productJson.getString("name");
                        double price = productJson.getDouble("Price");
                        int amount = productJson.getInt("stock");
                        material = new Material(id, name, price, amount);
                    }
                    materials.add(material);
                }
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
        return materials;
    }
}

