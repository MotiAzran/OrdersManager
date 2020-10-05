package com.moti;

import javax.swing.*;
import java.awt.image.renderable.ContextualRenderedImageFactory;
import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) {

        try {
            OrdersManager order_manager = new OrdersManager("menu.txt");
            order_manager.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            order_manager.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
