package hr.fer.zemris.optjava.dz12.node.function;

import hr.fer.zemris.optjava.dz12.Field;
import hr.fer.zemris.optjava.dz12.Map;
import hr.fer.zemris.optjava.dz12.node.Node;
import hr.fer.zemris.optjava.dz12.node.terminal.TerminalNode;

import java.util.List;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class IfFoodAhead extends FunctionNode {
    @Override
    public int getChildCount() {
        return 2;
    }

    @Override
    public int action(Map map, int count) {
        if (foodAhead(map)){
            return getChild(0).action(map, count);
        } else {
            return getChild(1).action(map, count);
        }
    }

    @Override
    public List<TerminalNode> getActions(Map map) {
        if (foodAhead(map)){
            return getChild(0).getActions(map);
        } else {
            return getChild(1).getActions(map);
        }
    }

    private boolean foodAhead(Map map){
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
                break;
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

        return map.getField(row, column) == Field.FOOD;
    }

    @Override
    public Node copy() {
        FunctionNode newNode = new IfFoodAhead();
        newNode.addChild(this.getChild(0).copy());
        newNode.addChild(this.getChild(1).copy());

        return newNode;
    }

    @Override
    protected String getName() {
        return "IfFoodAhead";
    }
}
