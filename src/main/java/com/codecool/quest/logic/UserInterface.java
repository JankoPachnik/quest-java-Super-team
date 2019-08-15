package com.codecool.quest.logic;

import com.codecool.quest.Tiles;
import com.codecool.quest.logic.actors.Player;
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

//    private Label healthLabelText = new Label("Health: ");
//    private Label healthLabel = new Label();
    private Label inventoryLabelText = new Label("»»»INVENTORY«««");
    private Label inventoryLabel = new Label();
    private Label messageLabel = new Label();
    private Label healthBarText = new Label();
    private Label labPlayerName = new Label();
    public Label labMonsterName = new Label();
    private Label labPlayer = new Label();
    public Label labMonster = new Label();

    PixelReader readerPlayer;
    Image imagePlayer;
    PixelReader readerMonster;
    Image imageMonster;
    WritableImage newImage;
    ImageView image1;
    public GridPane topPane = new GridPane();
    public GridPane bottomPane = new GridPane();
    public GridPane ui = new GridPane();


    public UserInterface(){
//        topPane.add(healthBarText, 0, 0);
        topPane.add(labPlayerName, 0, 0);
        topPane.add(labPlayer, 1, 0);
        topPane.add(labMonsterName, 0,1);
        topPane.add(labMonster, 1,1);
      
    }

    public Label getMessageLabel() {
        return messageLabel;
    }

//    public Label getHealthLabel() {
//        return healthLabel;
//    }

    public Label getInventoryLabel() {
        return inventoryLabel;
    }

    public void CreateUserInterfaceTopBar() {
        topPane.getStyleClass().add("top-ui-pane");

    }

    public void CreateUserInterfaceSideBar(GameMap map){
        createNameField(map);
        ui.getStyleClass().add("ui-pane");

        inventoryLabelText.setTextFill(Color.INDIANRED);
        inventoryLabel.setTextFill(Color.LIGHTGOLDENRODYELLOW);
        ui.add(inventoryLabelText, 0, 6);
        ui.add(inventoryLabel, 0, 7);
        Label lab = new Label("»»»»»»»»-«««««««");
        lab.setTextFill(Color.INDIANRED);
        ui.add(lab, 0, 20);


        imagePlayer = new Image("/health_bar_player.png") ;
        readerPlayer = imagePlayer.getPixelReader();
        imageMonster = new Image("/health_bar_monster.png") ;
        readerMonster = imageMonster.getPixelReader();
        int width = 68;
        newImage = new WritableImage(readerPlayer, 1, 1, 68, 15);
        image1 = new ImageView(newImage);
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
        Label label1 = new Label("Name:");
        label1.setTextFill(Color.INDIANRED);

        TextField textField = new TextField ();
        textField.setText("Wojo69");

        map.getPlayer().setName(textField.getText()); //set name as default
        showPlayerName(map.getPlayer().getName()); //at topBa
//        showMonsterName(name);

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


    public void showPlayerHealthBar(int health){ //, String name


        int width;

        switch (CustomUtils.RangePlayer.getFrom(health)) {

            case FIFTEEN_PLAYER:
                width = 68;
                break;

            case TEN_PLAYER:
                width = 51;
                break;

            case FIVE_PLAYER:
                width = 34;
                break;

            case OTHER:
            default:
                width = 17;
                break;
        }

        WritableImage newPlayerHealthImage = new WritableImage(readerPlayer, 1, 1, width, 15);
        ImageView playerHealthImage = new ImageView(newPlayerHealthImage);

//        Label label1 = new Label("Search");

//        ui.add(label1, 0, 0);
        labPlayer.setText(String.valueOf(health));
        labPlayer.setTextFill(Color.WHITESMOKE);
        labPlayer.setGraphic(playerHealthImage);

    }

    private void showPlayerName(String name){
        labPlayerName.setText("Player name: " + name + "  " );
        labPlayerName.setTextFill(Color.WHITESMOKE);
    }


    public int calculateMonsterHealthBar(int health, String name) {
        int width = 0;
        if (name.equals("Golem")) {
            switch (CustomUtils.RangeGolem.getFrom(health)) {

                case THIRTY_GOLEM:
                    width = 68;
                    break;

                case TWENTY_GOLEM:
                    width = 51;
                    break;

                case TEN_GOLEM:
                    width = 34;
                    break;

                case OTHER:
                default:
                    width = 68;
                    break;
            }
        }
        else if (name.equals("Skeleton")){
            switch (CustomUtils.RangeSkeleton.getFrom(health)) {

                case TEN_SKELETON:
                    width = 68;
                    break;

                case SIX_SKELETON:
                    width = 51;
                    break;

                case THREE_SKELETON:
                    width = 34;
                    break;

                case OTHER:
                default:
                    width = 17;
                    break;
            }
        }
        else if(name.equals("Ghost")){
            switch (CustomUtils.RangeGhost.getFrom(health)) {

                case EIGHT_GHOST:
                    width = 68;
                    break;

                case FIVE_GHOST:
                    width = 51;
                    break;

                case TWO_GHOST:
                    width = 34;
                    break;

                case OTHER:
                default:
                    width = 17;
                    break;
            }
        }

        return width;
    }

    public void showMonsterName(String name, int health){

        labMonsterName.setVisible(true);
        labMonster.setVisible(true);
        labMonsterName.setText("Monster name: " + name + "  ");
        labMonsterName.setTextFill(Color.WHITESMOKE);
        labMonster.setText(String.valueOf(health));
        labMonster.setTextFill(Color.WHITESMOKE);

        WritableImage newMonsterHealthImage = new WritableImage(readerMonster, 1, 1, calculateMonsterHealthBar(health, name), 15);
        ImageView monsterHealthImage = new ImageView(newMonsterHealthImage);
        labMonster.setTextFill(Color.WHITESMOKE);
        labMonster.setGraphic(monsterHealthImage);


    }
}
