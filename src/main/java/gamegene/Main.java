package gamegene;

import gamegene.gene.FitnessSortedPopulation;
import gamegene.gene.GeneticAlgorithm;
import gamegene.gene.TopPercentageSelectionProcess;
import gamegene.roshambo.WeightedRoshamboPlayer;
import gamegene.roshambo.gene.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        GeneticAlgorithm<WeightedRoshamboPlayer> ga = new GeneticAlgorithm<>();

        Random random = new Random(System.currentTimeMillis());

        List<WeightedRoshamboPlayer> initialPopulation = new ArrayList<>();
        int bound = 48;
        int numPlayers = 100;
        for (int i = 0; i < numPlayers; i++) {

           int rockWeight, paperWeight, scissorsWeight;
           boolean done = false;
           do {
                rockWeight = random.nextInt(bound);
                paperWeight = random.nextInt(bound);
                scissorsWeight = random.nextInt(bound);

                if (rockWeight != 0 && paperWeight != 0 && scissorsWeight != 0) {
                    done = true;
                }
            } while (done == false);
            initialPopulation.add(new WeightedRoshamboPlayer(random, rockWeight, paperWeight, scissorsWeight));
        }

        System.out.println("start time: " + LocalDateTime.now());
        for (WeightedRoshamboPlayer player : initialPopulation) {
            System.out.println(player);
        }
        int numGenerations = 1000;
        int numGamesPerSeries = 3;
        int maxTies = 3;
        double mutationChance = 0.01;
        FitnessSortedPopulation<WeightedRoshamboPlayer> finalPopulation = ga.evolve(
                //new RoshamboTournamentFitnessProcess(numGamesPerSeries, maxTies),
                new RoshamboMarkovFitnessProcess(),

                new RoshamboConvergenceEndProcess(new TopPercentageSelectionProcess<>(0.5)),
                //new RoshamboOptimalEndProcess(new TopPercentageSelectionProcess<>(0.5)),

                new RoshamboProductionProcess(random, mutationChance, bound),

                initialPopulation
        );

        List<WeightedRoshamboPlayer> players = finalPopulation.getSortedPopulation();
        int rank = players.size();
        for (WeightedRoshamboPlayer player : players) {
            double fitness = finalPopulation.getFitness(player);
            System.out.println((rank < 10 ? " " : "") + rank + ": " + player + ", fitness:" + fitness);
            rank--;
        }
        System.out.println("end time: " + LocalDateTime.now());
    }
}
