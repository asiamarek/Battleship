package android.battleship;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Game currentGame;

    private TextView sunkShipsOpponentCounterView;
    private TextView sunkShipsMyCounterView;

    private BoardView myBoardView;
    private BoardView opponentsBoardView;
    private BoardView settingBoardView;

    private void setTextViews() {
        int opponentsCounter = currentGame.getOpponentsBoard().getNumberOfSankShips();
        int myCounter = currentGame.getMyBoard().getNumberOfSankShips();
        sunkShipsOpponentCounterView.setText(String.format("Opponent's ships sunk: %d", opponentsCounter));
        sunkShipsMyCounterView.setText(String.format("My ships sunk: %d", myCounter));
    }

    private void setBoards(){
        myBoardView = findViewById(R.id.myBoard);
        opponentsBoardView = findViewById(R.id.opponentBoard);

        myBoardView.setMine(true);
        opponentsBoardView.setMine(false);

        myBoardView.setBoard(currentGame.getMyBoard());
        opponentsBoardView.setBoard(currentGame.getOpponentsBoard());

        setBoardListiners();
    }

    private void setVariables() {
        sunkShipsOpponentCounterView = findViewById(R.id.sunkShipsOpponentCounter);
        sunkShipsMyCounterView = findViewById(R.id.sunkShipsMyCounter);
    }

    private void setBoardListiners(){
        opponentsBoardView.addListener(new BoardView.BoardListener() {
            @Override
            public void onTouch(Coordinate coordinate) {
                int shotEffect = currentGame.getOpponentsBoard().shoot(coordinate);
                if (shotEffect == -1){
                    return;
                }
                if(currentGame.theEnd()){
                    setWinnerLayout();
                }
                currentGame.getMyBoard().randomShoot();
                if(currentGame.theEnd()){
                    setLooserLayout();
                }
                opponentsBoardView.invalidate(); // ?????? nie dziala
            }
        });
    }

    public void playClickHandler(View view){
        setMainLayout();
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

    private void setSettingShipsLayout(){
        setContentView(R.layout.set_ships_layout);
        currentGame = new Game();
        setBoards();
    }

    private void setMainLayout(){
        setContentView(R.layout.activity_main);
        currentGame = new Game(); //tu juz nie jak skoncze
        setVariables();
        setBoards(); //tu juz nie jak skoncze
        setTextViews();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMenuLayout();
    }
}
