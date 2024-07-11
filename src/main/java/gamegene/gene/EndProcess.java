package gamegene.gene;

public interface EndProcess<Genome> {

    boolean isEnd(FitnessSortedPopulation<Genome> fitnessSortedPopulation);
}
