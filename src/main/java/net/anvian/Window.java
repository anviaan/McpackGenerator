package net.anvian;

import javax.swing.*;
import java.io.FileNotFoundException;

public class Window extends JFrame {
    public static JTextField dir;
    private final JButton search = new JButton();
    private final JButton generate = new JButton();

    public Window() {
        initComponents();
    }

    private void initComponents() {

        dir = new JTextField();

        search.setText("Buscar");
        search.addActionListener(env -> Actions.searchActionPerformed());

        generate.setText("Generar");
        generate.addActionListener(evt -> {
            try {
                Actions.generateActionPerformed();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        dir.setEditable(false);
        dir.setText("Ubicacion del archivo");

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
}
