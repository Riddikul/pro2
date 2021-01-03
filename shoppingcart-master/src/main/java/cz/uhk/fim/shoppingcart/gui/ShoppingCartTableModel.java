package cz.uhk.fim.shoppingcart.gui;
import cz.uhk.fim.shoppingcart.model.ShoppingCart;
import cz.uhk.fim.shoppingcart.model.ShoppingCartItem;
import javax.swing.table.AbstractTableModel;
import java.util.concurrent.ConcurrentHashMap;

public class ShoppingCartTableModel extends AbstractTableModel {
    private ShoppingCart shoppingCart;

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
        fireTableDataChanged();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex != 3;
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Název";
            case 1:
                return "Cena za kus";
            case 2:
                return "Počet kusů";
            case 3:
                return "Celková cena";
            case 4:
                return "Zakoupeno";
            case 5:
                return "English";
            default:
                return "?";
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
            case 5:
                return String.class;
            case 1:
            case 3:
                return Double.class;
            case 2:
                return Integer.class;
            case 4:
                return Boolean.class;
            default:
                return null;
        }
    }

    @Override
    public int getRowCount() {
        if (shoppingCart == null)
            return 0;
        else
            return shoppingCart.getCount();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    private ConcurrentHashMap<String, String> dictionary = new ConcurrentHashMap<>();

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ShoppingCartItem item = shoppingCart.getItem(rowIndex);
        switch (columnIndex) {
            case 0:
                return item.getTitle();
            case 1:
                return item.getPricePerPiece();
            case 2:
                return item.getNumberOfPieces();
            case 3:
                return item.getTotalPricePerProduct();
            case 4:
                return item.isPurchased();
            case 5:
                if (!dictionary.containsKey(item.getTitle())) {
                    new Thread(new TranslatorRunnable(dictionary, item.getTitle())).start();
                }
                return dictionary.get(item.getTitle());
            default:
                return "?";
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        ShoppingCartItem item = shoppingCart.getItem(rowIndex);
        switch (columnIndex) {
            case 0:
                item.setTitle((String) aValue);
                break;
            case 1:
                item.setPricePerPiece((Double) aValue);
                break;
            case 2:
                item.setNumberOfPieces((Integer) aValue);
                break;
            case 4:
                item.setPurchased((Boolean) aValue);
                break;
            default:
                break;
        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    public ConcurrentHashMap<String, String> getDictionary() {
        return dictionary;
    }
}
