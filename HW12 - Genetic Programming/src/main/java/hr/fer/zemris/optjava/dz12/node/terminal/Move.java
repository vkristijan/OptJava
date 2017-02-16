package hr.fer.zemris.optjava.dz12.node.terminal;

import hr.fer.zemris.optjava.dz12.AntTrailGA;
import hr.fer.zemris.optjava.dz12.Map;
import hr.fer.zemris.optjava.dz12.node.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class Move extends TerminalNode {
    @Override
    public int action(Map map, int count) {
        if (count >= AntTrailGA.MAX_MOVES){
            return count;
        }

        map.move();
        return count + 1;
    }

    @Override
    public List<TerminalNode> getActions(Map map) {
        action(map, 0);
        List<TerminalNode> nodes = new ArrayList<>();

        nodes.add((TerminalNode)this.copy());
        return nodes;
    }

    @Override
    public Node copy() {
        return new Move();
    }

    @Override
    protected String getName() {
        return "Move";
    }
}
