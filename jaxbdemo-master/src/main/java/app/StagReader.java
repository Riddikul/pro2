package app;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Rozvrh;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class StagReader {
    public static void main(String[] args) throws IOException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String url = "https://stag-demo.uhk.cz/ws/services/rest2/rozvrhy/getRozvrhByMistnost?semestr=ZS&budova=J&mistnost=J1&outputFormat=json";
        String json = ReadFromURL(url);
        System.out.println(json);
        Rozvrh rozvrh = gson.fromJson(json, new TypeToken<Rozvrh>(){}.getType());
    }

    private static String ReadFromURL(String url) throws IOException {
        URL url1 = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP Error code : "
                    + conn.getResponseCode());
        }
        java.util.Scanner s = new java.util.Scanner(conn.getInputStream()).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }


}
