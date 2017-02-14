package hr.fer.zemris.optjava.dz12.node.terminal;

import hr.fer.zemris.optjava.dz12.Map;
import hr.fer.zemris.optjava.dz12.Orientation;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class Right extends TerminalNode {

    @Override
    public void action(Map map) {
        switch (map.getOrientation()){
            case RIGHT:
                map.setOrientation(Orientation.DOWN);
                break;
            case DOWN:
                map.setOrientation(Orientation.LEFT);
                break;
            case LEFT:
                map.setOrientation(Orientation.UP);
                break;
            case UP:
                map.setOrientation(Orientation.RIGHT);
                break;
        }
    }
}
