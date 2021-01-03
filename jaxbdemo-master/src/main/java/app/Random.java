package app;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Random {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String count;
        String min;
        String max;


            System.out.println("Počet čísel:");
            count = scanner.nextLine();

            System.out.println("Nejmenší hodnota čísla:");
            min = scanner.nextLine();

            System.out.println("Největší hodnota čísla:");
            max = scanner.nextLine();


        String url = "http://www.randomnumberapi.com/api/v1.0/random?min=" + min + "&max=" + max + "&count=" + count;
        String outJson = ReadFromURL(url);
        System.out.println(outJson);

        int[] out = gson.fromJson(outJson, new TypeToken<int[]>(){}.getType());
        int maxGen = out[0];
        int minGen = out[0];

        for(int i = 0; i < out.length; i++) {
            if (maxGen < out[i])
                maxGen = out[i];
            if (minGen > out[i])
                minGen = out[i];
        }
        System.out.println("Nejmenší číslo: " + minGen);
        System.out.println("Největší číslo: " + maxGen);
    }

    private static String ReadFromURL(String urlIn) throws IOException {
        URL url = new URL(urlIn);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/xml");
        if (connection.getResponseCode() != 200)
            throw new RuntimeException("Failed: HTTP Error code: " + connection.getResponseCode());

        Scanner scanner = new Scanner(connection.getInputStream()).useDelimiter("\\A");
        return scanner.hasNext() ? scanner.next() : "";
    }
}
