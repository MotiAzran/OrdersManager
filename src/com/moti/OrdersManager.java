package com.moti;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ListIterator;
import java.util.Scanner;

public class OrdersManager{

    Menu _menu;

    public OrdersManager(String menu_file_path) throws FileNotFoundException, CloneNotSupportedException {
        _menu = Menu.parse_menu_from_file(new Scanner(new File(menu_file_path)));
    }

    public void print_menu() {

        ListIterator<Product> it = _menu.get_products_iterator();

        while (it.hasNext()) {
            Product p = it.next();
            System.out.printf("%s: %s\n%s\n%.2f\n", p.get_name(), p.get_description(), p.get_meal_type(), p.get_price());
        }
    }
}
