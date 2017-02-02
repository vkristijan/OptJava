package hr.fer.zemris.generic.ga.selection;

import hr.fer.zemris.generic.ga.GASolution;
import hr.fer.zemris.generic.ga.IGAEvaluator;
import hr.fer.zemris.generic.ga.crossover.ICrossover;
import hr.fer.zemris.generic.ga.mutation.IMutation;
import hr.fer.zemris.thread.Task;

import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class CreateChildThread implements Runnable {
    private ISelection firstSelection;
    private ISelection secondSelection;
    private ICrossover crossover;
    private IMutation mutation;
    private List<GASolution<int[]>> population;

    private BlockingQueue<Task> tasks;
    private BlockingQueue<GASolution<int[]>[]> results;

    private IGAEvaluator<int[]> evaluator;


    public CreateChildThread(BlockingQueue<Task> tasks,
                             BlockingQueue<GASolution<int[]>[]> results, IGAEvaluator<int[]> evaluator, ISelection firstSelection, ISelection secondSelection, ICrossover crossover, IMutation mutation) {
        this.tasks = tasks;
        this.results = results;
        this.evaluator = evaluator;
        this.firstSelection = firstSelection;
        this.secondSelection = secondSelection;
        this.crossover = crossover;
        this.mutation = mutation;
    }

    @Override
    public void run() {
        while (true){
            try {
                Task task = tasks.take();
                int count = task.getCount();
                if (count < 0){
                    break;
                }

                population = task.getPopulation();
                GASolution<int[]>[] solution = new GASolution[count];
                for (int i = 0; i < count; ++i){
                    GASolution<int[]> a = firstSelection.select(population);
                    GASolution<int[]> b = secondSelection.select(population);

                    GASolution<int[]> child = crossover.crossover(a, b);
                    mutation.mutate(child);
                    evaluator.evaluate(child);

                    solution[i] = child;
                }

                results.put(solution);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
