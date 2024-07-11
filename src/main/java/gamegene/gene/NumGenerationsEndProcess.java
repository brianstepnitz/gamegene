package gamegene.gene;

public class NumGenerationsEndProcess<Genome> implements EndProcess<Genome> {

    private final int numGenerations;

    private int timesChecked = 0;

    public NumGenerationsEndProcess(int numGenerations) {
        if (numGenerations > 0) {
            this.numGenerations = numGenerations;
        }
        else {
            throw new IllegalArgumentException("numGenerations must be > 0: " + numGenerations);
        }
    }

    @Override
    public boolean isEnd(FitnessSortedPopulation<Genome> fitnessSortedPopulation) {
        timesChecked++;

        return timesChecked == numGenerations;
    }
}
