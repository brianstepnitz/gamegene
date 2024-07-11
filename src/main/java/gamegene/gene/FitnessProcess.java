package gamegene.gene;

import java.util.List;

public interface FitnessProcess<Genome> {

    FitnessSortedPopulation<Genome> scoreFitnesses(List<Genome> population);
}
