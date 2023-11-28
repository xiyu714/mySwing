package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultiLevelSelectorExample extends JFrame {

    private Map<String, List<String>> dataMap;

    private JComboBox<String> level1Selector;
    private JComboBox<String> level2Selector;

    public MultiLevelSelectorExample() {
        setTitle("Multi-Level Selector");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initData();

        level1Selector = new JComboBox<>(dataMap.keySet().toArray(new String[0]));
        level1Selector.addActionListener(e -> updateLevel2Selector());

        level2Selector = new JComboBox<>();
        level2Selector.setVisible(false); // Initially, set it to invisible

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            String level1Selection = (String) level1Selector.getSelectedItem();
            String level2Selection = (String) level2Selector.getSelectedItem();
            JOptionPane.showMessageDialog(this, "Selected: " + level1Selection + " - " + level2Selection);
        });

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));
        panel.add(new JLabel("Level 1:"));
        panel.add(level1Selector);
        panel.add(new JLabel("Level 2:"));
        panel.add(level2Selector);
        panel.add(new JLabel());
        panel.add(submitButton);

        getContentPane().add(panel);
        setVisible(true);
    }

    private void initData() {
        dataMap = new HashMap<>();
        dataMap.put("A", Arrays.asList("A1", "A2"));
        dataMap.put("B", Arrays.asList("B1"));
        // Add more data as needed
    }

    private void updateLevel2Selector() {
        String level1Selection = (String) level1Selector.getSelectedItem();
        List<String> level2Options = dataMap.get(level1Selection);

        if (level2Options != null && !level2Options.isEmpty()) {
            level2Selector.removeAllItems();
            level2Options.forEach(level2Selector::addItem);
            level2Selector.setVisible(true);
        } else {
            level2Selector.removeAllItems();
            level2Selector.setVisible(false);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MultiLevelSelectorExample::new);
    }
}
