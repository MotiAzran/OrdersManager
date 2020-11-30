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

    private final Integer[] AMOUNT_VALUES = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private JCheckBox checkBox;
    private boolean isChosen;
    private JComboBox<Integer> amountComboBox;
    private Product product;

    /**
     * Initialize product panel
     * @param product product to put in the panel
     */
    public ProductPanel(Product product, int panelWidth, int panelHeight) {
        // Set panel size
        setPreferredSize(new Dimension(panelWidth, panelHeight));

        // Copy product
        this.product = new Product(product);

        // Initialize check box
        checkBox = new JCheckBox(String.format("%s: %s", this.product.getName(), this.product.getDescription()));
        checkBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                isChosen = (ItemEvent.SELECTED == e.getStateChange());
            }
        });

        // Initialize combo box
        amountComboBox = new JComboBox<Integer>(AMOUNT_VALUES);

        add(checkBox);
        add(amountComboBox);
    }

    /**
     * Get if the user chose the product
     * @return true of the product chosen, otherwise false
     */
    public boolean isChosen() {
        return isChosen;
    }

    /**
     * Get the amount to sell
     * @return the amount of products
     */
    public int getAmount() {
        if (-1 == amountComboBox.getSelectedIndex()) {
            // The amount isn't set
            return -1;
        }

        return amountComboBox.getItemAt(amountComboBox.getSelectedIndex());
    }

    /**
     * Get product
     * @return Get the product from the panel
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Reset panel, unset check box and zero the combo box
     */
    public void resetPanel() {
        checkBox.setSelected(false);
        amountComboBox.setSelectedIndex(0);
        isChosen = false;
    }
}
