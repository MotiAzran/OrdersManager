/*
 * Moti Azran
 */

import javax.swing.*;

/**
 * Program main class
 */
public class Main {

    /**
     * Function entry point,
     * shown the program window
     * @param args command line argument
     */
    public static void main(String[] args) {
        try {
            // Initialize main window
            OrdersManager order_manager = new OrdersManager("menu.txt");

            order_manager.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            order_manager.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
