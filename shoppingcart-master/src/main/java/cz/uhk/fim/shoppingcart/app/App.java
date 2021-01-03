package cz.uhk.fim.shoppingcart.app;

import cz.uhk.fim.shoppingcart.gui.MainFrame;

import javax.swing.*;

/**
 * Class App for running our application
 */
public class App {
    /**
     * Main method
     * @param args passed arguments
     */
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame();
            }
        });
    }
}
