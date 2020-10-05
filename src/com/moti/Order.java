package com.moti;

import java.util.ArrayList;
import java.util.ListIterator;

public class Order {

    private ArrayList<Product> _products;
    private float _total_price;

    Order() {
        _products = new ArrayList<Product>();
    }

    Order(ArrayList<Product> products) throws CloneNotSupportedException {
        for (Product product : products) {
            _products.add((Product) product.clone());
            _total_price += product.get_price();
        }
    }

    void add_product(Product product) throws CloneNotSupportedException {
        _products.add((Product) product.clone());
        _total_price += product.get_price();
    }

    public ArrayList<Product> get_products() {
        return _products;
    }

    public ListIterator<Product> get_products_iterator() {
        return _products.listIterator();
    }

    public float get_total_price() {
        return _total_price;
    }
}
