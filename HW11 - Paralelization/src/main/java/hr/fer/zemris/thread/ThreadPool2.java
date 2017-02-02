package hr.fer.zemris.thread;

import hr.fer.zemris.generic.ga.GAIntArrSolution;
import hr.fer.zemris.generic.ga.GASolution;
import hr.fer.zemris.generic.ga.IGAEvaluator;
import hr.fer.zemris.generic.ga.crossover.ICrossover;
import hr.fer.zemris.generic.ga.mutation.IMutation;
import hr.fer.zemris.generic.ga.selection.CreateChildThread;
import hr.fer.zemris.generic.ga.selection.ISelection;

import java.util.concurrent.BlockingQueue;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class ThreadPool2 {
    private static Task RED_PILL = new Task(-1, null);

    private BlockingQueue<Task> tasks;
    private BlockingQueue<GASolution<int[]>[]> results;
    private IGAEvaluator<int[]> evaluator;

    private ISelection firstSelection;
    private ISelection secondSelection;
    private ICrossover crossover;
    private IMutation mutation;

    private Thread[] workers;

    public ThreadPool2(int n, BlockingQueue<Task> tasks, BlockingQueue<GASolution<int[]>[]> results,
                       IGAEvaluator<int[]> evaluator, ISelection firstSelection, ISelection secondSelection, ICrossover crossover, IMutation mutation) {
        this.tasks = tasks;
        this.results = results;
        this.evaluator = evaluator;

        workers = new Thread[n];
        this.evaluator = evaluator;
        this.firstSelection = firstSelection;
        this.secondSelection = secondSelection;
        this.crossover = crossover;
        this.mutation = mutation;
        for (int i = 0; i < n; ++i){
            workers[i] = new Thread(new CreateChildThread(tasks, results, this.evaluator, this.firstSelection,
                    this.secondSelection, this.crossover, this.mutation));
            workers[i].start();
        }
    }

    public void killAll(){
        int n = workers.length;
        try {
            for (int i = 0; i < n; ++i){
                    tasks.put(RED_PILL);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
