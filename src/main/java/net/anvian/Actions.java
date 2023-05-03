package net.anvian;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Actions {
    public static void verification(){
        if (Dirname.getDirname() != null){
            generateActionPerformed();
        }else {
            JOptionPane.showMessageDialog(null, "Seleccione una carpeta primero", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void searchActionPerformed() {
        try {
            Frame frame = new Frame();
            FileDialog folderChooser = new FileDialog(frame, "Seleccione una carpeta");
            folderChooser.setDirectory(System.getProperty("use.home"));
            folderChooser.setMode(FileDialog.LOAD);
            folderChooser.setVisible(true);

            Path folderPath = Path.of(folderChooser.getDirectory());

            Dirname.setDirname(folderPath);

            Window.dir.setText(String.valueOf(Dirname.getDirname()));
        }catch (NullPointerException ignored){}
    }

    public static void generateActionPerformed() {
        String zipFileName = Dirname.getDirname().toString() + ".mcpack";

        try (
                ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFileName))
        ){
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

            JOptionPane.showMessageDialog(null, "El archivo se ha generado correctamente" , "Completado", JOptionPane.INFORMATION_MESSAGE);
            Desktop.getDesktop().open(Dirname.dirname.toFile().getParentFile());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void cleanActionPerformed() {
        Dirname.setDirname(null);
        Window.dir.setText("Ubicación del archivo");
    }
}
