package com.moti;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class OrdersManager extends JFrame {

    private final int BUTTONS_PANEL_WIDTH = 500;
    private final int BUTTONS_PANEL_HEIGHT = 75;
    private Menu _menu;
    private JPanel _products_panels_holder;
    private JScrollPane _products_scroll_pane;
    private ArrayList<ProductPanel> _panels;
    private JPanel _buttons_panel;
    private JButton _order_button;

    public OrdersManager(String menu_file_path) throws FileNotFoundException, CloneNotSupportedException {
        super("Orders manager");
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        _buttons_panel = new JPanel();
        _buttons_panel.setPreferredSize(new Dimension(BUTTONS_PANEL_WIDTH, BUTTONS_PANEL_HEIGHT));

        _order_button = new JButton("Order");

        _buttons_panel.add(_order_button);

        _menu = Menu.parse_menu_from_file(new Scanner(new File(menu_file_path)));

        _products_panels_holder = new JPanel();
        _products_panels_holder.setLayout(new BoxLayout(_products_panels_holder, BoxLayout.Y_AXIS));

        ArrayList<Product> products = _menu.get_products();
        _panels = new ArrayList<ProductPanel>();
        for (Product.MealType meal_type : Product.MealType.values()) {
            List<Product> current_products = products.stream().filter(
                    p -> p.get_meal_type().equalsIgnoreCase(meal_type.name())).collect(Collectors.toList());

            for (Product p : current_products) {
                ProductPanel panel = new ProductPanel(p);
                _panels.add(panel);

                _products_panels_holder.add(panel);
            }
        }
        
        _products_scroll_pane = new JScrollPane(_products_panels_holder);

        getContentPane().add(_products_scroll_pane);
        add(_buttons_panel);

        int frame_width = Math.max(BUTTONS_PANEL_WIDTH, ProductPanel.PANEL_WIDTH);
        int frame_height = Math.min(BUTTONS_PANEL_HEIGHT + ProductPanel.PANEL_HEIGHT * _panels.size(), 600);

        setSize(frame_width, frame_height);
    }

    public void print_menu() {

        ListIterator<Product> it = _menu.get_products_iterator();

        while (it.hasNext()) {
            Product p = it.next();
            System.out.printf("%s: %s\n%s\n%.2f\n", p.get_name(), p.get_description(), p.get_meal_type(), p.get_price());
        }
    }
}
