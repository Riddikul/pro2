package cz.uhk.fim.shoppingcart.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cz.uhk.fim.shoppingcart.model.ShoppingCart;

import java.io.FileWriter;
import java.io.IOException;

public class JsonPersistence implements Persistence {
    String path;

    JsonPersistence(String path) {
        this.path = path;
    }

    @Override
    public void saveShoppingCart(ShoppingCart shoppingCart) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String json = gson.toJson(shoppingCart);

        try {
            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write(json);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public ShoppingCart openShoppingCart() {
        return null;
    }
}
