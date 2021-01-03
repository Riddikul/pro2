package cz.uhk.fim.shoppingcart.gui;

import cz.uhk.fim.shoppingcart.model.ShoppingCart;
import cz.uhk.fim.shoppingcart.model.ShoppingCartItem;

import javax.swing.*;

public class ShoppingCartListModel extends AbstractListModel
{
    ShoppingCart shoppingCart;

    public ShoppingCartListModel(ShoppingCart shoppingCart)
    {
        this.shoppingCart = shoppingCart;
    }

    @Override
    public int getSize()
    {
        return shoppingCart.getCount();
    }

    @Override
    public Object getElementAt(int index)
    {
        ShoppingCartItem item = shoppingCart.getItem(index);
        return item.getTitle() + "("+item.getNumberOfPieces()+")";
    }
}
