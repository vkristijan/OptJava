package hr.fer.zemris.optjava.dz12.genprog;

import hr.fer.zemris.optjava.dz12.node.Node;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Path;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class Solution implements Comparable<Solution> {
    private Node root;
    private int fitness;

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    public Solution copy(){
        Solution tmp = new Solution();
        tmp.setRoot(this.getRoot().copy());
        tmp.setFitness(this.getFitness());
        return tmp;
    }

    @Override
    public int compareTo(Solution o) {
        return Integer.compare(this.fitness, o.fitness);
    }

    public void write(BufferedWriter out) throws IOException {
        out.write("Fitness: " + fitness + "\n");
        root.write(out);
    }
}
