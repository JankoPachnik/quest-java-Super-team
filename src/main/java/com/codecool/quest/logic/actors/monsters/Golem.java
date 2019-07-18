package com.codecool.quest.logic.actors.monsters;

import com.codecool.quest.logic.Cell;

public class Golem extends Monster {


    public Golem(Cell cell) {
        super(cell);
        this.health = 15;
        this.attackPower = 3;
        this.isEnemy = true;
        setText(new String[]{"Urgh!","Please don't","I have a wife and family!"});
    }

    public void move() {

    }

    @Override
    public String getTileName() {
        return "golem";
    }

    public String getName(){
        return "";
    }
}
