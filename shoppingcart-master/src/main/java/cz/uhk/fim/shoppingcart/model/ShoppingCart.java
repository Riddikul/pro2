package cz.uhk.fim.shoppingcart.model;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart
{
    private List<ShoppingCartItem> itemList = new ArrayList<>();

    public ShoppingCartItem getItem(int index) {
        return itemList.get(index);
    }

    public void addItem(ShoppingCartItem item) {
        itemList.add(item);
    }

    public void removeItem(int index) {
        itemList.remove(index);
    }

    public void removeItem(ShoppingCartItem item) {
        itemList.remove(item);
    }

    public int getCount() {
        return itemList.size();
    }

    public List<ShoppingCartItem> getAllItems() {
        return itemList;
    }
}
