package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;

public class Axe extends Item {

    public Axe(Cell cell) {
        super(cell);
        this.durability = 90;
    }

    @Override
    public String getTileName() {
        return "axe";
    }

    @Override
    public String toString() {
        return "AXE";
    }

    @Override
    public int getAttackModifier() {
        return 10;
    }

    // sword check durability method and delete sword method

}
