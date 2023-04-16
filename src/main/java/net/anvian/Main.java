package net.anvian;

import javax.swing.*;
import java.nio.file.Path;

public class Main {
    private static Path dirname;
    public static Path getDirname() {
        return dirname;
    }
    public static void setDirname(Path dirname) {
        Main.dirname = dirname;
    }

    public static void main(String[] args) {
        Screen window = new Screen();
        window.setTitle("Mcpack Generator");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}