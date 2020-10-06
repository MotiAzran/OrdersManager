package com.moti;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.ListIterator;

public class Menu {

    private ArrayList<Product> _products;

    Menu() {
        _products = new ArrayList<Product>();
    }

    Menu(ArrayList<Product> products) {

        for (Product product : products) {
            _products.add(new Product(product));
        }
    }

    public static Menu parse_menu_from_file(Scanner menu_file) {

        Menu menu = new Menu();

        while (menu_file.hasNext()) {
            menu.add_product(Product.parse_product_from_file(menu_file));
        }

        return menu;
    }

    void add_product(Product product) {
        _products.add(new Product(product));
    }

    public ArrayList<Product> get_products() {
        return _products;
    }
}
