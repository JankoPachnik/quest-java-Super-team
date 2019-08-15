package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.CellType;
import com.codecool.quest.logic.Inventory;
import com.codecool.quest.logic.UserInterface;
import com.codecool.quest.logic.interactable.Interactable;
import com.codecool.quest.logic.items.Item;


public class Player extends Actor {

    private Inventory playerInventory;
    private String name;

    private Item currentWeapon = null;
    private Item currentArmor = null;

    private int maxHealth;
    private boolean isAttackBoosted;

    public Actor EnemyActor;

    public Player(Cell cell) {
        super(cell);
        playerInventory = new Inventory();
        this.isEnemy = false;
        this.health = 15;
        maxHealth = 15;
        this.attackPower = 5;
        isAttackBoosted = false;
        Actor enemyActor;
    }

    public String getTileName() {

        return "player";
    }


    public void changeEquippedWeapon(Item newWeapon){
        currentWeapon = newWeapon;
    }

    public void changeEquippedArmor(Item newArmor) { currentArmor = newArmor; }

    public int getEquippedWeaponAttack(){
        return currentWeapon.getAttackModifier();
    }

    @Override
    public int getDefense(){
        if (currentArmor != null)
            return currentArmor.getDefenseModifier();
        else
            return 0;
    }

    public int getAttackModifier(){
        if(isAttackBoosted)
            return 3;
        return 1;
    }

    public Inventory getPlayerInventory(){
        return playerInventory;
    }

    public Cell getNextCell(){
        switch (getDirection()){
            case "up":
                return getCell().getNeighbor(0,-1);
            case "down":
                return getCell().getNeighbor(0,1);
            case "left":
                return getCell().getNeighbor(-1,0);
            case "right":
                return getCell().getNeighbor(1,0);
        }
        return getCell();
    }

    public boolean pickItem(){
        Cell cell = this.getCell();
        if(cell.getItem() != null){
            Item item = cell.getItem();
            item.vanishItem();
            playerInventory.addItem(item);

            if(item.getTileName().equals("sword") || item.getTileName().equals("axe"))
                changeEquippedWeapon(item);
            if(item.getTileName().equals("armor"))
                changeEquippedArmor(item);

            return true;
        }
        return false;
    }

    public boolean canAttack(){
        boolean answer;
        Actor actor = getNextCell().getActor();
        answer = actor != null;
            return answer;

    }

    public String attack(){
        Actor actor = getNextCell().getActor();
//        Actor enemyActor = actor;
        if(actor!= null) {
            if(actor.isEnemy) {
                if (currentWeapon != null) {
                    actor.receiveAttack((getAttackPower() + getEquippedWeaponAttack())*getAttackModifier(), actor.getDefense(), this);

                    currentWeapon.setDurability(-30);

                    if (currentWeapon.getDurability() <= 0) {
                        this.playerInventory.removeItem(this.currentWeapon.getTileName());
                        currentWeapon = this.playerInventory.getWeapon();
                    }
                } else
                    actor.receiveAttack(getAttackPower(), actor.getDefense(), this);
                isAttackBoosted = false;

            }
        }
        if(!this.isDead()) {
            return getAttackMessage();
        }else{
            return "";
        }
    }

    public String useHealthPotion(){ // heals for max 10hp or up to max hp (if potion in inventory)
        if(this.playerInventory.checkForItem("health_potion")){
            this.playerInventory.removeItem("health_potion");
            if(this.health < this.maxHealth - 10){
                this.health += 10;
            }else{
                this.health = this.maxHealth;
            }

            return "Healed 10 HP";
        }

        return "You don't have health potions!";
    }

    public String usePowerPotion(){ // multiplies dmg done by 3 for 1 attack (if potion in inventory)
        if(this.playerInventory.checkForItem("power_potion")){
            this.playerInventory.removeItem("power_potion");
            isAttackBoosted = true;
            return "Next attack will be 3x more powerful!";
        }
        return "You don't have power potions!";
    }

    public String talk(){
        Actor actor = getNextCell().getActor();
        if(actor != null) {
            String message = actor.getNextText();
            if(actor.isEnemy) {
                return String.format("%s doesn't want to talk with you.", actor);
            }
            return message;
        }
        return null;
    }

    private String getAttackMessage(){
        Actor actor = getNextCell().getActor();
        if(actor != null) {
            String message = actor.getNextText();
            if(actor.isEnemy) {
                return message;
            }
            return "Don't do that!";
        }
        return null;
    }

    public String interactWithObject(Interactable interactableItem) {
        if(interactableItem.needsKey()) {
            if (playerInventory.checkForItem("key")) {
                interactableItem.Use();
                playerInventory.removeItem("key");
                return "";
            }
        }else{
            interactableItem.Use();
            Item found = interactableItem.searchForItems();
            if(found != null) {
                playerInventory.addItem(found);
                return "Found a " + found;
            }else{
                return "Found nothing.";
            }
        }

        return "";
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void updatePlayerNewPosition(Player nextLevelPlayer){
        this.changeCell(nextLevelPlayer.getCell());
    }

    public boolean isOnStairs(){
        return this.getCell().getType()== CellType.STAIRS;
    }

}
