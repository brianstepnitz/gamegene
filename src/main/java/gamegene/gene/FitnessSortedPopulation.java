package gamegene.gene;

import java.util.*;

public class FitnessSortedPopulation<Genome> {

    private final Map<Genome, Double> genomeToFitnessMap;
    private final List<Genome> sortedPopulation;

    public FitnessSortedPopulation(Map<Genome, Double> genomeToFitnessMap) {
        this.genomeToFitnessMap = genomeToFitnessMap;
        this.sortedPopulation = new ArrayList<>(genomeToFitnessMap.keySet());

        this.sortedPopulation.sort(Comparator.comparingDouble(this.genomeToFitnessMap::get));
    }

    public double getFitness(Genome genome) {
        return this.genomeToFitnessMap.get(genome);
    }

    public int getSize() {
        return this.sortedPopulation.size();
    }

    public List<Genome> getSortedPopulation() {
        return this.sortedPopulation;
    }

    public List<Genome> select(SelectionProcess selectionProcess) {
        return selectionProcess.select(this);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Iterator<Genome> iter = sortedPopulation.iterator(); iter.hasNext();) {
            Genome genome = iter.next();
            sb.append(genome).append(": ").append(this.getFitness(genome));
            if (iter.hasNext()) {
                sb.append("\n");
            }
        }

        return sb.toString();
    }
}
