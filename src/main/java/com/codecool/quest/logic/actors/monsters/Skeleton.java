package com.codecool.quest.logic.actors.monsters;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.CellType;
import com.codecool.quest.logic.CustomUtils;

public class Skeleton extends Monster {

    enum MoveDirection {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    private MoveDirection currentDirection;
    private String moveType;

    public Skeleton(Cell cell) {
        super(cell);
        this.isEnemy = true;
        this.isHostile = true;
        this.health = 10;
        this.attackPower = 5;
        setText(new String[]{"*Kling*","Please don't","I have a wife and family!"});

        initiateRandomMovementDirection();

    }

    public void move() {

        Cell nextCell = getNextCellDirectionBased();

        checkPotentialCollision(nextCell);

        nextCell = getNextCellDirectionBased();

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

    private void initiateRandomMovementDirection() {
        int randomChance = CustomUtils.getRandomNum(0, 10);

        if(randomChance < 5) {
            this.moveType = "horizontal";
            this.currentDirection = MoveDirection.RIGHT;
        } else {
            this.moveType = "vertical";
            this.currentDirection = MoveDirection.UP;
        }
    }

    private void switchMovementDirection() {
        switch(this.currentDirection) {
            case DOWN:
                this.currentDirection = MoveDirection.UP;
                break;
            case UP:
                this.currentDirection = MoveDirection.DOWN;
                break;
            case LEFT:
                this.currentDirection = MoveDirection.RIGHT;
                break;
            case RIGHT:
                this.currentDirection = MoveDirection.LEFT;
                break;
        }
    }

    private Cell getNextCellDirectionBased() {
        Cell nextCell;

        if (this.currentDirection.equals(MoveDirection.DOWN)) {
            nextCell = cell.getNeighbor(0,1);
        } else if (this.currentDirection.equals(MoveDirection.UP)) {
            nextCell = cell.getNeighbor(0,-1);
        } else if (this.currentDirection.equals(MoveDirection.LEFT)) {
            nextCell = cell.getNeighbor(-1,0);
        } else {
            nextCell = cell.getNeighbor(1,0);
        }

        return nextCell;
    }

    private void checkPotentialCollision(Cell nextCell) {
        if (nextCell.getType().equals((CellType.WALL)) ||
                nextCell.getType().equals((CellType.STAIRS)) ||
                nextCell.getInteractable() != null ||
                nextCell.getActor() != null) {
            switchMovementDirection();
        }
    }
}
