package com.moti;

import java.util.ArrayList;
import java.util.ListIterator;

public class Order {

    private ArrayList<Product> _products;
    private float _total_price;

    Order() {
        _products = new ArrayList<Product>();
    }

    Order(ArrayList<Product> products)  {
        for (Product product : products) {
            _products.add(new Product(product));
            _total_price += product.get_price();
        }
    }

    void add_product(Product product)  {
        _products.add(new Product(product));
        _total_price += product.get_price();
    }

    public boolean is_empty() {
        return _products.isEmpty();
    }

    public String get_order_summary() {
        String summary = "";

        for (Product p : _products) {
            summary = summary.concat(String.format("%s %.2f\n", p.get_name(), p.get_price()));
        }

        summary = summary.concat(String.format("\nTotal price: %.2f", _total_price));

        return summary;
    }
}
