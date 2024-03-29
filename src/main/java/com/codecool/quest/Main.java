package com.codecool.quest;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.GameMap;
import com.codecool.quest.logic.Inventory;
import com.codecool.quest.logic.MapLoader;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
    GameMap map = MapLoader.loadMap();
    Canvas canvas = new Canvas(
            map.getWidth() * Tiles.TILE_WIDTH,
            map.getHeight() * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    private Label healthLabelText = new Label("Health: ");
    private Label inventoryLabelText = new Label("Inventory:");
    Label healthLabel = new Label();
    Label inventoryLabel = new Label();


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane ui = new GridPane();
        GridPane bottomPane = new GridPane();

        CreateUserInterfaceSideBar(ui);
        CreateUserInterfaceBottomBar(bottomPane);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(canvas);
        borderPane.setRight(ui);
        borderPane.setBottom(bottomPane);

        showInventory();
        createScene(borderPane, primaryStage);
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case UP:
                map.getPlayer().changeDirection("up");
                map.getPlayer().move(0, -1);
                refresh();
                break;
            case DOWN:
                map.getPlayer().changeDirection("down");
                map.getPlayer().move(0, 1);
                refresh();
                break;
            case LEFT:
                map.getPlayer().changeDirection("left");
                map.getPlayer().move(-1, 0);
                refresh();
                break;
            case RIGHT:
                map.getPlayer().changeDirection("right");
                map.getPlayer().move(1,0);
                refresh();
                break;
            case E:
                if(map.getPlayer().pickItem()) {
                    showInventory();
                    break;
                }else if(false) { //check for doors
                    // open doors
                }else{
                    map.getPlayer().attack();
                    refresh();
                }
                refresh();
                break;
        }
    }

    private void refresh() {
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Cell cell = map.getCell(x, y);
                if (cell.getActor() != null) {
                    Tiles.drawTile(context, cell.getActor(), x, y);
                } else if(cell.getItem() != null){
                    Tiles.drawTile(context, cell.getItem(), x, y);
                }
                else {
                    Tiles.drawTile(context, cell, x, y);
                }
            }
        }
        healthLabel.setText("" + map.getPlayer().getHealth());
    }

    private void createScene(BorderPane borderPane, Stage primaryStage){
        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);

        primaryStage.setTitle("Codecool Quest");
        primaryStage.show();
    }

    private void CreateUserInterfaceSideBar(GridPane ui){
        ui.setBorder(new Border(new BorderStroke(Color.SANDYBROWN,
                BorderStrokeStyle.SOLID, new CornerRadii(2), new BorderWidths(8))));
        ui.setPrefWidth(200);
        ui.setBackground(new Background(new BackgroundFill(Color.rgb(89, 58, 68), CornerRadii.EMPTY, Insets.EMPTY)));
        ui.setPadding(new Insets(10));

        healthLabelText.setTextFill(Color.WHITESMOKE);
        healthLabel.setTextFill(Color.WHITESMOKE);
        ui.add(healthLabelText, 0, 0);
        ui.add(healthLabel, 1, 0);

        inventoryLabelText.setTextFill(Color.WHITESMOKE);
        inventoryLabel.setTextFill(Color.WHITESMOKE);
        ui.add(inventoryLabelText, 0, 1);
        ui.add(inventoryLabel, 0, 2);
    }

    private void CreateUserInterfaceBottomBar(GridPane bottomPane){
        bottomPane.setBorder(new Border(new BorderStroke(Color.SANDYBROWN,
                BorderStrokeStyle.SOLID, new CornerRadii(2), new BorderWidths(8))));

        bottomPane.setBackground(new Background(new BackgroundFill(Color.rgb(89, 58, 68), CornerRadii.EMPTY, Insets.EMPTY)));
        bottomPane.setPadding(new Insets(10));
        bottomPane.setHgap(10);

        Button pickItemButton = new Button("Pick Item");
        pickItemButton.setPadding(new Insets(5));
        pickItemButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                map.getPlayer().pickItem();
                showInventory();
            }
        });
        Button attackButton = new Button("Attack");
        attackButton.setPadding(new Insets(5));
        attackButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                map.getPlayer().attack();
                refresh();
            }
        });

        bottomPane.add(pickItemButton,0,0);
        bottomPane.add(attackButton,1,0);
    }

    private void showInventory(){
        Inventory inv = map.getPlayer().getPlayerInventory();
        inventoryLabel.setText(inv.toString());
    }
}
