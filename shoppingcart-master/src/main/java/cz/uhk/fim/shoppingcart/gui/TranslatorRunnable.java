package cz.uhk.fim.shoppingcart.gui;
import com.google.gson.Gson;
import cz.uhk.fim.shoppingcart.translate.TranslatedWord;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentHashMap;

public class TranslatorRunnable implements Runnable {
    private final ConcurrentHashMap<String, String> dictionary;
    private final String cz;

    public TranslatorRunnable(ConcurrentHashMap<String, String> dictionary, String cz) {
        this.dictionary = dictionary;
        this.cz = cz;
    }

    @Override
    public void run() {
        translate(cz);
    }

    private void translate(String cz) {
        try {
            URL url = new URL("https://api.itranslate.com/translate/v1");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");

            String jsonInputString = "{\"key\": \"83fcbf65-1d2c-4051-b37f-5935e8fc7768\", \"source\": {\"dialect\": \"cs\", \"text\": \"" + cz + "\"}, \"target\": {\"dialect\": \"en\"}}";

            OutputStream os = conn.getOutputStream();
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8)
            );

            StringBuilder response = new StringBuilder();
            String responseLine;

            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            TranslatedWord translatedWord = new Gson().fromJson(response.toString(), TranslatedWord.class);
            dictionary.put(cz, translatedWord.target.text);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
