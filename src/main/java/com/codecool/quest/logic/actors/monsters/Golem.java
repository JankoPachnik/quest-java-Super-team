package com.codecool.quest.logic.actors.monsters;

import com.codecool.quest.logic.Cell;

public class Golem extends Monster {

    public Golem(Cell cell) {
        super(cell);
        this.health = 30;
        this.attackPower = 5;
        this.isEnemy = true;
        this.isHostile = false;
        setText(new String[]{"*Badum*","Please don't","I have a wife and family!"});
    }

    public void move() {}

    @Override
    public String getTileName() {
        return "golem";
    }

    public String getName(){
        return "Golem";
    }

    @Override
    public String toString() {
        return "GOLEM";
    }
}
