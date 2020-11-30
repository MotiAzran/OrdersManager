import java.util.ArrayList;

/**
 * Represent a client order
 */
public class Order {
    /*
     * Holds list of all the products
     * and updates a total price for all the products
     */

    private ArrayList<Product> products;
    private float totalPrice;

    /**
     * Initialize the order without prodcts
     */
    public Order() {
        products = new ArrayList<Product>();
    }

    /**
     * Add product to the order
     * @param product product to add
     */
    public void addProduct(Product product)  {
        products.add(new Product(product));
        totalPrice += product.getPrice();
    }

    /**
     * Checks if the order is empty
     * @return true if the order has no products, otherwise false
     */
    public boolean isEmpty() {
        return products.isEmpty();
    }

    /**
     * Get summary of the order,
     * string with all the order products
     * and their prices
     * @return order summary message
     */
    public String getOrderSummary() {
        String summary = "";

        for (Product p : products) {
            summary = summary.concat(String.format("%s %.2f\n", p.getName(), p.getPrice()));
        }

        summary = summary.concat(String.format("\nTotal price: %.2f", totalPrice));

        return summary;
    }
}
