package hr.fer.zemris.optjava.dz12.node.function;

import hr.fer.zemris.optjava.dz12.Map;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class Prog3 extends FunctionNode {
    @Override
    public void action(Map map) {
        children.get(0).action(map);
        children.get(1).action(map);
        children.get(2).action(map);
    }
}
