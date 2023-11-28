package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

public class FlowChartExample1 extends JFrame {

    private List<String> dataList;
    private FlowChartPanel flowChartPanel;

    public FlowChartExample1(List<String> dataList) {
        this.dataList = dataList;
        setTitle("Flow Chart Example");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        flowChartPanel = new FlowChartPanel();
        JScrollPane scrollPane = new JScrollPane(flowChartPanel);
        add(scrollPane, BorderLayout.CENTER);

        JPanel searchPanel = createSearchPanel();
        add(searchPanel, BorderLayout.NORTH);

        setVisible(true);
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel();
        JTextField searchField = new JTextField(15);
        JButton searchButton = new JButton("Search");

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchTerm = searchField.getText();
                searchAndScroll(searchTerm);
            }
        });

        searchPanel.add(new JLabel("Search: "));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        return searchPanel;
    }

    private void searchAndScroll(String searchTerm) {
        if (searchTerm.isEmpty()) {
            return;
        }

        for (int i = 0; i < dataList.size(); i++) {
            if (dataList.get(i).contains(searchTerm)) {
                flowChartPanel.scrollToIndex(i);
                break;
            }
        }
    }

    private class FlowChartPanel extends JPanel {

        private static final int BOX_WIDTH = 80;
        private static final int BOX_HEIGHT = 40;
        private static final int HORIZONTAL_GAP = 100;
        private static final int VERTICAL_GAP = 60;

        private List<JTextArea> textAreas;

        public FlowChartPanel() {
            textAreas = new ArrayList<>();
            int x = HORIZONTAL_GAP;
            int y = VERTICAL_GAP;

            for (String data : dataList) {
                createAndAddTextArea(x, y, data);
                x += HORIZONTAL_GAP;
            }

            // Set preferred size based on content
            setPreferredSize(new Dimension(x, y + BOX_HEIGHT + VERTICAL_GAP));
        }

        private void createAndAddTextArea(int x, int y, String data) {
            JTextArea textArea = new JTextArea(data);
            textArea.setEditable(false);
            textArea.setOpaque(false);
            textArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            textArea.setBounds(x, y, BOX_WIDTH, BOX_HEIGHT);
            textAreas.add(textArea);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (JTextArea textArea : textAreas) {
                textArea.paint(g);
            }

            drawArrows(g);
        }

        private void drawArrows(Graphics g) {
            int x1 = HORIZONTAL_GAP + BOX_WIDTH / 2;
            int y1 = VERTICAL_GAP + BOX_HEIGHT / 2;
            int x2 = x1 + HORIZONTAL_GAP;
            int y2 = y1;

            for (int i = 1; i < dataList.size(); i++) {
                drawArrow(g, x1, y1, x2, y2);
                x1 += HORIZONTAL_GAP;
                x2 += HORIZONTAL_GAP;
            }
        }

        private void drawArrow(Graphics g, int x1, int y1, int x2, int y2) {
            // Draw a line
            g.drawLine(x1, y1, x2, y2);

            // Draw a filled triangle at the end of the line
            int arrowSize = 8;
            Polygon arrowHead = new Polygon();
            arrowHead.addPoint(0, 0);
            arrowHead.addPoint(-arrowSize, -arrowSize);
            arrowHead.addPoint(arrowSize, -arrowSize);

            // Calculate angle of the line
            double angle = Math.atan2(y2 - y1, x2 - x1);
            // Translate and rotate the arrowhead to the end of the line
            AffineTransform transform = new AffineTransform();
            transform.translate(x2, y2);
            transform.rotate(angle);

            // Apply the transformation to the arrowhead
            Shape transformedArrowHead = transform.createTransformedShape(arrowHead);

            // Fill the arrowhead
            Graphics2D g2 = (Graphics2D) g;
            g2.fill(transformedArrowHead);
        }

        private void scrollToIndex(int index) {
            JViewport viewport = (JViewport) getParent();
            Rectangle rect = getBounds(index);
            Point point = viewport.getViewPosition();

            int y = rect.y - (viewport.getHeight() - rect.height) / 2;
            int x = rect.x - (viewport.getWidth() - rect.width) / 2;

            if (y < 0) {
                y = 0;
            }
            if (x < 0) {
                x = 0;
            }

            viewport.setViewPosition(new Point(x, y));
        }

        private Rectangle getBounds(int index) {
            int row = index / 3;  // Assuming 3 columns
            int col = index % 3;
            int x = HORIZONTAL_GAP * col;
            int y = VERTICAL_GAP * row;
            return new Rectangle(x, y, BOX_WIDTH, BOX_HEIGHT);
        }
    }

    public static void main(String[] args) {
        List<String> dataList = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            dataList.add("Step " + i);
        }

        SwingUtilities.invokeLater(() -> new FlowChartExample1(dataList));
    }
}
