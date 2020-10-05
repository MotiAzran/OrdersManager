package com.moti;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.ListIterator;

public class Menu {

    private ArrayList<Product> _products;

    Menu() {
        _products = new ArrayList<Product>();
    }

    Menu(ArrayList<Product> products) throws CloneNotSupportedException {
        for (Product product : products) {
            _products.add((Product) product.clone());
        }
    }

    public static Menu parse_menu_from_file(Scanner menu_file) throws CloneNotSupportedException {

        Menu menu = new Menu();

        while (menu_file.hasNext()) {
            menu.add_product(Product.parse_product_from_file(menu_file));
        }

        return menu;
    }

    void add_product(Product product) throws CloneNotSupportedException {
        _products.add((Product) product.clone());
    }

    public ArrayList<Product> get_products() {
        return _products;
    }

    public ListIterator<Product> get_products_iterator() {
        return _products.listIterator();
    }
}
