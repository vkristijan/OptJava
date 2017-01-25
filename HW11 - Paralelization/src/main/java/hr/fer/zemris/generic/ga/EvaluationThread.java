package hr.fer.zemris.generic.ga;

import java.util.concurrent.BlockingQueue;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class EvaluationThread implements Runnable {
    private BlockingQueue<GASolution<int[]>> tasks;
    private BlockingQueue<GASolution<int[]>> results;

    private IGAEvaluator<int[]> evaluator;

    public EvaluationThread(BlockingQueue<GASolution<int[]>> tasks,
                            BlockingQueue<GASolution<int[]>> results, IGAEvaluator<int[]> evaluator) {
        this.tasks = tasks;
        this.results = results;
        this.evaluator = evaluator;
    }

    @Override
    public void run() {
        while (true){
            try {
                GASolution<int[]> solution = tasks.take();
                if (solution.data == null){
                    break;
                }

                evaluator.evaluate(solution);

                results.put(solution);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
