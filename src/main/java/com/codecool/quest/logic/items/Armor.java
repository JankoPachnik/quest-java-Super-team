package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;

public class Armor extends Item {

    public Armor(Cell cell) {
        super(cell);
        this.durability = 200;
    }

    @Override
    public String getTileName() {
        return "armor";
    }

    @Override
    public String toString() {
        return "ARMOR";
    }

    @Override
    public int getDefenseModifier() { return 2;}
}
