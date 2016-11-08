package hr.fer.zemris.optjava.dz3.neighbor;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public interface INeighborhood<T> {
    T randomNeighbor(T current);
}
