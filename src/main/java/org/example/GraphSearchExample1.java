package org.example;

import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphSearchExample1 extends JFrame {

    private mxGraph graph;
    private Object parent;
    private Map<String, Object> vertexMap;

    public GraphSearchExample1(Map<String, List<String>> graphData) {
        setTitle("Graph Search Example");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        graph = new mxGraph();
        parent = graph.getDefaultParent();
        vertexMap = new HashMap<>();

        graph.getModel().beginUpdate();
        try {
            createGraph(graphData);
        } finally {
            graph.getModel().endUpdate();
        }

        mxCompactTreeLayout layout = new mxCompactTreeLayout(graph);
        layout.setHorizontal(true);
        layout.execute(parent);

        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        getContentPane().add(graphComponent, BorderLayout.CENTER);

        JPanel searchPanel = createSearchPanel();
        getContentPane().add(searchPanel, BorderLayout.NORTH);

        setVisible(true);
    }

    private void createGraph(Map<String, List<String>> graphData) {
        graphData.keySet().forEach(key -> {
            vertexMap.put(key, graph.insertVertex(parent, null, key, 20, 20, 80, 30));
        });

        graphData.forEach((key, connectedVertices) -> {
            Object sourceVertex = vertexMap.get(key);
            connectedVertices.forEach(connectedVertex -> {
                Object targetVertex = vertexMap.get(connectedVertex);
                graph.insertEdge(parent, null, "", sourceVertex, targetVertex);
            });
        });
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel();
        JTextField searchField = new JTextField(15);
        JButton searchButton = new JButton("Search");

        searchButton.addActionListener(e -> {
            String searchTerm = searchField.getText();
            searchAndHighlight(searchTerm);
        });

        searchPanel.add(new JLabel("Search: "));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        return searchPanel;
    }

    private void searchAndHighlight(String searchTerm) {
        graph.clearSelection();

        vertexMap.forEach((key, vertex) -> {
            if (key.toLowerCase().contains(searchTerm.toLowerCase())) {
                graph.setCellStyles(mxConstants.STYLE_STROKECOLOR, "red", new Object[]{vertex});
            } else {
                graph.setCellStyles(mxConstants.STYLE_STROKECOLOR, "black", new Object[]{vertex});
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Map<String, List<String>> graphData = new HashMap<>();
            graphData.put("Item A", Arrays.asList("Item B", "Item C"));
            graphData.put("Item B", Arrays.asList("Item D"));
            graphData.put("Item C", Arrays.asList("Item E"));
            graphData.put("Item D", Arrays.asList("Item F"));
            graphData.put("Item E", Arrays.asList("Item F"));

            new GraphSearchExample1(graphData);
        });
    }
}
