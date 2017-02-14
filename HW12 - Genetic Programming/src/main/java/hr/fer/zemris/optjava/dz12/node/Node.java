package hr.fer.zemris.optjava.dz12.node;

import hr.fer.zemris.optjava.dz12.Map;

import java.util.List;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public abstract class Node {
    protected int depth;
    protected Node parent;

    public int getDepth(){
        return depth;
    }

    private void updateDepth(){
        if (parent == null){
            depth = 0;
        } else {
            depth = parent.getDepth() + 1;
        }
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public abstract List<Node> getChildren();

    public abstract int getChildCount();

    public abstract Node getChild(int index);

    public abstract void addChild(Node child);

    public abstract void setChild(Node child, int index);

    public abstract void action(Map map);
}
