package com.moti;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ProductPanel extends JPanel {

    private final Integer[] AMOUNT_VALUES = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    public static final int PANEL_WIDTH = 500;
    public static final int PANEL_HEIGHT = 50;
    private JCheckBox _check_box;
    private boolean _is_chosen;
    private JComboBox<Integer> _amount_combo_box;
    private Product _product;

    public ProductPanel(Product product) {

        // Set panel size
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

        // Copy product
        _product = new Product(product);

        // Initialize check box
        _check_box = new JCheckBox(String.format("%s: %s", _product.get_name(), _product.get_description()));
        _check_box.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                _is_chosen = (ItemEvent.SELECTED == e.getStateChange());
            }
        });

        // Initialize combo box
        _amount_combo_box = new JComboBox<>(AMOUNT_VALUES);

        add(_check_box);
        add(_amount_combo_box);
    }

    public boolean is_chosen() {
        return _is_chosen;
    }

    public int get_amount() {
        if (-1 == _amount_combo_box.getSelectedIndex()) {
            // The amount isn't set
            return -1;
        }

        return _amount_combo_box.getItemAt(_amount_combo_box.getSelectedIndex());
    }

    public Product get_product() {
        return _product;
    }

    public void reset_panel() {
        _check_box.setSelected(false);
        _amount_combo_box.setSelectedIndex(0);
        _is_chosen = false;
    }
}
