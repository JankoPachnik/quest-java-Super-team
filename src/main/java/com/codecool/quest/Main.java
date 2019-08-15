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

    private void monstersAction() {
        List<Monster> monsters = map.getMonsters();

        for (Monster monster : monsters) {
            if(!monster.isDead()){
                if(monster.isHostile() && monster.isInRange(map.getPlayer())){
                    monster.attackPlayer(map.getPlayer());
                }else{
                    monster.move();
                }
            }
        }
    }

    private void movePlayer(String direction, int xDir, int yDir) {
        Player player = map.getPlayer();
        player.changeDirection(direction);
        if(changingDirection){
            UserInterface.getMessageLabel().setText("Attacking direction: " + direction);
        }else{
            player.move(xDir, yDir);
            monstersAction();
            UserInterface.getMessageLabel().setText("");
            if(!player.isDead())
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
                case ENTER:
                    break;
                case BACK_SPACE:
                    break;
                case TAB:
                    break;
                case CANCEL:
                    break;
                case CLEAR:
                    break;
                case SHIFT:
                    break;
                case CONTROL:
                    break;
                case ALT:
                    break;
                case PAUSE:
                    break;
                case CAPS:
                    break;
                case ESCAPE:
                    break;
                case SPACE:
                    break;
                case PAGE_UP:
                    break;
                case PAGE_DOWN:
                    break;
                case END:
                    break;
                case HOME:
                    break;
                case LEFT:
                    movePlayer("left", -1, 0);
                    break;
                case RIGHT:
                    movePlayer("right", 1, 0);
                    break;
                case COMMA:
                    break;
                case MINUS:
                    break;
                case PERIOD:
                    break;
                case SLASH:
                    break;
                case DIGIT0:
                    break;
                case DIGIT1:
                    break;
                case DIGIT2:
                    break;
                case DIGIT3:
                    break;
                case DIGIT4:
                    break;
                case DIGIT5:
                    break;
                case DIGIT6:
                    break;
                case DIGIT7:
                    break;
                case DIGIT8:
                    break;
                case DIGIT9:
                    break;
                case SEMICOLON:
                    break;
                case EQUALS:
                    break;
                case A:
                    break;
                case B:
                    break;
                case C:
                    break;
                case D:
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
                        if(player.canAttack()) {
                            String monsterName = player.getNextCell().getActor().getName();
                            int monsterHealth = player.getNextCell().getActor().getHealth();
                            UserInterface.getMessageLabel().setText(message);
                            UserInterface.showMonsterName(monsterName, monsterHealth);
                        } else {
                            UserInterface.labMonsterName.setVisible(false);
                            UserInterface.labMonster.setVisible(false);
                        }
                        changingDirection = false;
                        monstersAction();
                    }
                    break;
                case F:
                    break;
                case G:
                    break;
                case H:
                    String message = player.useHealthPotion();
                    UserInterface.getMessageLabel().setText(message);
                    break;
                case I:
                    break;
                case J:
                    String msg = player.usePowerPotion();
                    UserInterface.getMessageLabel().setText(msg);
                    break;
                case K:
                    break;
                case L:
                    break;
                case M:
                    break;
                case N:
                    break;
                case O:
                    break;
                case P:
                    break;
                case Q:
                    break;
                case S:
                    break;
                case T:
                    break;
                case U:
                    break;
                case V:
                    break;
                case W:
                    break;
                case X:
                    break;
                case Y:
                    break;
                case Z:
                    break;
                case OPEN_BRACKET:
                    break;
                case BACK_SLASH:
                    break;
                case CLOSE_BRACKET:
                    break;
                case NUMPAD0:
                    break;
                case NUMPAD1:
                    break;
                case NUMPAD2:
                    break;
                case NUMPAD3:
                    break;
                case NUMPAD4:
                    break;
                case NUMPAD5:
                    break;
                case NUMPAD6:
                    break;
                case NUMPAD7:
                    break;
                case NUMPAD8:
                    break;
                case NUMPAD9:
                    break;
                case MULTIPLY:
                    break;
                case ADD:
                    break;
                case SEPARATOR:
                    break;
                case SUBTRACT:
                    break;
                case DECIMAL:
                    break;
                case DIVIDE:
                    break;
                case DELETE:
                    break;
                case NUM_LOCK:
                    break;
                case SCROLL_LOCK:
                    break;
                case F1:
                    break;
                case F2:
                    break;
                case F3:
                    break;
                case F4:
                    break;
                case F5:
                    break;
                case F6:
                    break;
                case F7:
                    break;
                case F8:
                    break;
                case F9:
                    break;
                case F10:
                    break;
                case F11:
                    break;
                case F12:
                    break;
                case F13:
                    break;
                case F14:
                    break;
                case F15:
                    break;
                case F16:
                    break;
                case F17:
                    break;
                case F18:
                    break;
                case F19:
                    break;
                case F20:
                    break;
                case F21:
                    break;
                case F22:
                    break;
                case F23:
                    break;
                case F24:
                    break;
                case PRINTSCREEN:
                    break;
                case INSERT:
                    break;
                case HELP:
                    break;
                case META:
                    break;
                case BACK_QUOTE:
                    break;
                case QUOTE:
                    break;
                case KP_UP:
                    break;
                case KP_DOWN:
                    break;
                case KP_LEFT:
                    break;
                case KP_RIGHT:
                    break;
                case DEAD_GRAVE:
                    break;
                case DEAD_ACUTE:
                    break;
                case DEAD_CIRCUMFLEX:
                    break;
                case DEAD_TILDE:
                    break;
                case DEAD_MACRON:
                    break;
                case DEAD_BREVE:
                    break;
                case DEAD_ABOVEDOT:
                    break;
                case DEAD_DIAERESIS:
                    break;
                case DEAD_ABOVERING:
                    break;
                case DEAD_DOUBLEACUTE:
                    break;
                case DEAD_CARON:
                    break;
                case DEAD_CEDILLA:
                    break;
                case DEAD_OGONEK:
                    break;
                case DEAD_IOTA:
                    break;
                case DEAD_VOICED_SOUND:
                    break;
                case DEAD_SEMIVOICED_SOUND:
                    break;
                case AMPERSAND:
                    break;
                case ASTERISK:
                    break;
                case QUOTEDBL:
                    break;
                case LESS:
                    break;
                case GREATER:
                    break;
                case BRACELEFT:
                    break;
                case BRACERIGHT:
                    break;
                case AT:
                    break;
                case COLON:
                    break;
                case CIRCUMFLEX:
                    break;
                case DOLLAR:
                    break;
                case EURO_SIGN:
                    break;
                case EXCLAMATION_MARK:
                    break;
                case INVERTED_EXCLAMATION_MARK:
                    break;
                case LEFT_PARENTHESIS:
                    break;
                case NUMBER_SIGN:
                    break;
                case PLUS:
                    break;
                case RIGHT_PARENTHESIS:
                    break;
                case UNDERSCORE:
                    break;
                case WINDOWS:
                    break;
                case CONTEXT_MENU:
                    break;
                case FINAL:
                    break;
                case CONVERT:
                    break;
                case NONCONVERT:
                    break;
                case ACCEPT:
                    break;
                case MODECHANGE:
                    break;
                case KANA:
                    break;
                case KANJI:
                    break;
                case ALPHANUMERIC:
                    break;
                case KATAKANA:
                    break;
                case HIRAGANA:
                    break;
                case FULL_WIDTH:
                    break;
                case HALF_WIDTH:
                    break;
                case ROMAN_CHARACTERS:
                    break;
                case ALL_CANDIDATES:
                    break;
                case PREVIOUS_CANDIDATE:
                    break;
                case CODE_INPUT:
                    break;
                case JAPANESE_KATAKANA:
                    break;
                case JAPANESE_HIRAGANA:
                    break;
                case JAPANESE_ROMAN:
                    break;
                case KANA_LOCK:
                    break;
                case INPUT_METHOD_ON_OFF:
                    break;
                case CUT:
                    break;
                case COPY:
                    break;
                case PASTE:
                    break;
                case UNDO:
                    break;
                case AGAIN:
                    break;
                case FIND:
                    break;
                case PROPS:
                    break;
                case STOP:
                    break;
                case COMPOSE:
                    break;
                case ALT_GRAPH:
                    break;
                case BEGIN:
                    break;
                case UNDEFINED:
                    break;
                case SOFTKEY_0:
                    break;
                case SOFTKEY_1:
                    break;
                case SOFTKEY_2:
                    break;
                case SOFTKEY_3:
                    break;
                case SOFTKEY_4:
                    break;
                case SOFTKEY_5:
                    break;
                case SOFTKEY_6:
                    break;
                case SOFTKEY_7:
                    break;
                case SOFTKEY_8:
                    break;
                case SOFTKEY_9:
                    break;
                case GAME_A:
                    break;
                case GAME_B:
                    break;
                case GAME_C:
                    break;
                case GAME_D:
                    break;
                case STAR:
                    break;
                case POUND:
                    break;
                case POWER:
                    break;
                case INFO:
                    break;
                case COLORED_KEY_0:
                    break;
                case COLORED_KEY_1:
                    break;
                case COLORED_KEY_2:
                    break;
                case COLORED_KEY_3:
                    break;
                case EJECT_TOGGLE:
                    break;
                case PLAY:
                    break;
                case RECORD:
                    break;
                case FAST_FWD:
                    break;
                case REWIND:
                    break;
                case TRACK_PREV:
                    break;
                case TRACK_NEXT:
                    break;
                case CHANNEL_UP:
                    break;
                case CHANNEL_DOWN:
                    break;
                case VOLUME_UP:
                    break;
                case VOLUME_DOWN:
                    break;
                case MUTE:
                    break;
                case COMMAND:
                    break;
                case SHORTCUT:
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
//        UserInterface.getHealthLabel().setText("" + map.getPlayer().getHealth());
//        showHealthBar(map.getCell(x,y))
//        showMonsterName(Player.getEnemyActorName());
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
