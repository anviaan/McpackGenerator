package net.anvian;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Screen window = new Screen();
        window.setTitle("Mcpack Generator");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}