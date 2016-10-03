package com.example.glenn.a2048;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.util.StringTokenizer;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Glenn on 7/08/2016.
 */
public class PlayActivity extends AppCompatActivity {
    private boolean continueGame;

    private int score;

    @Bind(R.id.txtScore)
    public TextView scoreView;

    @Bind(R.id.btnReset)
    public Button resetButton;

    private Board board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle("UI2048");
        //We stellen hier de score in op 0
        scoreView.setText(Integer.toString(0) + "");

        board = (Board) findViewById(R.id.board);
        //Voegt de een Listener toe aan het bord. Namelijk onze eigen gemaakte SwipeListener
        board.setOnTouchListener(new SwipeListener(board.getContext()) {

            @Override
            public void onSwipeDown() {
                Log.i("myTag", "swiped down!");
                //We laden het bord in in een tijdelijke array
                int[][] boardArray = board.toArray();
                //We maken een nieuw bord aan dat we zullen vullen met de aangepaste array
                int[][] turnedBoard = new int[4][4];
                //We overlopen het volledige oorspronkelijke bord
                for (int x = 0; x < 4; x++) {
                    for (int y = 0; y < 4; y++) {
                        //We steken de aangepaste waarden in het nieuwe bord
                        turnedBoard[x][y] = boardArray[y][3 - x];
                    }
                }
                for (int i = 0; i < 4; i++) {
                    turnedBoard[i] = losRijOp(turnedBoard[i]);
                }
                boardArray = new int[4][4];
                for (int x = 0; x < 4; x++) {
                    for (int y = 0; y < 4; y++) {
                        boardArray[y][3 - x] = turnedBoard[x][y];

                    }
                }
                board.arrayToBoard(boardArray);
                board.randomValue();
                saveBoard();

            }

            @Override
            public void onSwipeLeft() {
                Log.i("myTag", "swiped left!");
                int[][] boardArray = board.toArray();
                int[][] spiegelBord = new int[4][4];
                for (int x = 0; x < 4; x++) {
                    for (int y = 0; y < 4; y++) {
                        spiegelBord[x][y] = boardArray[x][3 - y];
                    }
                }
                for (int i = 0; i < 4; i++) {
                    spiegelBord[i] = losRijOp(spiegelBord[i]);
                }
                for (int x = 0; x < 4; x++) {
                    for (int y = 0; y < 4; y++) {
                        boardArray[x][3 - y] = spiegelBord[x][y];
                    }
                }
                board.arrayToBoard(boardArray);
                board.randomValue();
                saveBoard();
            }

            @Override
            public void onSwipeRight() {
                Log.i("myTag", "swiped right!");
                int[][] boardArray = board.toArray();
                for (int i = 0; i < 4; i++) {
                    boardArray[i] = losRijOp(boardArray[i]);
                }
                board.arrayToBoard(boardArray);
                board.randomValue();
                saveBoard();
            }

            @Override
            public void onSwipeUp() {
                Log.i("myTag", "swiped up!");
                int[][] boardArray = board.toArray();
                int[][] turnedBoard = new int[4][4];
                for (int x = 0; x < 4; x++) {
                    for (int y = 0; y < 4; y++) {
                        turnedBoard[y][3 - x] = boardArray[x][y];
                    }
                }


                for (int i = 0; i < 4; i++) {
                    turnedBoard[i] = losRijOp(turnedBoard[i]);
                }
                boardArray = new int[4][4];
                for (int x = 0; x < 4; x++) {
                    for (int y = 0; y < 4; y++) {

                        boardArray[x][y] = turnedBoard[y][3 - x];

                    }
                }
                board.arrayToBoard(boardArray);
                board.randomValue();
                saveBoard();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        //We kijken of we op continue geklikt hadden
        continueGame = (boolean) getIntent().getExtras().get("continue");
        //Alst dit het geval is
        if (continueGame) {
            //We halen de shared prefs op
            SharedPreferences prefs = this.getSharedPreferences("NAME", Context.MODE_PRIVATE);
            Log.i("create", "prefs :" + prefs.getString("string0", ""));

            if (!prefs.getString("string0", "").isEmpty()) {
                Log.i("create", "!isempty:" + !prefs.getString("string0", "").isEmpty());
                Log.i("create", "null:" + (prefs.getString("string0", "") != null));
                Log.i("create", "prefs :" + prefs.getString("string0", ""));
                //We laden het bord
                loadBoard();
            }
        } else {
            resetBoard();

        }
    }

    @OnClick(R.id.btnReset)
    public void resetBoard() {
        board.setLoad(false);
        //We starten een nieuw spel op
        board.startGame();
        //We zetten de score terug op 0
        score = 0;
        updateScore(0);
        //We slaan dit bord op
        saveBoard();
    }

    public int[] losRijOp(int[] rij) {
        int huidig = 0;
        int compare = 0;
        for (int i = 3; i >= 1; i--) {
            huidig = rij[i];
            if (huidig == 0) {
                boolean allzero = true;
                for (int j = i - 1; j >= 0; j--) {
                    if (rij[j] != 0) {
                        allzero = false;
                    }
                }
                if (!allzero) {
                    for (int j = i; j > 0; j--) {
                        rij[j] = rij[j - 1];
                    }
                    rij[0] = 0;
                    i++;

                }
            } else {
                for (int j = i - 1; j >= 0; j--) {
                    compare = rij[j];
                    if (compare == 0) {
                        continue;
                    } else if (huidig == compare) {
                        updateScore(rij[i]);
                        rij[i] = rij[i] * 2;
                        rij[j] = 0;
                        break;
                    } else {
                        break;
                    }
                }
            }
        }


        return rij;
    }


    public void updateScore(int i) {
        score += i;
        scoreView.setText(Integer.toString(score) + "");
    }


    private void saveBoard() {
        //We zorgen dat we de sharedprefs kunnen aanpassen
        SharedPreferences.Editor prefs = this.getSharedPreferences("NAME", Context.MODE_PRIVATE).edit();

        //We overlopen 4 kolommen
        for (int i = 0; i < 4; i++) {
            //We maken een nieuwe stringbuilder aan
            StringBuilder str = new StringBuilder();
            //We de rij
            for (int j = 0; j < 4; j++) {
                //We hangen de waarde van elke kaart achteraan onze string, gescheiden door een komma
                str.append(board.toArray()[i][j]).append(",");
            }
            //We voegen onze string toe aan de Prefs
            prefs.putString("string" + i, str.toString());

        }
        //We voegen de score toe aan de sharedPrefs
        prefs.putInt("score", score);
        prefs.commit();
    }

    private void loadBoard() {
        //We halen de shared prefs op
        SharedPreferences prefs = this.getSharedPreferences("NAME", Context.MODE_PRIVATE);
        String savedString;
        //We maken een nieuw bord aan
        int[][] loadBoard = new int[4][4];
        for (int i = 0; i < 4; i++) {
            //We halen het bord per rij op
            savedString = prefs.getString("string" + i, "");
            //We splitsen de zin met waarden op het teken komma
            StringTokenizer st = new StringTokenizer(savedString, ",");

            //Dan overlopen we de rij en vullen we dit in met de juiste waarden
            for (int j = 0; j < 4; j++) {
                loadBoard[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        board.setLoad(true);
        //We geven het net geladen bord door zodat het word geladen
        board.arrayToBoard(loadBoard);
        //We halen de score op en zetten dit op het bord
        score = prefs.getInt("score", 0);
        updateScore(0);
    }

}
