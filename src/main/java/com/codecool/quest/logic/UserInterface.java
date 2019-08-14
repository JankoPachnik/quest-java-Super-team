package com.codecool.quest.logic;

import com.codecool.quest.Tiles;
import com.codecool.quest.logic.actors.monsters.Monster;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
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

    PixelReader reader;
    Image image;
    WritableImage newImage;
    ImageView image1;
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


        image = new Image("/health_bar2.png") ;
        reader = image.getPixelReader();
        int width = 68;
        newImage = new WritableImage(reader, 1, 1, 68, 15);
        image1 = new ImageView(newImage);
    }

    public void CreateUserInterfaceBottomBar(){
        bottomPane.getStyleClass().add("ui-pane");
        bottomPane.setHgap(10);

        messageLabel.setTextFill(Color.WHITESMOKE);
        bottomPane.add(messageLabel, 0, 0);
    }

//    public void displayMonsterHealthBar(Monster monster){
//        Label lab = new Label(monster.getTileName());
//        ui.add(lab, 0, 0);
//    }

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
//        showPlayerName(playerName); //at topBa

        textField.setFocusTraversable(false);
        textField.getStyleClass().add("name-field");

        Button submit = new Button("«");
        submit.getStyleClass().add("btn");
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                String name = textField.getText();
                map.getPlayer().setName(name);
                showPlayerName(name); //at topBar
            }
        });

        ui.add(label1, 0, 0);
        ui.add(textField, 0, 2);
        ui.add(submit, 1, 2);
    }

//    Image image = new Image("/health_bar2.png") ;
//    public PixelReader reader = image.getPixelReader();
//    int width = 68;
//    WritableImage newImage = new WritableImage(reader, 1, 1, 68, 15);


    public void renderHpBar(WritableImage newImage, int width){
        PixelWriter pw = newImage.getPixelWriter();
        pw.setPixels(1,1, 51, 10, reader, 1, 1);
        ImageView image2 = new ImageView(newImage);
        image1 = image2;
    }



    public void showPlayerHealthBar(int health){

////        ImageView image = new ImageView(Tiles.getTileset());
////        ImageView image = new ImageView("/health_bar2.png");
//          Image image = new Image("/health_bar2.png") ;
////        Rectangle2D tile = new Rectangle2D( Tiles.getTile("heart").x,
////                Tiles.getTile("heart").y, Tiles.getTile("heart").x + 34,  Tiles.getTile("heart").y + 34);
////       image.setImage();
//
//        PixelReader reader = image.getPixelReader();

        int width = 68;
        switch (health) {
            case 15:
                width = 68;
                break;
            case 10:
                width = 51;
                break;
            case 5:
                width = 34;
                break;
            case 0:
                width = 17;
                break;
        }

        WritableImage newImage2 = new WritableImage(reader, 1, 1, width, 15);
        ImageView image2 = new ImageView(newImage2);

//        renderHpBar(newImage,width);

        Label label1 = new Label("Search");
//        label1.setGraphic(image);
//        ImageView image1 = new ImageView(newImage);
//        label1.setGraphic(image1);

        ui.add(label1, 0, 0);
        lab.setText(String.valueOf(health));
        lab.setTextFill(Color.WHITESMOKE);
        lab.setGraphic(image2);

    }

    private void showPlayerName(String name){
        healthBarText.setText(name + ": ");
        healthBarText.setTextFill(Color.WHITESMOKE);
    }
}
