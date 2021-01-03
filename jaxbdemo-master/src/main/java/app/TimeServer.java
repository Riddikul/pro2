package app;

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
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
public class TimeServer
{
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(80);
        while(true)
        {
            Socket socket = serverSocket.accept();
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                        String firstLine =bufferedReader.readLine();
                        System.out.println(firstLine);
                        URI url = new URI(firstLine.split(" ")[1]);
                        List<NameValuePair> params = URLEncodedUtils.parse(url, Charset.forName("UTF-8"));

                        PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
                        String body = LocalTime.now(ZoneId.of(params.get(0).getValue())).toString();
                        printWriter.println("HTTP/1.1 200 OK");
                        printWriter.println("Content-type: text/html");
                        printWriter.println("Content-length: " + body.length());
                        printWriter.println();
                        printWriter.println(body);
                        printWriter.flush();
                        socket.close();
                    } catch (IOException | URISyntaxException e) {
                    }
                }
            };
            thread.start();
        }
    }
}
