package android.battleship;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Game currentGame;

    private TextView sunkShipsOpponentCounterView;
    private TextView sunkShipsMyCounterView;

    private BoardView myBoardView;
    private BoardView opponentsBoardView;
    private BoardView settingBoardView;

    private Button startBattleButton;

    public void setTextViews() {
        int opponentsCounter = currentGame.getOpponentsBoard().getnumberOfSunkenShips();
        int myCounter = currentGame.getMyBoard().getnumberOfSunkenShips();
        if (currentGame.getMode() == Game.GameMode.ONE_PLAYER) {
            sunkShipsOpponentCounterView.setText(
                    String.format("Opponent's ships left: %d", Board.numberOfShips() - opponentsCounter));
            sunkShipsMyCounterView.setText(
                    String.format("Your ships left: %d", Board.numberOfShips() - myCounter));
        } else {
            sunkShipsOpponentCounterView.setText(
                    String.format("Player's 1 ships left: %d", Board.numberOfShips() - myCounter));
            sunkShipsMyCounterView.setText(
                    String.format("Player's 2 ships left: %d", Board.numberOfShips() - opponentsCounter));
        }
    }

    private void setBoards(boolean myTurn) {
        myBoardView = findViewById(R.id.myBoard);
        opponentsBoardView = findViewById(R.id.opponentBoard);

        myBoardView.setMine(true);
        opponentsBoardView.setMine(false);

        if (myTurn) {
            myBoardView.setBoard(currentGame.getMyBoard());
            opponentsBoardView.setBoard(currentGame.getOpponentsBoard());
        } else {
            myBoardView.setBoard(currentGame.getOpponentsBoard());
            opponentsBoardView.setBoard(currentGame.getMyBoard());
        }
    }

    private void setSettingBoard() {
        settingBoardView = findViewById(R.id.settingBoardView);
        TextView placeShipsTextView = findViewById(R.id.placeShipsTextView);
        settingBoardView.setMine(true);
        if (currentGame.isMyTurn()) {
            settingBoardView.setBoard(currentGame.getMyBoard());
            if (currentGame.getMode() == Game.GameMode.ONE_PLAYER) {
                placeShipsTextView.setText("Place your ships");
            } else {
                placeShipsTextView.setText("Player 1: place your ships");
            }
        } else {
            settingBoardView.setBoard(currentGame.getOpponentsBoard());
            placeShipsTextView.setText("Player 2: place your ships");
        }
    }

    private void setVariables() {
        sunkShipsOpponentCounterView = findViewById(R.id.sunkShipsOpponentCounter);
        sunkShipsMyCounterView = findViewById(R.id.sunkShipsMyCounter);
    }

    private void showToast(CharSequence text) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.setGravity(Gravity.TOP | Gravity.RIGHT, 0, 0);
        toast.show();
    }

    private void boardListener(Coordinate coordinate) {
        int shotEffect;
        if (currentGame.isMyTurn()) {
            shotEffect = currentGame.getOpponentsBoard().shoot(coordinate);
        } else {
            shotEffect = currentGame.getMyBoard().shoot(coordinate);
        }
        if (shotEffect == -1) {
            return;
        }
        if (currentGame.getMode() == Game.GameMode.ONE_PLAYER) {
            if (shotEffect == 1) {
                showToast("Hit !");
            }
            if (shotEffect == 2) {
                showToast("Sunk !");
            }
        }
        if (currentGame.theEnd()) {
            setWinnerLayout();
        }
        setTextViews();
        opponentsBoardView.invalidate();

        if (currentGame.getMode() == Game.GameMode.ONE_PLAYER) {
            shotEffect = currentGame.getMyBoard().randomShoot();
            if (shotEffect == 1) {
                showToast("You've been hit !");
            }
            if (shotEffect == 2) {
                showToast("Your ship sunk !");
            }
            if (currentGame.theEnd()) {
                setLooserLayout();
            }
            myBoardView.invalidate();
            setTextViews();
        } else {
            boolean isMyTurn = currentGame.isMyTurn();
            currentGame.setMyTurn(!isMyTurn);
            setSwitchPlayersLayout(shotEffect);
        }
    }

    private void setBoardListeners() {
        opponentsBoardView.addListener(new BoardView.BoardListener() {
            @Override
            public void onTouch(Coordinate coordinate) {
                boardListener(coordinate);
            }
        });
    }

    public void playClickHandler(View view) {
        setChooseModeLayout();
    }

    public void startBattleClickHandler(View view) {
        if (currentGame.getMode() == Game.GameMode.ONE_PLAYER) {
            setMainLayout();
        } else {
            boolean isMyTurn = currentGame.isMyTurn();
            currentGame.setMyTurn(!isMyTurn);
            if (isMyTurn) {
                setSettingShipsLayout();
            } else {
                setSwitchPlayersLayout(-1);
            }
        }
    }

    public void instructionsClickHandler(View view) {
        setInstructionsLayout();
    }

    public void menuClickHandler(View view) {
        setMenuLayout();
    }

    public void onePlayerModeClickHandler(View view) {
        currentGame = new Game(Game.GameMode.ONE_PLAYER);
        setSettingShipsLayout();
    }

    public void switchPlayerClickHandler(View view) {
        setMainLayout();
    }

    public void twoPlayersModeClickHandler(View view) {
        currentGame = new Game(Game.GameMode.TWO_PLAYERS);
        setSettingShipsLayout();
    }

    public void setShipsRandomlyHandler(View view) {
        if (currentGame.isMyTurn()) {
            currentGame.getMyBoard().setShipsRandomly();
        } else {
            currentGame.getOpponentsBoard().setShipsRandomly();
        }
        startBattleButton.setEnabled(true);
        settingBoardView.invalidate();
    }

    private void setMenuLayout() {
        setContentView(R.layout.menu_layout);
    }

    private void setWinnerLayout() {
        setContentView(R.layout.winner);
    }

    private void setLooserLayout() {
        setContentView(R.layout.looser);
    }

    private void setInstructionsLayout() {
        setContentView(R.layout.instructions);
    }

    private void setChooseModeLayout() {
        setContentView(R.layout.choose_mode);
    }

    private void setSwitchPlayersLayout(int shotEffect) {
        setContentView(R.layout.switch_players);
        TextView playerTurnTextView = findViewById(R.id.playerTurnTextView);
        TextView shotResultTextView = findViewById(R.id.shotResultTextView);

        switch (shotEffect) {
            case 0:
                shotResultTextView.setText("Miss...");
                break;
            case 1:
                shotResultTextView.setText("Hit!");
                break;
            case 2:
                shotResultTextView.setText("Sunk!");
                break;
            default:
                shotResultTextView.setText("");
        }

        if (currentGame.isMyTurn()) {
            playerTurnTextView.setText("Player's 1 turn");
        } else {
            playerTurnTextView.setText("Player's 2 turn");
        }
    }

    private void setSettingShipsLayout() {
        setContentView(R.layout.set_ships_layout);
        setSettingBoard();
        startBattleButton = findViewById(R.id.startBattleButton);
        startBattleButton.setEnabled(false);
    }

    private void setMainLayout() {
        setContentView(R.layout.activity_main);
        setBoards(currentGame.isMyTurn());
        setBoardListeners();
        setVariables();
        setTextViews();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMenuLayout();
    }
}
