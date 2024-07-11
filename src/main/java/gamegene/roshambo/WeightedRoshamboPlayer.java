package gamegene.roshambo;

import java.util.Random;

public class WeightedRoshamboPlayer implements RoshamboPlayer {

    private final Random random;

    private final int rockWeight;

    public int getRockWeight() {
        return rockWeight;
    }

    public int getPaperWeight() {
        return paperWeight;
    }

    public int getScissorsWeight() {
        return scissorsWeight;
    }

    private final int paperWeight;

    private final int scissorsWeight;

    private final int weightSum;

    private final double rockChance;

    private final double paperChance;

    public double getRockChance() {
        return rockChance;
    }

    public double getPaperChance() {
        return paperChance;
    }

    public double getScissorsChance() {
        return scissorsChance;
    }

    private final double scissorsChance;

    private static int gcd(int a, int b, int c) {
        int gcd = 1;

        int min = Integer.MAX_VALUE;
        if (a > 0 && a < min) {
            min = a;
        }
        if (b > 0 && b < min) {
            min = b;
        }
        if (c > 0 && c < min) {
            min = c;
        }

        for (int n = min; n > 1; n--) {
            if (a % n == 0 && b % n == 0 && c % n == 0) {
                return n;
            }
        }

        return gcd;
    }

    public WeightedRoshamboPlayer(Random random, int rockWeight, int paperWeight, int scissorsWeight) {
        if (random == null || (rockWeight == 0 && paperWeight == 0 && scissorsWeight == 0) ) {
            throw new IllegalArgumentException();
        }

        this.random = random;

        int gcd = gcd(rockWeight, paperWeight, scissorsWeight);

        this.rockWeight = rockWeight / gcd;
        this.paperWeight = paperWeight / gcd;
        this.scissorsWeight = scissorsWeight / gcd;

        this.weightSum = this.rockWeight + this.paperWeight + this.scissorsWeight;

        this.rockChance = (double) this.rockWeight / (double) weightSum;
        this.paperChance = (double) this.paperWeight / (double) weightSum;
        this.scissorsChance = (double) this.scissorsWeight / (double) weightSum;
    }

    private WeightedRoshamboPlayer(WeightedRoshamboPlayer clonee) {
        this(clonee.random, clonee.getRockWeight(), clonee.getPaperWeight(), clonee.getScissorsWeight());
    }

    @Override
    public Sign chooseMove(History history) {
        int roll = random.nextInt(this.weightSum);

        if (roll < this.rockWeight) {
            return Sign.ROCK;
        }
        else if (roll < this.rockWeight + this.paperWeight) {
            return Sign.PAPER;
        }
        else {
            return Sign.SCISSORS;
        }
    }

    @Override
    public String toString() {
        return "WeightedRoshamboPlayer{" +
                String.format("rockWeight=%3d (%.3f)", rockWeight, (double) rockWeight / (double) weightSum) +
                String.format(", paperWeight=%3d (%.3f)", paperWeight, (double) paperWeight / (double) weightSum) +
                String.format(", scissorsWeight=%3d (%.3f)", scissorsWeight, (double) scissorsWeight / (double) weightSum) +
                '}';
    }

    public WeightedRoshamboPlayer clone() {
        return new WeightedRoshamboPlayer(this);
    }
}
