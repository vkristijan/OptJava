package hr.fer.zemris.optjava.dz12.genprog.crossover;

import hr.fer.zemris.optjava.dz12.Utility;
import hr.fer.zemris.optjava.dz12.genprog.Solution;
import hr.fer.zemris.optjava.dz12.node.Node;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class Crossover implements ICrossover {
    private int maxDepth;
    private int maxNodes;
    private static int maxEffort = 100;

    public Crossover(int maxDepth, int maxNodes) {
        this.maxDepth = maxDepth;
        this.maxNodes = maxNodes;
    }

    @Override
    public Solution crossover(Solution a, Solution b) {
        Solution tmp = a.copy();

        Node first = getNode(tmp.getRoot());
        Node second;
        int effort = 0;
        do {
            effort++;
            if (effort > maxEffort){
                return tmp;
            }
            second = getNode(b.getRoot());
        }  while (first.getDepth() > second.getDepth());

        Node parent = first.getParent();
        int index = 0;
        for (int i = 0; i < parent.getChildCount(); ++i){
            if (parent.getChild(i) == first){
                index = i;
                break;
            }
        }
        first.getParent().setChild(second, index);

        return tmp;
    }

    private Node getNode(Node root){
        Node tmp;
        do {
            tmp = Utility.getRandomNode(root);
        } while (tmp.getParent() == null);
        return tmp;
    }
}
