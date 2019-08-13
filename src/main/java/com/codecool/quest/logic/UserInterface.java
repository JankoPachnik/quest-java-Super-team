package com.codecool.quest.logic;

import com.codecool.quest.Tiles;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;


public class UserInterface {

    private Label healthLabelText = new Label("Health: ");
    private Label healthLabel = new Label();
    private Label inventoryLabelText = new Label("»»»INVENTORY«««");
    private Label inventoryLabel = new Label();
    private Label messageLabel = new Label();
    private Label healthBarText = new Label();
    private Label lab = new Label();

    public GridPane topPane = new GridPane();
    public GridPane bottomPane = new GridPane();
    public GridPane ui = new GridPane();


    public UserInterface(){
        topPane.add(healthBarText, 0, 0);
        topPane.add(lab, 1, 0);
    }

    public Label getMessageLabel() {
        return messageLabel;
    }

    public Label getHealthLabel() {
        return healthLabel;
    }

    public Label getInventoryLabel() {
        return inventoryLabel;
    }

    public void CreateUserInterfaceTopBar() {
        topPane.getStyleClass().add("top-ui-pane");

    }

    public void CreateUserInterfaceSideBar(GameMap map){
        createNameField(map);
        ui.getStyleClass().add("ui-pane");

        healthLabelText.setTextFill(Color.INDIANRED);
        healthLabel.setTextFill(Color.WHITESMOKE);
        ui.add(healthLabelText, 0, 4);
        ui.add(healthLabel, 1, 4);

        inventoryLabelText.setTextFill(Color.INDIANRED);
        inventoryLabel.setTextFill(Color.LIGHTGOLDENRODYELLOW);
        ui.add(inventoryLabelText, 0, 6);
        ui.add(inventoryLabel, 0, 7);
        Label lab = new Label("»»»»»»»»-«««««««");
        lab.setTextFill(Color.INDIANRED);
        ui.add(lab, 0, 20);
    }

    public void CreateUserInterfaceBottomBar(){
        bottomPane.getStyleClass().add("ui-pane");
        bottomPane.setHgap(10);

        messageLabel.setTextFill(Color.WHITESMOKE);
        bottomPane.add(messageLabel, 0, 0);
    }


    public void showInventory(GameMap map){
        Inventory inv = map.getPlayer().getPlayerInventory();
        inventoryLabel.setText(inv.toString());
    }


    private void createNameField(GameMap map) {
        Label nameLabel = new Label("Name:");
        nameLabel.setTextFill(Color.INDIANRED);

        TextField textField = new TextField ("Wojo69");
        String playerName = textField.getText();

        map.getPlayer().setName(playerName); //set default name to player
        showPlayerName(playerName); //at topBar

        //prevents arrow keys from selecting buttons
        textField.setFocusTraversable(false);
        textField.getStyleClass().add("name-field");

        Button submit = new Button("«");
        submit.getStyleClass().add("btn");
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                String newName = textField.getText();

                map.getPlayer().setName(newName);
                showPlayerName(newName); //at topBar
            }
        });

        ui.add(nameLabel, 0, 0);
        ui.add(textField, 0, 2);
        ui.add(submit, 1, 2);
    }

    public void showPlayerHealthBar(int health){

        ImageView image = new ImageView(Tiles.getTileset());
//        Rectangle2D tile = new Rectangle2D( Tiles.getTile("heart").x,
//                Tiles.getTile("heart").y, Tiles.getTile("heart").x + 34,  Tiles.getTile("heart").y + 34);
//       image.setImage();
        Label label1 = new Label("Search");
        label1.setGraphic(image);

        ui.add(label1, 0, 0);
        lab.setText(String.valueOf(health));
        lab.setTextFill(Color.WHITESMOKE);
    }

    private void showPlayerName(String name){
        healthBarText.setText(name + ": ");
        healthBarText.setTextFill(Color.WHITESMOKE);
    }
}
