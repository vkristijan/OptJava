package hr.fer.zemris.thread;

import hr.fer.zemris.generic.ga.GASolution;

import java.util.List;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class Task {
    int count;
    List<GASolution<int[]>> population;

    public Task(int count, List<GASolution<int[]>> population) {
        this.count = count;
        this.population = population;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<GASolution<int[]>> getPopulation() {
        return population;
    }

    public void setPopulation(List<GASolution<int[]>> population) {
        this.population = population;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (count != task.count) return false;
        return population != null ? population.equals(task.population) : task.population == null;
    }

    @Override
    public int hashCode() {
        int result = count;
        result = 31 * result + (population != null ? population.hashCode() : 0);
        return result;
    }
}
