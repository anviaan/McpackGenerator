package net.anvian;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Window window = new Window();

        window.setTitle("Mcpack Generator");
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}