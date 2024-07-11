package gamegene.roshambo.gene;

import gamegene.gene.FitnessSortedPopulation;
import gamegene.gene.ProductionProcess;
import gamegene.gene.TopPercentageSelectionProcess;
import gamegene.roshambo.WeightedRoshamboPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

public class RoshamboProductionProcess implements ProductionProcess<WeightedRoshamboPlayer> {

    private final Random random;
    private final double mutationChance;
    private final int bound;

    public RoshamboProductionProcess(Random random, double mutationChance, int bound) {
        this.random = random;
        this.mutationChance = mutationChance;
        this.bound = bound;
    }

    private int mutate(int value) {
        double rollMutation = random.nextDouble();

        if (rollMutation < mutationChance) {
            return random.nextInt(bound);
        }
        else {
            return value;
        }
    }

    private WeightedRoshamboPlayer crossover(WeightedRoshamboPlayer player1, double fitness1, WeightedRoshamboPlayer player2, double fitness2) {
        double fitnessSum = fitness1 + fitness2;

        if (fitnessSum == 0.0) {
            int flip = random.nextInt(2);
            if (flip == 0) {
                return player1.clone();
            }
            else {
                return player2.clone();
            }
        }

        double rollRock = random.nextDouble() * fitnessSum;
        int newRock;
        if (rollRock < fitness1) {
            newRock = player1.getRockWeight();
        } else {
            newRock = player2.getRockWeight();
        }
        newRock = mutate(newRock);

        double rollPaper = random.nextDouble() * fitnessSum;
        int newPaper;
        if (rollPaper < fitness1) {
            newPaper = player1.getPaperWeight();
        } else {
            newPaper = player2.getPaperWeight();
        }
        newPaper = mutate(newPaper);

        double rollScissors = random.nextDouble() * fitnessSum;
        int newScissors;
        if (rollScissors < fitness1) {
            newScissors = player1.getScissorsWeight();
        } else {
            newScissors = player2.getScissorsWeight();
        }
        newScissors = mutate(newScissors);

        WeightedRoshamboPlayer newPlayer;
        if (newRock == 0 && newPaper == 0 && newScissors == 0) {
            double rollClone = random.nextDouble() * fitnessSum;
            if (rollClone < fitness1) {
                newPlayer = player1.clone();
            } else {
                newPlayer = player2.clone();
            }
        } else {
            newPlayer = new WeightedRoshamboPlayer(random, newRock, newPaper, newScissors);
        }

        return newPlayer;
    }

    @Override
    public List<WeightedRoshamboPlayer> produce(FitnessSortedPopulation<WeightedRoshamboPlayer> fitnessSortedPopulation) {
        List<WeightedRoshamboPlayer> selectedPlayers = fitnessSortedPopulation.select(
                new TopPercentageSelectionProcess<WeightedRoshamboPlayer>(0.5)
        );

        List<WeightedRoshamboPlayer> nextGeneration = new ArrayList<>(fitnessSortedPopulation.getSize());
        nextGeneration.addAll(selectedPlayers);

        double totalFitness = selectedPlayers.stream().mapToDouble(player -> fitnessSortedPopulation.getFitness(player)).sum();

        while (nextGeneration.size() < fitnessSortedPopulation.getSize()) {
            WeightedRoshamboPlayer newPlayer = null;

            double roll1 = random.nextDouble() * totalFitness;
            double cumulativeWeight1 = 0;
            for (ListIterator<WeightedRoshamboPlayer> reverseIter1 = selectedPlayers.listIterator(selectedPlayers.size()); reverseIter1.hasPrevious(); ) {
                WeightedRoshamboPlayer player1 = reverseIter1.previous();
                double fitness1 = fitnessSortedPopulation.getFitness(player1);
                if (fitness1 == totalFitness) {
                    // then this is the only player that ever won
                    newPlayer = crossover(player1, fitness1, player1, fitness1);
                    break; // out of reverseIter1 loop
                }

                // otherwise at least one other player has ever won
                cumulativeWeight1 += fitness1;
                if (roll1 < cumulativeWeight1) {
                    double roll2 = random.nextDouble() * (totalFitness - fitness1);
                    double cumulativeWeight2 = 0;
                    for (ListIterator<WeightedRoshamboPlayer> reverseIter2 = selectedPlayers.listIterator(selectedPlayers.size()); reverseIter2.hasPrevious(); ) {
                        WeightedRoshamboPlayer player2 = reverseIter2.previous();
                        if (player2 == player1) {
                            continue; // to next player
                        }
                        double fitness2 = fitnessSortedPopulation.getFitness(player2);
                        cumulativeWeight2 += fitness2;
                        if (roll2 < cumulativeWeight2) {
                            // breed 'em
                            newPlayer = crossover(player1, fitness1, player2, fitness2);
                            break;
                        }
                    } // next reverseIter2

                    break;
                } // end if roll1 < cumulativeWeight1
            } // next reverseIter1

            if (newPlayer == null) {
                // something went wrong
                throw new RuntimeException("totalFitness=" + totalFitness + ", roll1=" + roll1 + "\n" + fitnessSortedPopulation);
            }
            nextGeneration.add(newPlayer);
        } // end while

        return nextGeneration;
    }
}
