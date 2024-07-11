package gamegene.gene;

import java.util.List;

public interface ProductionProcess<Genome> {
    List<Genome> produce(FitnessSortedPopulation<Genome> fitnessSortedPopulation);
}
