package com.codecool.quest.logic.actors.monsters;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.actors.Actor;
import com.codecool.quest.logic.actors.Player;

public abstract class Monster extends Actor {

    public Monster(Cell cell) {
        super(cell);
    }

    abstract public void move();

    public boolean isInRange(Player player){
        int x1 = player.getX();
        int y1 = player.getY();
        int x2 = this.getX();
        int y2 = this.getY();

        double distance = Math.sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1));

        if(distance == 1.0)
            return true;

        return false;
    }

}
