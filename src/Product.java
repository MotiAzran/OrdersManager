import java.util.Scanner;

/**
 * Represent a product in the restaurant
 */
public class Product {
    private String _name;
    private String _description;
    private MealType _mealType;
    private float _price;

    /**
     * Holds valid values to meal types
     */
    public enum MealType {
        Appetizer,
        Main,
        Desert,
        Drink
    }

    /**
     * Initialize the product
     * @param name product name
     * @param description product description
     * @param meal_type meal type of the product
     * @param price product price
     */
    public Product(String name, String description, MealType meal_type, float price) {
        _name = name;
        _description = description;
        _mealType = meal_type;
        _price = price;
    }

    /**
     * Product copy constructor
     * takes an initialized product and copy it
     * @param p product to copy
     */
    public Product(Product p) {
        _name = p._name;
        _description = p._description;
        _mealType = p._mealType;
        _price = p._price;
    }

    /**
     * Get product from 3 lines in file
     * first line contains the product name and description
     * separated by comma
     * second line contains the meal type
     * third line contains the product price
     * @param productFile scanner to the input
     * @return initialized product with values from the file
     * @throws IllegalArgumentException the exception thrown in case of
     *          invalid meal type or invalid file format
     */
    public static Product parseProductFromFile(Scanner productFile) throws IllegalArgumentException{
        // Parse first product line - get name and description
        String descriptionLine = productFile.nextLine();
        String name = _getNameByLine(descriptionLine);
        String description = _getDescriptionByLine(descriptionLine);

        // Parse second product line - get meal type
        String mealTypeLine = productFile.nextLine();
        MealType mealType = _getMealTypeByLine(mealTypeLine);
        if (null == mealType) {
            throw new IllegalArgumentException("Invalid meal type " + mealTypeLine);
        }

        // Parse third product line - get product price
        float price = productFile.nextFloat();

        // Move file pointer to next line
        if (productFile.hasNextLine()) {
            productFile.nextLine();
        }

        return new Product(name, description, mealType, price);
    }

    /**
     * Get meal type from string line
     * @param line the line with the meal type
     * @return meal type in case the line represent a MealType name,
     *          otherwise null
     */
    private static MealType _getMealTypeByLine(String line) {
        // Check if the type is a meal type
        for (Product.MealType type : Product.MealType.values()) {
            if (line.equalsIgnoreCase(type.name())) {
                return type;
            }
        }

        // The meal type not found
        return null;
    }

    /**
     * get product name by line,
     * the name appears before the first comma
     * @param line the line with the name and description
     * @return product name
     */
    private static String _getNameByLine(String line) {

        // line format: <name>,<description>
        return line.split(",")[0];
    }

    /**
     * Get product description from the line,
     * the description appears after the comma
     * @param line line with product name and description
     * @return product description if appears, if there isn't a comma
     *          returned an empty string
     */
    private static String _getDescriptionByLine(String line) {

        if (line.indexOf(',') == -1 || line.indexOf(',') == (line.length() - 1)) {
            // The comma is last character or it does not appearing in the line
            return "";
        }
        // line format: <name>,<description>
        return line.substring(line.indexOf(',') + 1);
    }

    /**
     * Get product name
     * @return product name
     */
    public String getName() {
        return _name;
    }

    /**
     * Get product description
     * @return product description
     */
    public String getDescription() {
        return _description;
    }

    /**
     * Get meal type
     * @return meal type of the product
     */
    public String getMealType() {
        return _mealType.name();
    }

    /**
     * Get product price
     * @return product price
     */
    public float getPrice() {
        return _price;
    }
}
