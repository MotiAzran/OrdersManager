package com.moti;

import javax.swing.*;
import java.awt.*;

public class ProductPanel extends JPanel {

    private final Integer[] AMOUNT_VALUES = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    public static final int PANEL_WIDTH = 500;
    public static final int PANEL_HEIGHT = 50;
    private JCheckBox _check_box;
    private JComboBox<Integer> _amount_combo_box;
    private Product _product;

    public ProductPanel(Product product) throws CloneNotSupportedException {

        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

        _product = (Product) product.clone();
        _check_box = new JCheckBox(String.format("%s: %s", _product.get_name(), _product.get_description()));
        _amount_combo_box = new JComboBox<>(AMOUNT_VALUES);

        add(_check_box);
        add(_amount_combo_box);
    }

}
