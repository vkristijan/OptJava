# OptJava
Homeworks from the Solving Optimization Problems Using Evolutionary Computation Algorithms in Java course.

## Homework 1 - Iterative Algorithms
Solving the Tri SAT problem by using 3 different approaches:

* Checking all the solutions
* Greedy iterative algorithm that tries to change the solution in just one bit. After trying out all the changes, the algorithm will continue with one of the solutions that were better than the previous one.
* Greedy iterative algorithm that changes one bit, but uses a fitness function that will generate the fitness bases on how many times a clause is true. This will result in giving the clauses that are true almost all the time a lower priority.

## Homework 2 - Numeric Optimization
Minimizing and maximizing different functions using:

* Gradient descent
* Newton's method

## Homework 3 - Simulated Annealing
Minimizing a function using simulated annealing with various solution representations and temperature schedules.

## Homework 4 - Genetic Algorithms
* Minimizing a function using an elitist genetic algorithm.
* Solving the "Box Packing Problem" using a steady-state genetic algorithm.

## Homework 5 - Genetic Algorithms
* Maximizing a function that has a large contant area using RAPGA (Relevant Alleles Preserving Genetic Algorithm). Tested different parameters and selection methods for the algorithm.
* Solving the quadratic assignment problem using SASEGASA (Self Adaptive Segregative Genetic Algorithm with Simulated Annealing aspects).

## Homework 6 - Ant Algorithms
Solving the travelling salesman problem using Max-Min Ant System Algorithm.

## Homework 7 - PSO and ClonAlg
Created an Feedforward artificial neural network to classify different species of Iris. The neural network is trained using different approaches:

* Particle Swarm Optimization algorithm using global neigborhood
* Particle Swarm Optimization algorithm using local neigborhood
* Clonal Selection Artificial Immune Systems Algorithm

## Homework 8 - Differential Evolution
Created two artificial neural networks: a Time Delay Neural Network and an Elman Neural Network, in order to predict the next value from the given series of data. Both neural networks are trained using a Differential Evolution algorithm.

## Homework 9 - NSGA
Solved two multiple objective optimization problems using the NSGA algorithm (Non-dominated Sorting Genetic Algorithm).

## Homework 10 - NSGA II
Solved two multiple objective optimization problems using the NSGA II algorithm (Non-dominated Sorting Genetic Algorithm II).

## Homework 11 - Parallelization
Created a thread safe random number generator that uses the default Java Random class from java.util. Created two versions of a genetic algorithm that takes a grayscale image and tries to create it using only a given number of rectangles. In the first version, only the evaluation is done multithreaded. In the second solution the job of creating a new child is done multithreaded (parent selection, crossover, mutation and evaluation), while the main thread is used just to create the tasks and new population.

## Homework 12 - Genetic Programming
Solved a problem of finding the best path for an ant to get the maximum food on a given map using genetic programming. Created GUI using Swing to simulate the movements.
