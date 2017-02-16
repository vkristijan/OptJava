package hr.fer.zemris.optjava.dz12.node.function;

import hr.fer.zemris.optjava.dz12.node.Node;

import java.util.ArrayList;
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
    public Node getChild(int index) {
        return children.get(index);
    }

    @Override
    public void addChild(Node child) {
        if (children == null){
            children = new ArrayList<>();
        }

        children.add(child);
        child.setParent(this);
        this.nodeCount += child.getNodeCount();
    }

    @Override
    public void setChild(Node child, int index) {
        this.nodeCount -= children.get(index).getNodeCount();
        children.set(index, child);
        child.setParent(this);
        this.nodeCount += child.getNodeCount();
    }


}
