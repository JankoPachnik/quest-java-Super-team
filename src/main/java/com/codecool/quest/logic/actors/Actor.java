package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.CellType;
import com.codecool.quest.logic.interfaces.Drawable;
import com.codecool.quest.logic.interfaces.Movable;

import java.util.Arrays;
import java.util.List;

public abstract class Actor implements Drawable, Movable {
    protected Cell cell;
    protected int health;
    private String[] cheatNames = {"Michał", "Piotrek", "Janek", "Olek", "Daniel"};
    protected int attackPower;
    protected int defense;
    private String direction;
    protected boolean isEnemy;
    private boolean dead;
    protected String[] text;
    protected int sentenceCounter;

    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
        this.direction = "up";
        this.dead = false;
        this.defense = 0;
    }

//    public getActorName



    public void move(int dx, int dy) {
        Cell nextCell = cell.getNeighbor(dx, dy);

        List<String> cheatNamesList = Arrays.asList(cheatNames);
        String actorName = cell.getActor().getName();

        boolean isProvidedCheat = cheatNamesList.contains(actorName);
        if (isMoveValid(nextCell) || isProvidedCheat) {
            changeCell(nextCell);
        }

    }

    protected boolean isMoveValid(Cell nextCell) {
        boolean isNextCellWall = nextCell.getType().equals(CellType.WALL);
        boolean isNextCellActor = nextCell.getActor() != null;

        boolean canMoveThrough = true;
        if(nextCell.getInteractable() != null){
            if(!nextCell.getInteractable().isPassable()){
                canMoveThrough = false;
            }
        }

        return !isNextCellWall && !isNextCellActor && canMoveThrough;
    }

    protected void changeCell(Cell nextCell) {
        cell.setActor(null);
        nextCell.setActor(this);
        cell = nextCell;
    }

    public int getHealth() {
        return health;
    }

    public boolean isDead(){ return dead; }

    public void changeHealth(int change){
        health += change;
    }

    public int getAttackPower(){
        return attackPower;
    }

    public int getDefense(){
        return defense;
    }

    public String getDirection(){
        return direction;
    }

    public void changeDirection(String newDirection){
        direction = newDirection;
    }

    public Cell getCell() {
        return cell;
    }

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }

    public Cell getNextCell(){
        switch (getDirection()){
            case "down":
                return getCell().getNeighbor(0,1);
            case "up":
                return getCell().getNeighbor(0,-1);
            case "left":
                return getCell().getNeighbor(-1,0);
            case "right":
                return getCell().getNeighbor(1,0);
        }
        return getCell();
    }

    public abstract String getName();

    // public void setOnChange(...)

    public void receiveAttack(int receivedDamage, int defenseModifier, Actor player){
        changeHealth(-(receivedDamage-defenseModifier));

        // onHealthChange();
        if (health<=0){
            die();
        } else{
            if( player != null)
                this.attackPlayer(player);
        }
    }

    public void attackPlayer(Actor player){
        player.receiveAttack(getAttackPower(), player.getDefense(), null);
    }

    public void die(){
        getCell().setActor(null);
        cell = null;
        dead = true;
    }

    public void setText(String[] text){
        this.text = text;
    }

    public String getNextText(){
        //int index = (int)(Math.random() * text.length);
        String temp = text[sentenceCounter];
        sentenceCounter++;
        if(sentenceCounter >= text.length){
            sentenceCounter = 0;
        }
        return temp;
    }
}
