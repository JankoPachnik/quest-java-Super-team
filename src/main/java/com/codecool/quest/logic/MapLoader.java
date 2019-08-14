package com.codecool.quest.logic;

import com.codecool.quest.logic.actors.NPC;
import com.codecool.quest.logic.actors.NPCType;
import com.codecool.quest.logic.actors.Player;
import com.codecool.quest.logic.actors.monsters.Ghost;
import com.codecool.quest.logic.actors.monsters.Golem;
import com.codecool.quest.logic.actors.monsters.Skeleton;
import com.codecool.quest.logic.interactable.Chest;
import com.codecool.quest.logic.interactable.Doors;
import com.codecool.quest.logic.items.*;

import java.io.InputStream;
import java.util.Scanner;

public class MapLoader {
    private static GameMap map;
    private static int currentLevel = 1;

    public static GameMap getCurrentMap() {
        return MapLoader.map;
    }

    public static GameMap loadMap() {
        InputStream is = MapLoader.class.getResourceAsStream("/map"+currentLevel+".txt");
        Scanner scanner = new Scanner(is);
        int width = scanner.nextInt();
        int height = scanner.nextInt();

        scanner.nextLine(); // empty line

        GameMap map = new GameMap(width, height, CellType.EMPTY);
        for (int y = 0; y < height; y++) {
            String line = scanner.nextLine();
            for (int x = 0; x < width; x++) {
                if (x < line.length()) {
                    Cell cell = map.getCell(x, y);
                    switch (line.charAt(x)) {
                        case ' ': //Empty
                            cell.setType(CellType.EMPTY);
                            break;
                        case '#': //Wall
                            cell.setType(CellType.WALL);
                            break;
                        case '.': //Floor
                            cell.setType(CellType.FLOOR);
                            break;
                        case '\\': //Stairs
                            cell.setType(CellType.STAIRS);
                            break;
                        case 's': //Monster Skeleton
                            cell.setType(CellType.FLOOR);
                            map.addMonster(new Skeleton(cell));
                            break;
                        case 'o': //Monster Golem
                            cell.setType(CellType.FLOOR);
                            map.addMonster(new Golem(cell));
                            break;
                        case 'g': //Monster Ghost
                            cell.setType(CellType.EMPTY);
                            map.addMonster(new Ghost(cell));
                            break;
                        case '%': //Doors
                            cell.setType(CellType.DOORS);
                            new Doors(cell);
                            break;
                        case 'C': //closed Chest
                            cell.setType(CellType.FLOOR);
                            new Chest(cell);
                            break;
                        case 'c': //open Chest
                            cell.setType(CellType.FLOOR);
                            new Chest(cell).Use();
                            break;
                        case '@': //Player
                            cell.setType(CellType.FLOOR);
                            map.setPlayer(new Player(cell));
                            break;
                        case 'W': //NPC Wizard
                            cell.setType(CellType.FLOOR);
                            new NPC(cell, NPCType.WIZARD);
                            break;
                        case 'G': //NPC Guard
                            cell.setType(CellType.FLOOR);
                            new NPC(cell, NPCType.GUARD);
                            break;
                        case '!': //Item Sword
                            cell.setType(CellType.FLOOR);
                            new Sword(cell);
                            break;
                        case '?': //Item Axe
                            cell.setType(CellType.FLOOR);
                            new Axe(cell);
                            break;
                        case 'a': //Item Armor
                            cell.setType(CellType.FLOOR);
                            new Armor(cell);
                            break;
                        case 'k': //Item Key
                            cell.setType(CellType.FLOOR);
                            new Key(cell);
                            break;
                        case '+': //Item Health Potion
                            cell.setType(CellType.FLOOR);
                            new HealthPotion(cell);
                            break;
                        case '=': //Item Power Potion
                            cell.setType(CellType.FLOOR);
                            new PowerPotion(cell);
                            break;
                        default:
                            throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                    }
                }
            }
        }

        MapLoader.map = map;

        return map;
    }

    public static GameMap loadNextLevel(){
        currentLevel++;
        return loadMap();
    }
}
