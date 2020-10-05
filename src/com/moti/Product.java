package com.moti;

import java.util.Scanner;

public class Product implements Cloneable {

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

    public static Product parse_product_from_file(Scanner product_file) throws IllegalArgumentException{

        // Get product description
        String desc_line = product_file.nextLine();
        String name = _get_name_by_line(desc_line);
        String description = _get_description_by_line(desc_line);

        // Get product meal type
        String meal_type_line = product_file.nextLine();
        MealType meal_type = _get_meal_type_by_line(meal_type_line);
        if (null == meal_type) {
            throw new IllegalArgumentException("Invalid meal type " + meal_type_line);
        }

        // Get product price
        float price = product_file.nextFloat();

        // Move file pointer to next line
        if (product_file.hasNextLine()) {
            product_file.nextLine();
        }

        return new Product(name, description, meal_type, price);
    }

    private static MealType _get_meal_type_by_line(String line) {
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

    @Override
    public Object clone() throws CloneNotSupportedException {
        Product new_product = (Product) super.clone();

        new_product._name = _name;
        new_product._description = _description;
        new_product._meal_type = _meal_type;
        new_product._price = _price;

        return new_product;
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
