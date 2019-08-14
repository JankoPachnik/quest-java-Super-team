package com.codecool.quest.logic;

import com.codecool.quest.logic.items.Item;
import java.util.ArrayList;

public class Inventory {

    private ArrayList<Item> inventory = new ArrayList<>();

    public Inventory(){
    }

    @Override
    public String toString(){
        StringBuilder itemsSB = new StringBuilder();
        if(!(inventory.size() == 0)){
            for(Item item: inventory){
                itemsSB.append(" » ");
                itemsSB.append(item.toString());
                itemsSB.append("\n");
            }
        }else{
            itemsSB.append(" » EMPTY");
        }
        return itemsSB.toString();
    }

    public void addItem(Item itemToAdd){
        if(itemToAdd != null) inventory.add(itemToAdd);
    }

    public void removeItem(Item itemToRemove){
        inventory.remove(itemToRemove);
    }

    public void removeItem(String itemName){
        for (int i=0; i<inventory.size();i++){
            if( itemName.equals(inventory.get(i).getTileName())){
                inventory.remove(i);
                break;
            }
        }
    }

    public Item getLastItem(){
        return inventory.get(inventory.size()-1);
    }

    public Boolean checkForItem(String itemName){
        Boolean isItem = false;
        for (int i=0; i<inventory.size();i++) {
            if (itemName.equals(inventory.get(i).getTileName())) {
                isItem = true;
            }
        }
        return isItem;
    }

    public Item getItem(String itemName){
        for (int i=0; i<inventory.size();i++) {
            if (itemName.equals(inventory.get(i).getTileName())) {
                return inventory.get(i);
            }
        }
        return null;
    }

    public Item getWeapon(){
        Item weapon = this.getItem("sword");
        if(weapon == null) weapon = this.getItem("axe");
        return weapon;
    }

}
