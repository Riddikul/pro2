package cz.uhk.fim.shoppingcart.persistence;

import cz.uhk.fim.shoppingcart.model.ShoppingCart;

public interface Persistence
{
    void saveShoppingCart(ShoppingCart shoppingCart);
    ShoppingCart openShoppingCart();
}
