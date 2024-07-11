package gamegene.game;

public interface Player<GameState, Move> {
    Move chooseMove(GameState gameState);
}
