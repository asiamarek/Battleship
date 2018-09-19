package android.battleship;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import static android.battleship.Board.BOARD_SIZE;

public class BoardView extends View {
    private Board board;

    private Paint whitePaint;
    private Paint blackPaint;
    private Paint redPaint;
    private Paint greyPaint;
    private Paint transparentPaint;

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

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawGrid(canvas);
        drawBoard(canvas);
    }

    private void drawField(Canvas canvas, Coordinate coordinate, Paint paint) {
        float measuredFieldSize = getMeasuredHeight() / BOARD_SIZE;
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
                    if(fieldStatus == Field.FieldStatus.WATER)
                        continue;
                    switch (fieldStatus){
                        case SHIP:  drawField(canvas, new Coordinate(i,j), blackPaint);
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
