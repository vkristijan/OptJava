package hr.fer.zemris.optjava.dz12.node.terminal;

import hr.fer.zemris.optjava.dz12.Map;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class Move extends TerminalNode {
    @Override
    public void action(Map map) {
        map.move();
    }
}
