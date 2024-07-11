package gamegene.gene;

import java.util.List;

public class TopPercentageSelectionProcess<Genome> implements SelectionProcess<Genome> {

    private final double percentage;

    public TopPercentageSelectionProcess(double percentage) {
        if (percentage > 0 && percentage <= 1) {
            this.percentage = percentage;
        }
        else {
            throw new IllegalArgumentException("percentage must be > 0 and <= 1: " + percentage);
        }
    }

    @Override
    public List<Genome> select(FitnessSortedPopulation<Genome> fitnessSortedPopulation) {
        List<Genome> sortedPopulation = fitnessSortedPopulation.getSortedPopulation();
        return sortedPopulation.subList((int) ((double) sortedPopulation.size() * this.percentage), sortedPopulation.size());
    }
}
