package com.moti;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Represent product panel
 */
public class ProductPanel extends JPanel {
    /*
     * Product panel contains:
     *  - check box to check to buy to product
     *  - combo box to select amount to buy
     */

    private final Integer[] AMOUNT_VALUES = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    public static final int PANEL_WIDTH = 500;
    public static final int PANEL_HEIGHT = 50;
    private JCheckBox _checkBox;
    private boolean _isChosen;
    private JComboBox<Integer> _amountComboBox;
    private Product _product;

    /**
     * Initialize product panel
     * @param product product to put in the panel
     */
    public ProductPanel(Product product) {
        // Set panel size
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

        // Copy product
        _product = new Product(product);

        // Initialize check box
        _checkBox = new JCheckBox(String.format("%s: %s", _product.getName(), _product.getDescription()));
        _checkBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                _isChosen = (ItemEvent.SELECTED == e.getStateChange());
            }
        });

        // Initialize combo box
        _amountComboBox = new JComboBox<>(AMOUNT_VALUES);

        add(_checkBox);
        add(_amountComboBox);
    }

    /**
     * Get if the user chose the product
     * @return true of the product chosen, otherwise false
     */
    public boolean is_chosen() {
        return _isChosen;
    }

    /**
     * Get the amount to sell
     * @return the amount of products
     */
    public int getAmount() {
        if (-1 == _amountComboBox.getSelectedIndex()) {
            // The amount isn't set
            return -1;
        }

        return _amountComboBox.getItemAt(_amountComboBox.getSelectedIndex());
    }

    /**
     * Get product
     * @return Get the product from the panel
     */
    public Product getProduct() {
        return _product;
    }

    /**
     * Reset panel, unset check box and zero the combo box
     */
    public void resetPanel() {
        _checkBox.setSelected(false);
        _amountComboBox.setSelectedIndex(0);
        _isChosen = false;
    }
}
