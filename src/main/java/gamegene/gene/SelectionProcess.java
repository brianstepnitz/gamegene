package gamegene.gene;

import java.util.List;

public interface SelectionProcess<Genome> {

    public List<Genome> select(FitnessSortedPopulation<Genome> fitnessSortedPopulation);

}
