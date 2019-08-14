package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;

public class PowerPotion extends Item {

    public PowerPotion(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "power_potion";
    }

    @Override
    public String toString() {
        return "POWER POTION";
    }
}
