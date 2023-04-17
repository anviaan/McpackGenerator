package net.anvian;

import java.nio.file.Path;

public class Dirname {
    public static Path getDirname() {
        return dirname;
    }
    public static void setDirname(Path dirname) {
        Dirname.dirname = dirname;
    }

    protected static Path dirname;
}
