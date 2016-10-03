package com.example.glenn.fragments_paradoxes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Glenn on 9/08/2016.
 */
public class ParadoxDetailFragment extends Fragment {

    //Butterknife bind txtViews uit de layouts aan lokale variabelen
    @Bind(R.id.txtTitle)
    TextView title;

    @Bind(R.id.txtContent)
    TextView detail;

    public static final String PARADOXINDEX = "PARADOXINDEX";

    public ParadoxDetailFragment() {
        //Noodzakelijke lege constructor om Fragment aan te kunnen maken
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflates de layout die bij deze fragment hoort
        //Zal de juiste layout op de juiste plaats zetten
        View layout = inflater.inflate(R.layout.fragment_paradox_detail, container, false);
        //Butterknife zal deze klasse aan de layout binden
        ButterKnife.bind(this, layout);
        return layout;
    }

    @Override
    public void onStart() {
        super.onStart();
        //Vraagt de argumenten op die door de activity zijn meegegeven
        Bundle args = getArguments();
        if (args != null) {
            //Dit zal de juiste index van de aangeklikte paradox teruggeven
            int index = args.getInt(PARADOXINDEX);
            updateParadoxDescription(index);
        }
    }

    public void updateParadoxDescription(int position){
        //Zal de juiste titel en tekst van de aangeklikte paradox tonen op het scherm
        title.setText(Paradoxes.ParadoxNames[position]);
        detail.setText(Paradoxes.ParadoxDescription[position]);
    }
}