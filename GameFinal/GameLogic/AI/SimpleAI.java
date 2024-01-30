package GameLogic.AI;

import GameLogic.DotAndBoxesGame;
import java.util.Random;

public class SimpleAI implements Player {
    private String name = "-AI";
    String name1;
    String name2;
    int gridSize;
    Player player2;

    public SimpleAI(String name1, String name2, int gridSize, Player player2){
        this.name1 = name1;
        this.name2 = name2;
        this.gridSize = gridSize;
        this.player2 = player2;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int makeMove() {
        Random random = new Random();
        DotAndBoxesGame dotAndBoxesGame= new DotAndBoxesGame(name1, name2, gridSize, player2);
        int randomMove = random.nextInt(dotAndBoxesGame.getNumEdges());
        return randomMove;
    }
}