package com.moti;

import java.util.Scanner;


public class Product {

    private String _name;
    private String _description;
    private MealType _meal_type;
    private float _price;

    public enum MealType {
        Appetizer,
        Main,
        Desert,
        Drink
    }

    public Product(String name, String description, MealType meal_type, float price) {

        _name = name;
        _description = description;
        _meal_type = meal_type;
        _price = price;
    }

    public Product(Product p) {

        _name = p._name;
        _description = p._description;
        _meal_type = p._meal_type;
        _price = p._price;
    }

    public static Product parse_product_from_file(Scanner product_file) throws IllegalArgumentException{

        // Parse first product line - get name and description
        String desc_line = product_file.nextLine();
        String name = _get_name_by_line(desc_line);
        String description = _get_description_by_line(desc_line);

        // Parse second product line - get meal type
        String meal_type_line = product_file.nextLine();
        MealType meal_type = _get_meal_type_by_line(meal_type_line);
        if (null == meal_type) {
            throw new IllegalArgumentException("Invalid meal type " + meal_type_line);
        }

        // Parse third product line - get product price
        float price = product_file.nextFloat();

        // Move file pointer to next line
        if (product_file.hasNextLine()) {
            product_file.nextLine();
        }

        return new Product(name, description, meal_type, price);
    }

    private static MealType _get_meal_type_by_line(String line) {

        // Check if the type is a meal type
        for (Product.MealType type : Product.MealType.values()) {
            if (line.equalsIgnoreCase(type.name())) {
                return type;
            }
        }

        return null;
    }

    private static String _get_name_by_line(String line) {

        // line format: <name>,<description>
        return line.split(",")[0];
    }

    private static String _get_description_by_line(String line) {

        if (line.indexOf(',') == -1 || line.indexOf(',') == (line.length() - 1)) {
            // The comma is last character or it does not appearing in the line
            return "";
        }
        // line format: <name>,<description>
        return line.substring(line.indexOf(',') + 1);
    }

    public String get_name() {
        return _name;
    }

    public String get_description() {
        return _description;
    }

    public String get_meal_type() {
        return _meal_type.name();
    }

    public float get_price() {
        return _price;
    }
}
