package com.codecool.quest.logic.actors.monsters;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.CustomUtils;
import com.codecool.quest.logic.MapLoader;

public class Ghost extends Monster {

    private int hidingCounter;
    private int showingCounter = 2;
    private Cell hidingCell;

    public Ghost(Cell cell) {
        super(cell);
        this.hidingCell = cell;
        this.health = 8;
        this.attackPower = 2;
        this.isEnemy = true;
        setText(new String[]{"*Puff*","Please don't","I have a wife and family!"});
        this.setHidingCounter();
        this.hideGhost();
    }

    public void move() {

        // Old ghost movement ////////////////
//        incrementMoveCounter();
//
//        if (moveCounter < 5) return;
//
//        boolean isValidCoords = false;
//        Cell nextCell = null;
//
//
//        while (!isValidCoords) {
//            int randomXPos = ThreadLocalRandom.current().nextInt(1, 25);
//            int randomYPos = ThreadLocalRandom.current().nextInt(1, 20);
//
//            try {
//                nextCell = MapLoader.getCurrentMap().getCell(randomXPos, randomYPos);
//
//                boolean isCellFloor = nextCell.getType().equals(CellType.FLOOR);
//                boolean isMoveValid = isMoveValid(nextCell);
//
//                if (isMoveValid && isCellFloor) {
//                    isValidCoords = true;
//                }
//            } catch (NullPointerException e) {
//                continue;
//            }
//        }
//
//        changeCell(nextCell);

//        New ghost movement
        boolean hidingTurnIsOver = hidingCounter == 0;
        boolean fightingTurnsRemaining = showingCounter > 0;

        if ((hidingTurnIsOver) && (fightingTurnsRemaining)) {
            decrementShowingCounter();
            return;
        } else if (!fightingTurnsRemaining) {
            hideGhost();
            setHidingCounter();
            setShowingCounter();
            return;
        }

        showGhostNextToPlayer();
    }

    public void hideGhost() {
        this.cell.setActor(null);
        this.cell = hidingCell;
    }

    public void showGhost(Cell cell) {
        cell.setActor(this);
        this.cell = cell;
    }

    private void setHidingCounter() {
        this.hidingCounter = CustomUtils.getRandomNum(20, 30);
    }

    private void setShowingCounter() {
        this.showingCounter = 2;
    }

    private void showGhostNextToPlayer(){
        decrementHidingCounter();

        if (hidingCounter > 0) return;

        Cell cellLeftToPlayerPosition = MapLoader.getCurrentMap().getPlayer().getCell().getNeighbor(-1, 0);

        showGhost(cellLeftToPlayerPosition);
    }

    private void decrementHidingCounter() {
        this.hidingCounter--;
    }

    private void decrementShowingCounter() {
        this.showingCounter--;
    }

    @Override
    public String getTileName() {
        return "ghost";
    }

    public String getName(){
        return "";
    }

    @Override
    public String toString(){
        return "GHOST";
    }
}
