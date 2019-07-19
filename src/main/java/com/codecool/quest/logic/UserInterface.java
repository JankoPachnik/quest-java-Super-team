package com.codecool.quest.logic;

import com.codecool.quest.logic.actors.monsters.Monster;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
        healthBarText.setTextFill(Color.WHITESMOKE);
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

    public void showPlayerHealthBar(int health){
        lab.setText(String.valueOf(health));
        lab.setTextFill(Color.WHITESMOKE);
    }

    public void showPlayerName(String name){
        healthBarText.setText(name + ": ");
        lab.setTextFill(Color.WHITESMOKE);
    }

    public void showInventory(GameMap map){
        Inventory inv = map.getPlayer().getPlayerInventory();
        inventoryLabel.setText(inv.toString());
    }


    private void createNameField(GameMap map) {
        Label label1 = new Label("Name:");
        label1.setTextFill(Color.INDIANRED);
        TextField textField = new TextField ();
        textField.setText("Wojo69");
        map.getPlayer().setName(textField.getText()); //set name as default
        textField.setFocusTraversable(false);
        textField.getStyleClass().add("name-field");

        Button submit = new Button("«");
        submit.getStyleClass().add("btn");
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                String name = textField.getText();
                map.getPlayer().setName(name);

                showPlayerName(name); //at top pane
            }
        });

        ui.add(label1, 0, 0);
        ui.add(textField, 0, 2);
        ui.add(submit, 1, 2);
    }
}
