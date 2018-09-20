package android.battleship;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Game currentGame;

    private TextView sunkShipsOpponentCounterView;
    private TextView sunkShipsMyCounterView;

    private BoardView myBoardView;
    private BoardView opponentsBoardView;

    private void setTextViews() {
        int opponentsCounter = currentGame.getOpponentsBoard().getNumberOfSankShips();
        int myCounter = currentGame.getMyBoard().getNumberOfSankShips();
        sunkShipsOpponentCounterView.setText(String.format("Opponent's ships sunk: %d", opponentsCounter));
        sunkShipsMyCounterView.setText(String.format("My ships sunk: %d", myCounter));
    }

    private void setVariables() {
        sunkShipsOpponentCounterView = findViewById(R.id.sunkShipsOpponentCounter);
        sunkShipsMyCounterView = findViewById(R.id.sunkShipsMyCounter);
        myBoardView = findViewById(R.id.myBoard);
        opponentsBoardView = findViewById(R.id.opponentBoard);

        myBoardView.setMine(true);
        opponentsBoardView.setMine(false);

        myBoardView.setBoard(currentGame.getMyBoard());
        opponentsBoardView.setBoard(currentGame.getOpponentsBoard());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentGame = new Game();
        setVariables();
        setTextViews();
    }
}
