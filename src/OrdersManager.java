/*
 * Moti Azran
 */

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Represent the program main window
 */
public class OrdersManager extends JFrame {

    private final int BUTTONS_PANEL_WIDTH = 500;
    private final int BUTTONS_PANEL_HEIGHT = 50;
    private final int PRODUCT_PANEL_WIDTH = 500;
    private final int PRODUCT_PANEL_HEIGHT = 50;
    private Menu menu;
    private JPanel menuPanel;
    private JScrollPane productsScrollPane;
    private ArrayList<ProductPanel> productPanels;
    private JPanel buttonsPanel;
    private JButton orderButton;

    /**
     * Holds all options after pressing order
     */
    private enum DialogOption {
        Confirm,
        Change,
        Cancel
    }

    /**
     * Initialize the window
     * @param menuFilePath path to menu file
     * @throws FileNotFoundException if the menu file doesn't exists the exception thrown
     */
    public OrdersManager(String menuFilePath) throws FileNotFoundException {
        super("Orders Manager");
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        initButtonsPanel();

        menu = Menu.parseMenuFromFile(new Scanner(new File(menuFilePath)));

        initProductsPanel();

        int frameWidth = Math.max(BUTTONS_PANEL_WIDTH, PRODUCT_PANEL_WIDTH);
        int frameHeight = Math.min(BUTTONS_PANEL_HEIGHT + PRODUCT_PANEL_HEIGHT * productPanels.size(), 600);

        setSize(frameWidth, frameHeight);
    }

    /**
     * Init the buttons of the program
     */
    private void initButtonsPanel() {

        // Create panel for all buttons
        buttonsPanel = new JPanel();
        buttonsPanel.setPreferredSize(new Dimension(BUTTONS_PANEL_WIDTH, BUTTONS_PANEL_HEIGHT));

        // Create order button
        orderButton = new JButton("Order");
        orderButton.addActionListener(new OrderButtonListener());

        buttonsPanel.add(orderButton);
    }

    /**
     * Initialize the check box and the combo box
     */
    private void initProductsPanel()  {
        // Create panel for all product panels
        menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));

        ArrayList<Product> products = menu.getProducts();
        productPanels = new ArrayList<>();
        for (Product.MealType mealType : Product.MealType.values()) {
            // Add meal type label
            JPanel mealTypePanel = new JPanel();
            JLabel mealTypeLabel = new JLabel(mealType.name());
            mealTypePanel.add(mealTypeLabel);

            menuPanel.add(mealTypePanel);

            // Add all product of the current meal type
            for (Product p : products) {
                if (p.getMealType().equalsIgnoreCase(mealType.name())) {
                    ProductPanel panel = new ProductPanel(p, PRODUCT_PANEL_WIDTH, PRODUCT_PANEL_HEIGHT);
                    productPanels.add(panel);

                    menuPanel.add(panel);
                }
            }
        }

        productsScrollPane = new JScrollPane(menuPanel);

        getContentPane().add(productsScrollPane);
        add(buttonsPanel);
    }

    /**
     * Reset all products panels
     */
    private void resetPanels() {
        // Reset all product panels
        for (ProductPanel panel : productPanels) {
            panel.resetPanel();
        }
    }

    /**
     * Represent listener for the order button
     */
    private class OrderButtonListener implements ActionListener {

        /**
         * Initialize order and show order summary
         * @param e action information
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            // Init order by user selection
            Order order = initOrder();
            if (null == order) {
                return;
            }

            DialogOption chosenOption = showSummaryDialog(order);
            switch (chosenOption) {
                case Confirm:
                    try {
                        handleConfirm(order);
                    } catch (IOException exp) {
                        JOptionPane.showMessageDialog(null, "IO Error",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                case Change:
                    break;
                case Cancel:
                    handleCancel();
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid option",
                            "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        /**
         * Initialize user order
         * @return user chosen order
         */
        private Order initOrder() {
            Order order = new Order();

            // Add all chosen products to order
            for (ProductPanel panel : productPanels) {
                if (!panel.isChosen()) {
                    continue;
                }

                int productAmount = panel.getAmount();
                if (-1 == productAmount) {
                    // Invalid amount
                    JOptionPane.showMessageDialog(null,
                            String.format("Set amount for %s", panel.getProduct().getName()),
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return null;
                }

                for (int i = 0; i < productAmount; ++i) {
                    order.addProduct(panel.getProduct());
                }
            }

            if (order.isEmpty()) {
                // Empty order
                JOptionPane.showMessageDialog(null,
                        "Please select some products", "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }

            return order;
        }

        /**
         * Show order summary to user,
         * and let them choose what to do next
         * order, change order, cancel order
         * @param order the user order
         * @return the option that the user selected
         */
        private DialogOption showSummaryDialog(Order order) {
            String summary = order.getOrderSummary();

            // Show summary dialog
            int chosenOption = JOptionPane.showOptionDialog(null, summary,
                    "Order summary", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                    null, DialogOption.values(), DialogOption.Change);

            if (JOptionPane.CLOSED_OPTION == chosenOption) {
                return DialogOption.Change;
            }

            return DialogOption.values()[chosenOption];
        }

        /**
         * Write the user order to file
         * named as the user input (name and ID)
         * @param order user order
         * @throws IOException if there is a file error the exception thrown
         */
        private void handleConfirm(Order order) throws IOException {
            String fileName = "";

            do {
                fileName = JOptionPane.showInputDialog(null,
                        "Enter name with ID (no space): ");
                if (null == fileName) {
                    // The user canceled
                    return;
                }
            } while(fileName.isEmpty());

            // Write order summary to client file
            FileWriter writer = new FileWriter(fileName + ".txt");
            writer.write(order.getOrderSummary());
            writer.close();

            resetPanels();
        }

        /**
         * Cancel order, reset all products
         * choices and products amount choices
         */
        private void handleCancel() {
            resetPanels();
        }
    }
}
