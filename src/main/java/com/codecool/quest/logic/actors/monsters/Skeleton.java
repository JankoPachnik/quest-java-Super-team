package com.codecool.quest.logic.actors.monsters;

import com.codecool.quest.logic.Cell;
import java.util.concurrent.ThreadLocalRandom;

public class Skeleton extends Monster {

    public Skeleton(Cell cell) {
        super(cell);
        this.isEnemy = true;
        this.health = 10;
        this.attackPower = 5;
        setText(new String[]{"*Kling*","Please don't","I have a wife and family!"});
    }

    public void move() {

        boolean isValidCoords = false;
        Cell nextCell = null;

//        if skeleton is surrounded from every side > it may get stuck
        while (!isValidCoords) {
            int randomXPos = ThreadLocalRandom.current().nextInt(-1, 1 + 1);
            int randomYPos = ThreadLocalRandom.current().nextInt(-1, 1 + 1);

            nextCell = cell.getNeighbor(randomXPos, randomYPos);

            boolean isMoveValid = isMoveValid(nextCell);

            if (isMoveValid) {
                isValidCoords = true;
            }
        }

        changeCell(nextCell);
    }

    @Override
    public String getTileName() {
        return "skeleton";
    }

    public String getName(){
        return "Skeleton";
    }

    @Override
    public String toString() {
        return "SKELETON";
    }
}
