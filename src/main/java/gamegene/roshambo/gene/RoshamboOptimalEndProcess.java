package gamegene.roshambo.gene;

import gamegene.gene.EndProcess;
import gamegene.gene.FitnessSortedPopulation;
import gamegene.gene.SelectionProcess;
import gamegene.roshambo.WeightedRoshamboPlayer;

import java.util.List;

public class RoshamboOptimalEndProcess implements EndProcess<WeightedRoshamboPlayer> {

    private final SelectionProcess<WeightedRoshamboPlayer> selectionProcess;

    public RoshamboOptimalEndProcess(SelectionProcess<WeightedRoshamboPlayer> selectionProcess) {
        if (selectionProcess != null) {
            this.selectionProcess = selectionProcess;
        }
        else {
            throw new IllegalArgumentException("selectionProcess must be nonnull");
        }
    }

    private int timesChecked = 0;

    @Override
    public boolean isEnd(FitnessSortedPopulation<WeightedRoshamboPlayer> fitnessSortedPopulation) {
        timesChecked++;
        System.out.println("timesChecked=" + timesChecked);

        List<WeightedRoshamboPlayer> selection = fitnessSortedPopulation.select(this.selectionProcess);
        for (WeightedRoshamboPlayer weightedRoshamboPlayer : selection) {
            if (weightedRoshamboPlayer.getPaperWeight() != weightedRoshamboPlayer.getRockWeight()
                    || weightedRoshamboPlayer.getPaperWeight() != weightedRoshamboPlayer.getScissorsWeight()
                    || weightedRoshamboPlayer.getRockWeight() != weightedRoshamboPlayer.getScissorsWeight()) {

                return false;
            }
        }

        return true;
    }
}
