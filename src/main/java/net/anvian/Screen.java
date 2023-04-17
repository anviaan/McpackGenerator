package net.anvian;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Screen extends JFrame {
    public Screen() {
        initComponents();
    }
    private static Path dirname;
    public static Path getDirname() {
        return dirname;
    }
    public static void setDirname(Path dirname) {
        Screen.dirname = dirname;
    }

    private JTextField dir;

    private void initComponents() {

        JButton search = new JButton();
        dir = new JTextField();
        JButton generate = new JButton();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        search.setText("Buscar");
        search.addActionListener(this::searchActionPerformed);

        dir.setEditable(false);
        dir.setText("Ubicacion del archivo");

        generate.setText("Generar");
        generate.addActionListener(evt -> {
            try {
                generateActionPerformed();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(search)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(dir, GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE))
                    .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(generate)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(search)
                    .addComponent(dir, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(generate)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }

    private void generateActionPerformed() throws FileNotFoundException {
        String zipFileName = Screen.getDirname().toString() + ".mcpack";
        
        try (
                ZipOutputStream zos = new ZipOutputStream(
                        new FileOutputStream(zipFileName))
        ) {
            Files.walkFileTree(Screen.getDirname(), new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) {

                    if (attributes.isSymbolicLink()) {
                        return FileVisitResult.CONTINUE;
                    }

                    try (FileInputStream fis = new FileInputStream(file.toFile())) {

                        Path targetFile = Screen.getDirname().relativize(file);
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
            Desktop.getDesktop().open(dirname.toFile().getParentFile());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void searchActionPerformed(java.awt.event.ActionEvent evt) {
        JFileChooser fc = new JFileChooser();
        fc.showOpenDialog(null);
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        File f = fc.getCurrentDirectory();
        Path source = Paths.get(f.getAbsolutePath());
        Screen.setDirname(source);

        dir.setText(String.valueOf(Screen.getDirname()));
    }
}
