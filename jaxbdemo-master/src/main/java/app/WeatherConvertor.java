package app;

import com.google.gson.*;
import model.*;
import model.xml.Weather;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class WeatherConvertor {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Weather.class);

            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            System.out.println("Cesta k souboru: ");
            String path = scanner.nextLine();
            Weather weather = (Weather) unmarshaller.unmarshal(new File(path));


            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
            marshaller.marshal(convert(weather), new File(  "WeatherCoverted.xml"));

            try {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                Writer writer = Files.newBufferedWriter(Paths.get("WeatherConvertedJSON.json"));
                gson.toJson(weather, writer);
                writer.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    private static Weather convert (Weather weather) {
        weather.getTemperature().toCelsius();
        weather.getWind().toKmh();
        return weather;
    }
}
