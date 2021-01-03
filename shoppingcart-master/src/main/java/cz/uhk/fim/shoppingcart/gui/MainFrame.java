package cz.uhk.fim.shoppingcart.gui;
import cz.uhk.fim.shoppingcart.model.ShoppingCart;
import cz.uhk.fim.shoppingcart.model.ShoppingCartItem;
import cz.uhk.fim.shoppingcart.persistence.JsonPersistenceFactory;
import cz.uhk.fim.shoppingcart.persistence.Persistence;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class MainFrame extends JFrame {
    private ShoppingCartTableModel model;
    JLabel lbl_notBought;
    JLabel lbl_totalPrice;
    ShoppingCart shoppingCart;
    JList list;
    ArrayList<String> loggedItems;
    JList<String> history;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public MainFrame() {
        setTitle("Shopping Cart");
        setSize(640, 480);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        history = new JList<>();
        lbl_totalPrice = new JLabel();
        lbl_notBought = new JLabel();

        initUi();
        shoppingCart = new ShoppingCart();
        addInitialItemsToCart();
        model.setShoppingCart(shoppingCart);
        loggedItems = new ArrayList<>();

        calculateAndSetTotalSum();
        calculateAndSetNonBoughtSum();

        model.addTableModelListener(e -> {
            calculateAndSetTotalSum();
            calculateAndSetNonBoughtSum();
            list.setModel(new ShoppingCartListModel(shoppingCart));
        });

        list.setModel(new ShoppingCartListModel(shoppingCart));
    }

    private void initUi() {
        JTabbedPane tabbedPane = new JTabbedPane();
        JPanel firstTabPanel = new JPanel(new BorderLayout());
        JPanel secondTabPanel = new JPanel();
        tabbedPane.addTab("Table", firstTabPanel);
        tabbedPane.addTab("List", secondTabPanel);

        add(tabbedPane, BorderLayout.CENTER);


        list = new JList();
        secondTabPanel.add(list);

        JPanel thirdTabPanel = new JPanel();
        tabbedPane.addTab("History", thirdTabPanel);
        thirdTabPanel.add(history);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));
        JLabel titleLabel = new JLabel("Název:");
        JLabel priceLabel = new JLabel("Cena/ks:");
        JLabel amountLabel = new JLabel("Počet kusů:");
        JTextField titleField = new JTextField();
        JTextField priceField = new JTextField();
        JSpinner amountField = new JSpinner();
        JButton submitButton = new JButton("Přidat");
        JLabel timeLabel = new JLabel("");
        JButton openButton = new JButton("Otevřít");
        JButton saveButton = new JButton("Uložit");

        inputPanel.add(titleLabel);
        inputPanel.add(titleField);
        inputPanel.add(priceLabel);
        inputPanel.add(priceField);
        inputPanel.add(amountLabel);
        inputPanel.add(amountField);
        inputPanel.add(submitButton);
        inputPanel.add(timeLabel);
        inputPanel.add(openButton);
        inputPanel.add(saveButton);

        firstTabPanel.add(inputPanel, BorderLayout.NORTH);


        submitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addItem(titleField.getText(), priceField.getText(),
                        (Integer) amountField.getValue());
                model.fireTableDataChanged();
            }
        });

        saveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(MainFrame.this);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    if (file.getAbsolutePath().endsWith("csv")) {
                        writeCsv(file);
                    } else {
                        JsonPersistenceFactory jsonPersistenceFactory = new JsonPersistenceFactory(file.getPath());
                        Persistence persistence = jsonPersistenceFactory.GetPersistence();
                        persistence.saveShoppingCart(shoppingCart);
                    }
                    loggedItems.add("Saved To " + file.getName() + " At " + LocalDateTime.now().format(formatter));
                    addHistory();
                }
            }
        });

        openButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(MainFrame.this);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    if (file.getAbsolutePath().endsWith("csv")) {
                        readCsv(file);
                        model.setShoppingCart(shoppingCart);
                    }
                    loggedItems.add("Opened " + file.getName() + " At " + LocalDateTime.now().format(formatter));
                    addHistory();
                }
            }
        });


        JTable table = new JTable();
        firstTabPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        model = new ShoppingCartTableModel();
        table.setModel(model);

        lbl_totalPrice = new JLabel();

        JPanel totalPricesPanel = new JPanel();
        totalPricesPanel.setLayout(new BoxLayout(totalPricesPanel, BoxLayout.X_AXIS));
        totalPricesPanel.add(lbl_totalPrice);
        totalPricesPanel.add(Box.createHorizontalGlue());
        totalPricesPanel.add(lbl_notBought);

        firstTabPanel.add(totalPricesPanel, BorderLayout.SOUTH);
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(100);
                    SwingUtilities.invokeLater(() -> timeLabel.setText(LocalDateTime.now().format(formatter)));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    private void addItem(String titleText, String priceText, int amount) {
        boolean added = true;
        double price = 1;
        try {
            price = Double.parseDouble(priceText);
        } catch (NumberFormatException e) {
            added = false;
            JOptionPane.showMessageDialog(this, "Enter price.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        if (titleText.trim().length() == 0) {
            added = false;
            JOptionPane.showMessageDialog(this,
                    "Enter name.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        if (added) {
            shoppingCart.addItem(new ShoppingCartItem(titleText, price, amount));
            loggedItems.add("Added " + titleText + " At " + LocalDateTime.now().format(formatter));
            addHistory();
        }
    }

    private void addInitialItemsToCart() {
        shoppingCart.addItem(new ShoppingCartItem("Rajcata",10,1));
        shoppingCart.addItem(new ShoppingCartItem("Pivo",20,5));
        shoppingCart.addItem(new ShoppingCartItem("Jablka", 7, 25));
        shoppingCart.addItem(new ShoppingCartItem("Rohlíky", 2, 32));
        shoppingCart.addItem(new ShoppingCartItem("Maso", 120, 18));
        shoppingCart.addItem(new ShoppingCartItem("Pizza", 65, 4));
        shoppingCart.addItem(new ShoppingCartItem("Bageta", 6, 5));
    }

    private void calculateAndSetTotalSum() {
        double sum = 0;
        for (ShoppingCartItem item : shoppingCart.getAllItems()) {
            sum += item.getNumberOfPieces() * item.getPricePerPiece();
        }
        lbl_totalPrice.setText("Total price: " + sum);
    }

    private void calculateAndSetNonBoughtSum() {
        double sum = 0;
        for (ShoppingCartItem item : shoppingCart.getAllItems()) {
            if (!item.isPurchased()) {
                sum += item.getNumberOfPieces() * item.getPricePerPiece();
            }
        }
        lbl_notBought.setText("Price of not bought items " + sum);
    }

    private void addHistory() {
        String[] test = new String[loggedItems.size()];
        loggedItems.toArray(test);
        history.setListData(test);
    }

    private void writeCsv(File file) {
        try {
            FileWriter csvWriter = new FileWriter(file);
            csvWriter.append("Název,");
            csvWriter.append("Cena za kus,");
            csvWriter.append("Počet Kusů,");
            csvWriter.append("Celková Cena,");
            csvWriter.append("Zakoupeno,");
            csvWriter.append("English\n");

            for (ShoppingCartItem item : shoppingCart.getAllItems()) {
                csvWriter.append(item.getTitle()).append(",");
                csvWriter.append(String.valueOf(item.getPricePerPiece())).append(",");
                csvWriter.append(String.valueOf(item.getNumberOfPieces())).append(",");
                csvWriter.append(String.valueOf(item.getTotalPricePerProduct())).append(",");
                csvWriter.append(String.valueOf(item.isPurchased())).append(",");
                csvWriter.append(model.getDictionary().get(item.getTitle())).append("\n");
            }
            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readCsv(File file) {
        try {
            Scanner reader = new Scanner(file);
            reader.nextLine();
            shoppingCart.getAllItems().clear();
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] values = line.split(",");
                String title = values[0];
                double pricePerPiece = Double.parseDouble(values[1]);
                int numberOfPieces = Integer.parseInt(values[2]);
                ShoppingCartItem item = new ShoppingCartItem(title, pricePerPiece, numberOfPieces);
                item.setPurchased(Boolean.parseBoolean(values[4]));
                shoppingCart.addItem(item);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
