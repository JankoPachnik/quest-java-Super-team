package com.codecool.quest;

import com.codecool.quest.logic.*;
import com.codecool.quest.logic.actors.monsters.Monster;
import com.codecool.quest.logic.items.Item;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
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


    private void onKeyPressed(KeyEvent keyEvent) {
        if(map.getPlayer().getHealth() > 0)
        switch (keyEvent.getCode()) {
            case UP:
                map.getPlayer().changeDirection("up");
                if(changingDirection){
                    UserInterface.getMessageLabel().setText("Attacking direction: Up");
                }else{
                    map.getPlayer().move(0, -1);
//                    moveMonsters();
                    UserInterface.getMessageLabel().setText("");
                    refresh();
                }
                break;
            case DOWN:
                map.getPlayer().changeDirection("down");
                if(changingDirection){
                    UserInterface.getMessageLabel().setText("Attacking direction: Down");
                }else{
                    map.getPlayer().move(0, 1);
//                    moveMonsters();
                    UserInterface.getMessageLabel().setText("");
                    refresh();
                }
                break;
            case LEFT:
                map.getPlayer().changeDirection("left");
                if(changingDirection){
                    UserInterface.getMessageLabel().setText("Attacking direction: Left");
                }else{
                    map.getPlayer().move(-1, 0);
//                    moveMonsters();
                    UserInterface.getMessageLabel().setText("");
                    refresh();
                }
                break;
            case RIGHT:
                map.getPlayer().changeDirection("right");
                if(changingDirection){
                    UserInterface.getMessageLabel().setText("Attacking direction: Right");
                }else {
                    map.getPlayer().move(1, 0);
//                    moveMonsters();
                    UserInterface.getMessageLabel().setText("");
                    refresh();
                }
                break;
            case E:
                if(map.getPlayer().pickItem()) {
                    UserInterface.getMessageLabel().setText(String.format("Picked a %s",map.getPlayer().getPlayerInventory().getLastItem()));
                }else if(map.getPlayer().getNextCell().getInteractable() != null) { //check for doors/chests
                    if(map.getPlayer().getNextCell().getInteractable().needsKey()) {
                        if (map.getPlayer().getPlayerInventory().checkForItem("key")) {
                            map.getPlayer().getNextCell().getInteractable().Use();
                            map.getPlayer().getPlayerInventory().removeItem("key");
                            UserInterface.getMessageLabel().setText("");
                        }
                    }else{
                        map.getPlayer().getNextCell().getInteractable().Use();
                        Item found = map.getPlayer().getNextCell().getInteractable().searchForItems();
                        if(found != null) {
                            map.getPlayer().getPlayerInventory().addItem(found);
                            UserInterface.getMessageLabel().setText(String.format("Found a %s", found));
                        }else{
                            UserInterface.getMessageLabel().setText("Found nothing.");
                        }
                    }
                }else{
                    String message = map.getPlayer().talk();
                    UserInterface.getMessageLabel().setText(message);
                }
                refresh();
                break;
            case R:
                if(!changingDirection){
                    changingDirection = true;
                    UserInterface.getMessageLabel().setText("Choose attack direction and press R to attack");
                }else{
                    String message = map.getPlayer().attack();
                    UserInterface.getMessageLabel().setText(message);
                    changingDirection = false;
                    refresh();
                }
                break;
        }
    }

    private void refresh() {
        showHealthBar();
        moveMonsters();
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
