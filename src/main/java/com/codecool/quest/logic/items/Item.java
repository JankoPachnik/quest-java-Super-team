package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.interfaces.Drawable;

public abstract class Item implements Drawable {

    protected int durability = 100;
    private Cell cell;

    public Item(Cell cell) { //Constructor, it makes items based on arguments
        this.cell = cell;
        if(cell != null) this.cell.setItem(this);
    }

    public void vanishItem(){
        this.cell.setItem(null);
        this.cell = null;
    }

    public int getAttackModifier() {
        return 0;
    }

    public int getDefenseModifier() { return 0; }

    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability += durability;
    }
}


