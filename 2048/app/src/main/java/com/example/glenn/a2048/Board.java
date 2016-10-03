package com.example.glenn.a2048;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.GridLayout;

import java.util.Random;

/**
 * Created by Glenn on 7/08/2016.
 */
public class Board extends GridLayout {

    //Array die het spelboard voorstelt, opgebouwd uit verschillende kaarten
    private Card[][] cardBoard;

    private boolean load;

    public Board(Context context) {
        super(context);
        initBoard();
    }

    public Board(Context context, AttributeSet attrs) {
        super(context, attrs);
        initBoard();
    }

    public Board(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initBoard();
    }

    public Board(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initBoard();
    }

    //Methode die het bord opsteld
    public void initBoard() {
        //Bord wordt geïnstantieerd voor 4x4
        cardBoard = new Card[4][4];
        //Overlopen van het bord
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                //Per vak wordt een nieuw bord aangemaakt
                cardBoard[x][y] = new Card(getContext());
            }
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.i("myTag", "onSizeChanged");
        super.onSizeChanged(w, h, oldw, oldh);
        int cardWith = (Math.min(w, h) - 10) / 4;
        addCards(cardWith, cardWith);
        startGame();
    }

    private void addCards(int cardWith, int cardHeight) {
        //Bord wordt overlopen
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                cardBoard[x][y].setLayoutParams(new ViewGroup.LayoutParams(cardWith, cardHeight));
                //voegt kaart toe aan view
                this.addView(cardBoard[x][y]);
            }
        }
    }

    public void startGame() {
        if (!load) {
            setBoard();
        }
        Log.i("myTag", "startGame");
    }

    private void setBoard() {
        Random r = new Random();
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                cardBoard[x][y].setCardValue(r.nextInt(3));
            }
        }
    }

    public int[][] toArray() {
        int[][] output = new int[4][4];
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                output[x][y] = cardBoard[x][y].getCardValue();
            }
        }
        return output;
    }

    public void arrayToBoard(int[][] boardArray) {
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                cardBoard[x][y].setCardValue(boardArray[x][y]);
            }
        }

    }

    public void randomValue() {
        Random ran = new Random();
        boolean zeroGevonden = false;
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                if (cardBoard[x][y].getCardValue() == 0) {
                    cardBoard[x][y].setCardValue(ran.nextInt(2) + 1);
                    zeroGevonden = true;
                }
                if (zeroGevonden) {
                    break;
                }
            }
            //als het hier komt heb je verloren
        }
    }


    public void setLoad(boolean load) {
        this.load = load;
    }
}
