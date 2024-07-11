package gamegene.gene;

import java.util.List;

public class GeneticAlgorithm<Genome> {

    public FitnessSortedPopulation<Genome> evolve(
            FitnessProcess<Genome> fitnessProcess,
            EndProcess<Genome> endProcess,
            ProductionProcess<Genome> productionProcess,
            List<Genome> population) {

        FitnessSortedPopulation<Genome> fitnessSortedPopulation;
        while(true) {

            fitnessSortedPopulation = fitnessProcess.scoreFitnesses(population);

            if (endProcess.isEnd(fitnessSortedPopulation)) {
                break;
            }

            population = productionProcess.produce(fitnessSortedPopulation);

        }

        return fitnessSortedPopulation;
    }
}
