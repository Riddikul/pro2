package cz.uhk.fim.shoppingcart.model;

public class ShoppingCartItem {
    private String title;
    private double pricePerPiece;
    private int numberOfPieces;
    private double totalPricePerProduct;
    private boolean isPurchased;


    public ShoppingCartItem() {
    }

    public ShoppingCartItem(String title, double pricePerPiece, int numberOfPieces) {
        this.title = title;
        this.pricePerPiece = pricePerPiece;
        this.numberOfPieces = numberOfPieces;
        totalPricePerProduct = pricePerPiece * numberOfPieces;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPricePerPiece() {
        return pricePerPiece;
    }

    public void setPricePerPiece(double pricePerPiece) {
        this.pricePerPiece = pricePerPiece;
    }

    public int getNumberOfPieces() {
        return numberOfPieces;
    }

    public void setNumberOfPieces(int numberOfPieces) {
        this.numberOfPieces = numberOfPieces;
    }

    public boolean isPurchased() {
        return isPurchased;
    }

    public void setPurchased(boolean purchased) {
        isPurchased = purchased;
    }

    public double getTotalPricePerProduct() {
        return totalPricePerProduct;
    }
}
