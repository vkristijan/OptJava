package hr.fer.zemris.optjava.dz12.genprog.mutation;

import hr.fer.zemris.optjava.dz12.Utility;
import hr.fer.zemris.optjava.dz12.genprog.Solution;
import hr.fer.zemris.optjava.dz12.node.Node;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class Mutation implements IMutation {
    private int maxDepth;
    private int maxNodes;

    public Mutation(int maxDepth, int maxNodes) {
        this.maxDepth = maxDepth;
        this.maxNodes = maxNodes;
    }

    @Override
    public Solution mutate(Solution solution) {
        Node root = solution.getRoot();

        Node node = Utility.getRandomNode(root);
        node.updateDepth();

        Solution sol = new Solution();
        if (node.getParent() == null){
            sol.setRoot(Utility.generateTreeRoot(maxDepth, maxNodes, false));
            return sol;
        }

        Node parent = node.getParent();
        int index = 0;
        for (int i = 0; i < parent.getChildCount(); ++i){
            if (parent.getChild(i) == node){
                index = i;
                break;
            }
        }

        Node newNode = Utility.generateSubtree(
                maxDepth - node.getDepth(),
                maxNodes - node.getNodeCount(),
                false
        );
        node.getParent().setChild(newNode, index);
        sol.setRoot(root);
        return sol;
    }
}
