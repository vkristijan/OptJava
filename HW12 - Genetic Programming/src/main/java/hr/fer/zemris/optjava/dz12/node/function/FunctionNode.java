package hr.fer.zemris.optjava.dz12.node.function;

import hr.fer.zemris.optjava.dz12.node.Node;

import java.util.List;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public abstract class FunctionNode extends Node {
    protected List<Node> children;

    @Override
    public List<Node> getChildren() {
        return children;
    }

    @Override
    public int getChildCount() {
        return children.size();
    }

    @Override
    public Node getChild(int index) {
        return children.get(index);
    }

    @Override
    public void addChild(Node child) {
        children.add(child);
    }

    @Override
    public void setChild(Node child, int index) {
        children.set(index, child);
    }
}
