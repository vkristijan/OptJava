package hr.fer.zemris.optjava.dz12;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class Map {
    private int width;
    private int height;

    private int antRow;
    private int antColumn;
    private Orientation orientation;

    private int foodEaten;

    private Field[][] map;

    public Map() {
        antColumn = 0;
        antRow = 0;
        orientation = Orientation.RIGHT;
        foodEaten = 0;
    }

    public static Map readFromFile(Path filePath){
        List<String> lines = null;
        Map map = new Map();

        try {
            lines = Files.readAllLines(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String sizeLine = lines.get(0);
        String[] sizes = sizeLine.split("x");
        map.width = Integer.parseInt(sizes[0]);
        map.height = Integer.parseInt(sizes[1]);

        map.map = new Field[map.height][map.width];
        lines = lines.subList(1, lines.size());
        int row = 0;
        for (String line : lines){
            char[] lineChars = line.toCharArray();
            for (int column = 0; column < map.width; ++column){
                if (lineChars[column] == '1'){
                    map.map[row][column] = Field.FOOD;
                } else {
                    map.map[row][column] = Field.EMPTY;
                }
            }
            row++;
        }

        if (map.getCurrentField() == Field.FOOD){
            map.foodEaten++;
            map.setCurrentField(Field.EMPTY);
        }

        return map;
    }

    public void move(){
        switch (orientation){
            case DOWN:
                antRow++;
                if (antRow >= height){
                    antRow = 0;
                }
                break;
            case UP:
                antRow--;
                if (antRow < 0){
                    antRow = height - 1;
                }
                break;
            case LEFT:
                antColumn--;
                if (antColumn < 0){
                    antColumn = width - 1;
                }
                break;
            case RIGHT:
                antColumn++;
                if (antColumn >= width){
                    antColumn = 0;
                }
                break;
        }

        if (getCurrentField() == Field.FOOD){
            foodEaten++;
            setCurrentField(Field.EMPTY);
        }
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Field getField(int row, int column){
        return map[row][column];
    }

    public Field getCurrentField(){
        return map[antRow][antColumn];
    }

    public void setCurrentField(Field field){
        map[antRow][antColumn] = field;
    }

    public int getAntRow() {
        return antRow;
    }

    public int getAntColumn() {
        return antColumn;
    }

    public int getFoodEaten() {
        return foodEaten;
    }

    public Map copy(){
        Map newMap = new Map();
        newMap.foodEaten = this.foodEaten;
        newMap.antColumn = this.antColumn;
        newMap.antRow = this.antRow;

        newMap.width = this.width;
        newMap.height = this.height;
        newMap.orientation = this.orientation;

        newMap.map = new Field[height][width];
        for (int i = 0; i < height; ++i){
            System.arraycopy(this.map[i], 0, newMap.map[i], 0, width);
        }

        return newMap;
    }
}
