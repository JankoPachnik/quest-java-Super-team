package com.codecool.quest;

import com.codecool.quest.logic.*;
import com.codecool.quest.logic.actors.Player;
import com.codecool.quest.logic.actors.monsters.Monster;
import com.codecool.quest.logic.interactable.Interactable;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.List;


public class Main extends Application {
    private GameMap map = MapLoader.loadMap();
    private Canvas canvas = new Canvas(
            map.getWidth() * Tiles.TILE_WIDTH,
            map.getHeight() * Tiles.TILE_WIDTH);

    private GraphicsContext context = canvas.getGraphicsContext2D();

    private UserInterface UserInterface = new UserInterface();

    private boolean changingDirection = false;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        UserInterface.CreateUserInterfaceSideBar(map);
        UserInterface.CreateUserInterfaceBottomBar();
        UserInterface.CreateUserInterfaceTopBar();

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(canvas);
        borderPane.setRight(UserInterface.ui);
        borderPane.setBottom(UserInterface.bottomPane);
        borderPane.setTop(UserInterface.topPane);

        UserInterface.showInventory(map);
        createScene(borderPane, primaryStage);
    }


    private void createScene(BorderPane borderPane, Stage primaryStage){
        Scene scene = new Scene(borderPane);
        scene.getStylesheets().add("style.css");

        primaryStage.setScene(scene);
        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);

        primaryStage.setTitle("Codecool Quest");
        primaryStage.show();
    }

    private void moveMonsters() {
        List<Monster> monsters = map.getMonsters();

        for (Monster monster : monsters) {
            if(!monster.isDead()) monster.move();
        }
    }

    private void movePlayer(String direction, int xDir, int yDir) {
        Player player = map.getPlayer();
        player.changeDirection(direction);
        if(changingDirection){
            UserInterface.getMessageLabel().setText("Attacking direction: " + direction);
        }else{
            player.move(xDir, yDir);
            moveMonsters();
            UserInterface.getMessageLabel().setText("");
            if(player.isOnStairs()){
                map = MapLoader.loadNextLevel();
                player.updatePlayerNewPosition(map.getPlayer());
                map.setPlayer(player);
            }
        }
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        Player player = map.getPlayer();
        if(player.getHealth() > 0) {
            Inventory playerInventory = player.getPlayerInventory();
            Interactable interactableItem = player.getNextCell().getInteractable();
            switch (keyEvent.getCode()) {
                case UP:
                    movePlayer("up", 0, -1);
                    break;
                case DOWN:
                    movePlayer("down", 0, 1);
                    break;
                case LEFT:
                    movePlayer("left", -1, 0);
                    break;
                case RIGHT:
                    movePlayer("right", 1, 0);
                    break;
                case E:
                    if (player.pickItem()) {
                        UserInterface.getMessageLabel().setText(String.format("Picked a %s", playerInventory.getLastItem()));
                    } else if (interactableItem != null) { //check for doors/chests
                        String interactionMessage = player.interactWithObject(interactableItem);
                        UserInterface.getMessageLabel().setText(interactionMessage);
                    } else {
                        String message = player.talk();
                        UserInterface.getMessageLabel().setText(message);
                    }
                    break;
                case R:
                    if (!changingDirection) {
                        changingDirection = true;
                        UserInterface.getMessageLabel().setText("Choose attack direction and press R to attack");
                    } else {
                        String message = player.attack();
                        UserInterface.getMessageLabel().setText(message);
                        changingDirection = false;
                    }
                    break;
                case H:
                    String message = player.useHealthPotion();
                    UserInterface.getMessageLabel().setText(message);
                    break;
                case J:
                    String msg = player.usePowerPotion();
                    UserInterface.getMessageLabel().setText(msg);
                    break;
            }
        }
        refresh();
    }

    private void refresh() {
        showHealthBar();
        showInventory();
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Cell cell = map.getCell(x, y);
                if (cell.getActor() != null) {
                    Tiles.drawTile(context, cell.getActor(), x, y);
                } else if(cell.getItem() != null){
                    Tiles.drawTile(context, cell.getItem(), x, y);
                } else if(cell.getInteractable() != null){
                    Tiles.drawTile(context, cell.getInteractable(), x, y);
                }
                else {
                    Tiles.drawTile(context, cell, x, y);
                }
            }
        }
        UserInterface.getHealthLabel().setText("" + map.getPlayer().getHealth());
        showInventory();
        if(map.getPlayer().getHealth() <= 0){
            UserInterface.getMessageLabel().setText("YOU ARE DEAD!!");
        }
    }

    private void showHealthBar(){
        int playerHealth = map.getPlayer().getHealth();
        UserInterface.showPlayerHealthBar(playerHealth);
    }

    private void showInventory(){
        Inventory inv = map.getPlayer().getPlayerInventory();
        UserInterface.getInventoryLabel().setText(inv.toString());
    }
}
