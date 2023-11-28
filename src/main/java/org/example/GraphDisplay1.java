package org.example;

import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphDisplay1 extends JFrame {

    public GraphDisplay1(Map<String, List<String>> graphData) {
        setTitle("Graph Display");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mxGraph graph = new mxGraph();
        Object parent = graph.getDefaultParent();

        graph.getModel().beginUpdate();
        try {
            // Create vertices for each key in the map
            Map<String, Object> vertexMap = new HashMap<>();
            graphData.keySet().forEach(key -> {
                vertexMap.put(key, graph.insertVertex(parent, null, key, 20, 20, 80, 30));
            });

            // Create edges based on the relationships in the map
            graphData.forEach((key, connectedVertices) -> {
                Object sourceVertex = vertexMap.get(key);
                connectedVertices.forEach(connectedVertex -> {
                    Object targetVertex = vertexMap.get(connectedVertex);
                    graph.insertEdge(parent, null, "", sourceVertex, targetVertex);
                });
            });
        } finally {
            graph.getModel().endUpdate();
        }

        mxCompactTreeLayout layout = new mxCompactTreeLayout(graph);
        layout.setHorizontal(true);
        layout.execute(parent);

        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        getContentPane().add(graphComponent, BorderLayout.CENTER);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Example data
            Map<String, List<String>> graphData = new HashMap<>();
            graphData.put("Item A", Arrays.asList("Item B", "Item C"));
            graphData.put("Item B", Arrays.asList("Item D"));
            graphData.put("Item C", Arrays.asList("Item E"));
            graphData.put("Item D", Arrays.asList("Item F"));
            graphData.put("Item E", Arrays.asList("Item F"));

            new GraphDisplay1(graphData);
        });
    }
}
