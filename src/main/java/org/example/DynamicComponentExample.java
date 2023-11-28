package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DynamicComponentExample extends JFrame {

    private JPanel buttonPanel;

    public DynamicComponentExample() {
        setTitle("Dynamic Component Example");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        buttonPanel = new JPanel();

        JButton addButton = new JButton("Add Button");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addDynamicButton();
            }
        });

        JButton removeButton = new JButton("Remove Last Button");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeLastDynamicButton();
            }
        });

        JPanel controlPanel = new JPanel();
        controlPanel.add(addButton);
        controlPanel.add(removeButton);

        getContentPane().add(buttonPanel);
        getContentPane().add(controlPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void addDynamicButton() {
        JButton newButton = new JButton("Dynamic Button");
        buttonPanel.add(newButton);
        revalidate(); // Trigger layout manager to recalculate layout
        repaint();    // Ensure the component is repainted
    }

    private void removeLastDynamicButton() {
        Component[] components = buttonPanel.getComponents();
        if (components.length > 0) {
            buttonPanel.remove(components[components.length - 1]);
            revalidate();
            repaint();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DynamicComponentExample::new);
    }
}
