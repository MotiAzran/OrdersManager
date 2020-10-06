package com.moti;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class OrdersManager extends JFrame {

    private final int BUTTONS_PANEL_WIDTH = 500;
    private final int BUTTONS_PANEL_HEIGHT = 50;
    private Menu _menu;
    private JPanel _menu_panel;
    private JScrollPane _products_scroll_pane;
    private ArrayList<ProductPanel> _product_panels;
    private JPanel _buttons_panel;
    private JButton _order_button;

    private enum DialogOption {
        Confirm,
        Change,
        Cancel
    }

    public OrdersManager(String menu_file_path) throws FileNotFoundException {
        super("Orders manager");
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        _init_buttons_panel();

        _menu = Menu.parse_menu_from_file(new Scanner(new File(menu_file_path)));

        _init_products_panel();

        int frame_width = Math.max(BUTTONS_PANEL_WIDTH, ProductPanel.PANEL_WIDTH);
        int frame_height = Math.min(BUTTONS_PANEL_HEIGHT + ProductPanel.PANEL_HEIGHT * _product_panels.size(), 600);

        setSize(frame_width, frame_height);
    }

    private void _init_buttons_panel() {

        // Create panel for all buttons
        _buttons_panel = new JPanel();
        _buttons_panel.setPreferredSize(new Dimension(BUTTONS_PANEL_WIDTH, BUTTONS_PANEL_HEIGHT));

        // Create order button
        _order_button = new JButton("Order");
        _order_button.addActionListener(new OrderButtonListener());

        _buttons_panel.add(_order_button);

    }

    private void _init_products_panel()  {

        // Create panel for all product panels
        _menu_panel = new JPanel();
        _menu_panel.setLayout(new BoxLayout(_menu_panel, BoxLayout.Y_AXIS));

        ArrayList<Product> products = _menu.get_products();
        _product_panels = new ArrayList<>();
        for (Product.MealType meal_type : Product.MealType.values()) {
            // Get all products of the current meal type
            List<Product> current_products = products.stream().filter(
                    p -> p.get_meal_type().equalsIgnoreCase(meal_type.name())).collect(Collectors.toList());

            // Add meal type label
            JPanel meal_type_panel = new JPanel();
            JLabel meal_type_label = new JLabel(meal_type.name());
            meal_type_panel.add(meal_type_label);

            _menu_panel.add(meal_type_panel);

            // Add all product of the current meal type
            for (Product p : current_products) {
                ProductPanel panel = new ProductPanel(p);
                _product_panels.add(panel);

                _menu_panel.add(panel);
            }
        }

        _products_scroll_pane = new JScrollPane(_menu_panel);

        getContentPane().add(_products_scroll_pane);
        add(_buttons_panel);
    }

    private void reset_panels() {

        // Reset all product panels
        for (ProductPanel panel : _product_panels) {
            panel.reset_panel();
        }
    }

    private class OrderButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            // Init order by user selection
            Order order = _init_order();
            if (null == order) {
                return;
            }

            DialogOption chosen_option = _show_summary_dialog(order);
            switch (chosen_option) {
                case Confirm:
                    try {
                        _handle_confirm(order);
                    } catch (IOException exp) {
                        JOptionPane.showMessageDialog(null, "IO Error",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                case Change:
                    break;
                case Cancel:
                    _handle_cancel();
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid option",
                            "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        private Order _init_order() {

            Order order = new Order();

            // Add all chosen products to order
            for (ProductPanel panel : _product_panels) {
                if (!panel.is_chosen()) {
                    continue;
                }

                int product_amount = panel.get_amount();
                if (-1 == product_amount) {
                    // Invalid amount
                    JOptionPane.showMessageDialog(null,
                            String.format("Set amount for %s", panel.get_product().get_name()),
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return null;
                }

                for (int i = 0; i < product_amount; ++i) {
                    order.add_product(panel.get_product());
                }
            }

            if (order.is_empty()) {
                // Empty order
                JOptionPane.showMessageDialog(null,
                        "Please select some products", "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }

            return order;
        }

        private DialogOption _show_summary_dialog(Order order) {

            String summary = order.get_order_summary();

            // Show summary dialog
            int chosen_option = JOptionPane.showOptionDialog(null, summary,
                    "Order summary", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                    null, DialogOption.values(), DialogOption.Change);

            if (JOptionPane.CLOSED_OPTION == chosen_option) {
                return DialogOption.Change;
            }

            return DialogOption.values()[chosen_option];
        }

        private void _handle_confirm(Order order) throws IOException {
            String file_name = JOptionPane.showInputDialog(null,
                    "Enter name with ID (no space): ");

            // Write order summary to client file
            FileWriter writer = new FileWriter(file_name + ".txt");
            writer.write(order.get_order_summary());
            writer.close();

            reset_panels();
        }

        private void _handle_cancel() {
            reset_panels();
        }
    }
}
