package net.anvian;

import javax.swing.*;

public class Window extends JFrame {
    public static JTextField dir;
    private final JButton search = new JButton();
    private final JButton generate = new JButton();
    private final JButton clean = new JButton();

    public Window() {
        initComponents();
    }

    private void initComponents() {

        dir = new JTextField();

        search.setText("Buscar");
        search.addActionListener(env -> Actions.searchActionPerformed());

        generate.setText("Generar");
        generate.addActionListener(evt -> Actions.verification());

        clean.setText("Limpiar");
        clean.addActionListener(evt -> Actions.cleanActionPerformed());

        dir.setEditable(false);
        dir.setText("Ubicación del archivo");

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(search)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(dir))
                                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGap(0, 232, Short.MAX_VALUE)
                                                .addComponent(clean)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
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
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(generate)
                                        .addComponent(clean))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }
}
