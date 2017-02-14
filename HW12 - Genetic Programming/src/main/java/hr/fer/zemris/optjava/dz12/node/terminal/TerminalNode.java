package hr.fer.zemris.optjava.dz12.node.terminal;

import hr.fer.zemris.optjava.dz12.node.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public abstract class TerminalNode extends Node {
    @Override
    public List<Node> getChildren() {
        return new ArrayList<>();
    }

    @Override
    public int getChildCount() {
        return 0;
    }

    @Override
    public Node getChild(int index) {
        throw new UnsupportedOperationException("This node doesn't support children!");
    }

    @Override
    public void addChild(Node child) {
        throw new UnsupportedOperationException("This node doesn't support children!");
    }

    @Override
    public void setChild(Node child, int index) {
        throw new UnsupportedOperationException("This node doesn't support children!");
    }
}
