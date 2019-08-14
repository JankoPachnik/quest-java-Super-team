package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;

public class HealthPotion extends Item {

    public HealthPotion(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "health_potion";
    }

    @Override
    public String toString() {
        return "HEALTH POTION";
    }
}
