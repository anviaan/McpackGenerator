package net.anvian;

import java.nio.file.Path;

public class Dirname {
    protected static Path dirname;

    public static Path getDirname() {
        return dirname;
    }
    public static void setDirname(Path dirname) {
        Dirname.dirname = dirname;
    }
}
