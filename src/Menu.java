import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represent a menu
 */
public class Menu {
    private ArrayList<Product> products;

    /**
     * Initialize an empty menu
     */
    Menu() {
        products = new ArrayList<Product>();
    }

    /**
     * Create menu from file
     * @param menuFile input for the input
     * @return the initialized menu
     */
    public static Menu parseMenuFromFile(Scanner menuFile) {

        Menu menu = new Menu();

        while (menuFile.hasNext()) {
            menu.addProduct(Product.parseProductFromFile(menuFile));
        }

        return menu;
    }

    /**
     * Add product to menu
     * @param product product to add
     */
    void addProduct(Product product) {
        products.add(new Product(product));
    }

    /**
     * Get all menu products
     * @return list of menu products
     */
    public ArrayList<Product> getProducts() {
        return products;
    }
}
