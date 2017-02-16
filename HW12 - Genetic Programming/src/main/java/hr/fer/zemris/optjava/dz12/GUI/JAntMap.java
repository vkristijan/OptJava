package hr.fer.zemris.optjava.dz12.GUI;

import hr.fer.zemris.optjava.dz12.Field;
import hr.fer.zemris.optjava.dz12.Map;
import hr.fer.zemris.optjava.dz12.node.terminal.TerminalNode;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class JAntMap extends JComponent {
    private static final long serialVersionUID = 1;

    private Map map;
    private java.util.List<TerminalNode> nodes;

    private Image empty;
    private Image food;
    private Image up;
    private Image down;
    private Image left;
    private Image right;

    private static final String emptyPath = "img/grass.png";
    private static final String foodPath = "img/food.png";
    private static final String upPath = "img/ant-U.png";
    private static final String downPath = "img/ant-D.png";
    private static final String leftPath = "img/ant-L.png";
    private static final String rightPath = "img/ant-R.png";

    public JAntMap(Map map, List<TerminalNode> nodes) {
        this.map = map;
        this.nodes = nodes;

        empty = loadImage(emptyPath);
        food = loadImage(foodPath);
        up = loadImage(upPath);
        down = loadImage(downPath);
        left = loadImage(leftPath);
        right = loadImage(rightPath);

    }

    private Image loadImage(String path) {
        URL file = getClass().getClassLoader().getResource(path);
        try {
            return ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();

        int mapWidth = map.getWidth();
        int mapHeight = map.getHeight();

        int gridWidth = width / mapWidth;
        int gridHeight = height / mapHeight;

        for (int i = 0; i < mapHeight; ++i){
            for (int j = 0; j < mapWidth; ++j){
                if (map.getField(i, j) == Field.EMPTY){
                    g.drawImage(empty, j * gridWidth, i * gridHeight, gridWidth, gridHeight, this);
                } else {
                    g.drawImage(food, j * gridWidth, i * gridHeight, gridWidth, gridHeight, this);
                }
            }
        }

        int row = map.getAntRow();
        int column = map.getAntColumn();

        switch (map.getOrientation()){
            case UP:
                g.drawImage(up, column * gridWidth, row * gridHeight, gridWidth, gridHeight, this);
                break;
            case DOWN:
                g.drawImage(down, column * gridWidth, row * gridHeight, gridWidth, gridHeight, this);
                break;
            case LEFT:
                g.drawImage(left, column * gridWidth, row * gridHeight, gridWidth, gridHeight, this);
                break;
            case RIGHT:
                g.drawImage(right, column * gridWidth, row * gridHeight, gridWidth, gridHeight, this);
                break;
        }
    }

    public List<TerminalNode> getNodes() {
        return nodes;
    }

    public TerminalNode getNode(int index){
        return nodes.get(index);
    }

    public int getNodeCount(){
        return nodes.size();
    }

    public Map getMap() {
        return map;
    }
}
