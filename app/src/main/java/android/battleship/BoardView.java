package android.battleship;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import static android.battleship.Board.BOARD_SIZE;

public class BoardView extends View {
    private Board board;
    private boolean isMine;

    private Paint whitePaint;
    private Paint blackPaint;
    private Paint redPaint;
    private Paint greyPaint;
    private Paint transparentPaint;

    private List<BoardListener> listeners = new ArrayList<>();

    public BoardView(Context context) {
        super(context);
        setPaints();
    }

    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setPaints();
    }

    public BoardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setPaints();
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setMine(boolean isMine){
        this.isMine = isMine;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawGrid(canvas);
        drawBoard(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_UP:
                if(isMine)
                    break;
                Coordinate coordinate = findPlaceOnBoard(e.getX(), e.getY());
                invalidate();
                notifyBoardListeners(coordinate);
        }
        return true;
    }

    void addListener(BoardListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    private void notifyBoardListeners(Coordinate coordinate) {
        for (BoardListener listener : listeners) {
            listener.onTouch(coordinate);
        }
    }

    public interface BoardListener {
        void onTouch(Coordinate coordinate);
    }

    private Coordinate findPlaceOnBoard(float x, float y) {
        float measuredFieldSize = getMeasuredFieldSize();
        int fieldX = (int) (x / measuredFieldSize);
        int fieldY = (int) (y / measuredFieldSize);
        return new Coordinate(fieldX, fieldY);
    }

    private float getMeasuredFieldSize(){
        return getMeasuredHeight() / BOARD_SIZE;
    }

    private void drawField(Canvas canvas, Coordinate coordinate, Paint paint) {
        float measuredFieldSize = getMeasuredFieldSize();
        int x = coordinate.getX();
        int y = coordinate.getY();
        canvas.drawRect(measuredFieldSize * x, measuredFieldSize * y,
                measuredFieldSize * (x + 1), measuredFieldSize * (y + 1), paint);
    }

    private void drawBoard(Canvas canvas) {
        if(board != null){
            Field[][] boardFields = board.getFields();
            for(int i = 0; i < BOARD_SIZE; i++){
                for(int j = 0; j < BOARD_SIZE; j++){
                    Field.FieldStatus fieldStatus = boardFields[i][j].getStatus();
                    switch (fieldStatus){
                        case SHIP:  if(isMine){
                                        drawField(canvas, new Coordinate(i,j), blackPaint);
                                    }
                                    break;
                        case HIT_SHIP:  drawField(canvas, new Coordinate(i,j), redPaint);
                                        break;
                        case HIT_WATER: drawField(canvas, new Coordinate(i,j), greyPaint);
                                        break;
                    }
                }
            }
        }
    }

    private void setPaints() {
        whitePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        whitePaint.setColor(Color.rgb(255, 255, 255));

        blackPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        blackPaint.setColor(Color.rgb(0, 0, 0));

        redPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        redPaint.setColor(Color.rgb(201, 6, 45));

        greyPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        greyPaint.setColor(Color.rgb(152, 152, 152));

        transparentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        transparentPaint.setColor(Color.argb(0, 255, 255, 255));
    }

    private void drawGrid(Canvas canvas) {
        float measuredSize = getMeasuredHeight();
        canvas.drawRect(0, 0, measuredSize, measuredSize, transparentPaint);

        for (int i = 0; i < (BOARD_SIZE + 1); i++) {
            float coordinate = (i * measuredSize) / BOARD_SIZE;
            canvas.drawLine(0, coordinate, measuredSize, coordinate, whitePaint); // horizontal
            canvas.drawLine(coordinate, 0, coordinate, measuredSize, whitePaint); // vertical
        }
    }
}
