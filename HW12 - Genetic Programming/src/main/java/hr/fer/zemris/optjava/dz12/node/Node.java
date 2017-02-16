package hr.fer.zemris.optjava.dz12.node;

import hr.fer.zemris.optjava.dz12.Map;
import hr.fer.zemris.optjava.dz12.node.terminal.TerminalNode;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public abstract class Node {
    protected int depth;
    protected Node parent;
    protected int nodeCount;

    public int getDepth(){
        return depth;
    }

    public void updateDepth(){
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

    public abstract int action(Map map, int count);

    public abstract List<TerminalNode> getActions(Map map);

    public abstract Node copy();

    public void write(BufferedWriter out) throws IOException {
        out.write(this.getName() + "(");
        for (int i = 0; i < getChildCount(); ++i){
            Node child = getChild(i);
            child.write(out);

            if (i + 1 < getChildCount()){
                out.write(", ");
            }
        }
        out.write(")");
    }

    protected abstract String getName();

    public int getNodeCount(){
        //node count stores the number of children nodes
        //+1 is to include the current node
        return nodeCount + 1;
    }
}
