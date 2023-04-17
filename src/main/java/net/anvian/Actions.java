package net.anvian;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Actions {
    public static void generateActionPerformed() throws FileNotFoundException {
        String zipFileName = Dirname.getDirname().toString() + ".mcpack";

        try (
                ZipOutputStream zos = new ZipOutputStream(
                        new FileOutputStream(zipFileName))
        ) {
            Files.walkFileTree(Dirname.getDirname(), new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) {

                    if (attributes.isSymbolicLink()) {
                        return FileVisitResult.CONTINUE;
                    }

                    try (FileInputStream fis = new FileInputStream(file.toFile())) {

                        Path targetFile = Dirname.getDirname().relativize(file);
                        zos.putNextEntry(new ZipEntry(targetFile.toString()));

                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len = fis.read(buffer)) > 0) {
                            zos.write(buffer, 0, len);
                        }

                        zos.closeEntry();

                        System.out.println("Zip file: " + file);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) {
                    System.err.printf("Unable to zip : %s%n%s%n", file, exc);
                    return FileVisitResult.CONTINUE;
                }
            });

            JOptionPane.showMessageDialog(null, "Done");
            //Open the previous directory
            Desktop.getDesktop().open(Dirname.dirname.toFile().getParentFile());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void searchActionPerformed() {
        JFileChooser fc = new JFileChooser();
        fc.showOpenDialog(null);
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        File f = fc.getCurrentDirectory();
        Path source = Paths.get(f.getAbsolutePath());
        Dirname.setDirname(source);

        Window.dir.setText(String.valueOf(Dirname.getDirname()));
    }
}
