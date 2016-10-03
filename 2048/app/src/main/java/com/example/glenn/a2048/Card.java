package com.example.glenn.a2048;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by Glenn on 7/08/2016.
 */
public class Card extends FrameLayout implements Comparable{

    private TextView cardLabel;

    public Card(Context context) {
        super(context);
        //Instantieerd nieuwe textview
        cardLabel = new TextView(getContext());
        cardLabel.setGravity(Gravity.CENTER);
        LayoutParams lp = new LayoutParams(-1, -1);
        lp.setMargins(10,10,0,0);
        cardLabel.setBackgroundColor(Color.parseColor("#eee4da"));
        //Voegt textView toe aan View
        addView(cardLabel, lp);
    }

    @Override
    public int compareTo(Object another) {
        return 0;
    }

    //De waarde wordt ingesteld in de kaart. dit wordt op het label geplaatst
    public void setCardValue(int cardValue) {
        cardLabel.setText(String.valueOf(cardValue));
    }

    //Dit geeft de waarde van de kaart terug
    public int getCardValue() {
        return Integer.parseInt(cardLabel.getText().toString());
    }
}

