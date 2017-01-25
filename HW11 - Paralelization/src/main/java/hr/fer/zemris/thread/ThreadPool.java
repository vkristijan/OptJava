package hr.fer.zemris.thread;

import hr.fer.zemris.generic.ga.EvaluationThread;
import hr.fer.zemris.generic.ga.GAIntArrSolution;
import hr.fer.zemris.generic.ga.GASolution;
import hr.fer.zemris.generic.ga.IGAEvaluator;

import java.util.concurrent.BlockingQueue;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class ThreadPool {
    private static GASolution<int[]> RED_PILL = new GAIntArrSolution();

    private BlockingQueue<GASolution<int[]>> tasks;
    private BlockingQueue<GASolution<int[]>> results;
    private IGAEvaluator<int[]> evaluator;


    private Thread[] workers;

    public ThreadPool(int n, BlockingQueue<GASolution<int[]>> tasks, BlockingQueue<GASolution<int[]>> results,
                      IGAEvaluator<int[]> evaluator) {
        this.tasks = tasks;
        this.results = results;
        this.evaluator = evaluator;

        workers = new Thread[n];
        this.evaluator = evaluator;
        for (int i = 0; i < n; ++i){
            workers[i] = new Thread(new EvaluationThread(tasks, results, this.evaluator));
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
