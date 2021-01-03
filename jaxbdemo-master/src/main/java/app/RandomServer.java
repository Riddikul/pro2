package app;

import com.google.gson.Gson;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.net.URLEncodedUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Random;

public class RandomServer {
    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(80);

        while(true)
        {
            Socket socket = serverSocket.accept();

            Thread thread = new Thread(() -> {
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String firstLine = bufferedReader.readLine();
                    System.out.println(firstLine);
                    URI url = new URI(firstLine.split(" ")[1]);
                    List<NameValuePair> params = URLEncodedUtils.parse(url, Charset.forName("UTF-8"));


                    PrintWriter printWriter = new PrintWriter(socket.getOutputStream());

                    Random random = new Random();

                    int min = Integer.parseInt(params.get(0).getValue());
                    int max = Integer.parseInt(params.get(1).getValue());
                    int count = Integer.parseInt(params.get(2).getValue());

                    System.out.println("Min " + min);
                    System.out.println("Max " + max);
                    System.out.println("Count " + count);

                    int poleRandom[] = new int[count];
                    for (int i = 0; i < count; i++) {
                        int rand = random.nextInt(max);

                        if (rand < min) {
                            rand = random.nextInt(max);
                        }
                        rand++;
                        poleRandom[i] = rand;
                    }

                    for (int i = 0; i < count; i++) {
                        System.out.println(poleRandom[i]);
                    }

                    Gson gson = new Gson();
                    String vypis = gson.toJson(poleRandom);

                    printWriter.println("HTTP/1.1 200 OK");
                    printWriter.println("Content-type: text/html");
                    printWriter.println("Content-lenght: " + vypis.length());
                    printWriter.println(""); //prázdný řádek - důležité
                    printWriter.println(vypis);

                    System.out.println(vypis);

                    printWriter.flush();

                    socket.close();

                }

                catch(IOException | URISyntaxException e){
                    e.printStackTrace();
                }

            });
            thread.start();
        }

    }


}
