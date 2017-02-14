package hr.fer.zemris.optjava.dz12.node.function;

import hr.fer.zemris.optjava.dz12.Field;
import hr.fer.zemris.optjava.dz12.Map;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class IfFoodAhead extends FunctionNode {
    @Override
    public void action(Map map) {
        int row = map.getAntRow();
        int column = map.getAntColumn();

        switch (map.getOrientation()){
            case UP:
                row--;
                break;
            case DOWN:
                row++;
                break;
            case LEFT:
                column--;
                break;
            case RIGHT:
                column++;
        }

        if (row < 0){
            row = map.getHeight() - 1;
        }
        if (row >= map.getHeight()){
            row = 0;
        }
        if (column < 0){
            column = map.getWidth() - 1;
        }
        if (column >= map.getWidth()){
            column = 0;
        }

        if (map.getField(row, column) == Field.FOOD){
            getChild(0).action(map);
        } else {
            getChild(1).action(map);
        }
    }
}
