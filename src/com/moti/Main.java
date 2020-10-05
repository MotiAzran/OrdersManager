package com.moti;

import java.awt.image.renderable.ContextualRenderedImageFactory;
import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) {

        try {
            OrdersManager order_manager = new OrdersManager("menu.txt");

            order_manager.print_menu();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
