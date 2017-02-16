package hr.fer.zemris.optjava.dz12;

import hr.fer.zemris.optjava.dz12.node.Node;
import hr.fer.zemris.optjava.dz12.node.function.FunctionNode;
import hr.fer.zemris.optjava.dz12.node.function.IfFoodAhead;
import hr.fer.zemris.optjava.dz12.node.function.Prog2;
import hr.fer.zemris.optjava.dz12.node.function.Prog3;
import hr.fer.zemris.optjava.dz12.node.terminal.Left;
import hr.fer.zemris.optjava.dz12.node.terminal.Move;
import hr.fer.zemris.optjava.dz12.node.terminal.Right;
import hr.fer.zemris.optjava.dz12.node.terminal.TerminalNode;

import java.util.Random;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class Utility {
    private static Random rnd = new Random();

    public static Node generateTreeRoot(int maxDepth, int maxNodes, boolean full){
        Node root = getRandomFunctionNode();

        for (int i = 0; i < root.getChildCount(); ++i){
            Node child = generateSubtree(maxDepth - 1, maxNodes - 1, full);
            root.addChild(child);
        }

        return root;
    }

    public static Node generateSubtree(int maxDepth, int maxNodes, boolean full){
        Node root;
        if (maxDepth == 1 || maxNodes < 1){
            root = getRandomTerminalNode();
        } else if (full){
            root = getRandomFunctionNode();
        } else {
            root = getRandomNode();
        }

        maxNodes--;
        for (int i = 0; i < root.getChildCount(); ++i){
            Node child = generateSubtree(maxDepth - 1, maxNodes, full);
            maxNodes -= child.getNodeCount();
            root.addChild(child);
        }

        return root;
    }

    public static Node getRandomNode(){
        int rand = rnd.nextInt(2);
        switch (rand){
            case 0:
                return getRandomFunctionNode();
            case 1:
                return getRandomTerminalNode();
        }

        return null;
    }

    public static FunctionNode getRandomFunctionNode(){
        int rand = rnd.nextInt(3);
        switch (rand){
            case 0:
                return new IfFoodAhead();
            case 1:
                return new Prog2();
            case 2:
                return new Prog3();
        }

        return null;
    }

    public static TerminalNode getRandomTerminalNode(){
        int rand = rnd.nextInt(3);
        switch (rand){
            case 0:
                return new Left();
            case 1:
                return new Right();
            case 2:
                return new Move();
        }

        return null;
    }

    public static Node getRandomNode(Node root){
        int count = root.getNodeCount();

        return getRandomNode(root, rnd.nextInt(count));
    }

    private static Node getRandomNode(Node root, int count){
        if (count == 0) return root;

        count--;

        for (int i = 0; i < root.getChildCount(); ++i){
            Node child = root.getChild(i);
            if (child.getNodeCount() > count){
                return getRandomNode(child, count);
            } else {
                count -= child.getNodeCount();
            }
        }

        return root;
    }
}
