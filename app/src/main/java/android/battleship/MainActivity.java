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
        sunkShipsOpponentCounterView.setText(
                String.format("Opponent's ships left: %d", Board.numberOfShips() - opponentsCounter));
        sunkShipsMyCounterView.setText(
                String.format("Your ships left: %d", Board.numberOfShips() - myCounter));
    }

    private void setBoards(){
        myBoardView = findViewById(R.id.myBoard);
        opponentsBoardView = findViewById(R.id.opponentBoard);

        myBoardView.setMine(true);
        opponentsBoardView.setMine(false);

        myBoardView.setBoard(currentGame.getMyBoard());
        opponentsBoardView.setBoard(currentGame.getOpponentsBoard());

        setBoardListeners();
    }

    private void setSettingBoard(){
        settingBoardView = findViewById(R.id.settingBoardView);
        settingBoardView.setBoard(currentGame.getMyBoard());
        settingBoardView.setMine(true);
    }

    private void setVariables() {
        sunkShipsOpponentCounterView = findViewById(R.id.sunkShipsOpponentCounter);
        sunkShipsMyCounterView = findViewById(R.id.sunkShipsMyCounter);
    }

    private void showToast(CharSequence text){
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.setGravity(Gravity.TOP|Gravity.RIGHT, 0, 0);
        toast.show();
    }

    private void setBoardListeners(){
        opponentsBoardView.addListener(new BoardView.BoardListener() {
            @Override
            public void onTouch(Coordinate coordinate) {
                int shotEffect = currentGame.getOpponentsBoard().shoot(coordinate);
                if (shotEffect == -1){
                    return;
                }
                if(shotEffect == 1){
                    showToast("Hit !");
                }
                if(shotEffect == 2){
                    showToast("Sunk !");
                }
                if(currentGame.theEnd()){
                    setWinnerLayout();
                }
                setTextViews();
                shotEffect = currentGame.getMyBoard().randomShoot();
                if(shotEffect == 1){
                    showToast("You've been hit !");
                }
                if(shotEffect == 2){
                    showToast("Your ship sunk !");
                }
                if(currentGame.theEnd()){
                    setLooserLayout();
                }
                myBoardView.invalidate();
                setTextViews();
            }
        });
    }

    public void playClickHandler(View view){
        setSettingShipsLayout();
    }

    public void startBattleClickHandler(View view){
        setMainLayout();
    }

    public void instructionsClickHandler(View view){
        setInstructionsLayout();
    }

    public void menuClickHandler(View view){
        setMenuLayout();
    }

    public void setShipsRandomlyHandler(View view){
        currentGame.getMyBoard().setShipsRandomly();
        startBattleButton.setEnabled(true);
        settingBoardView.invalidate();
    }

    private void setMenuLayout(){
        setContentView(R.layout.menu_layout);
    }

    private void setWinnerLayout(){
        setContentView(R.layout.winner);
    }

    private void setLooserLayout(){
        setContentView(R.layout.looser);
    }

    private void setInstructionsLayout(){
        setContentView(R.layout.instructions);
    }

    private void setSettingShipsLayout(){
        setContentView(R.layout.set_ships_layout);
        currentGame = new Game();
        setSettingBoard();
        startBattleButton = findViewById(R.id.startBattleButton);
        startBattleButton.setEnabled(false);
    }

    private void setMainLayout(){
        setContentView(R.layout.activity_main);
        setBoards();
        setVariables();
        setTextViews();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMenuLayout();
    }
}
