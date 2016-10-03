package com.example.glenn.fragments_paradoxes;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Glenn on 9/08/2016.
 */
public class ParadoxListFragment extends Fragment {
    //Lokale variabele binden met ListView in de xml via Butterknife
    @Bind(R.id.paradoxes)
    ListView paradoxes;

    //aanmaken listener om te weten welke paradox geselecteerd werd
    private OnParadoxSelectedListener paradoxSelectedListener;

    public ParadoxListFragment() {
        //noodzakelijke lege constructor om fragment te kunnen aanmaken in activity
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //Voegt de saved instance toe aan het fragment
        //Op die manier worden gegevens opnieuw ingeladen
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // View maken met daarin de layout geïnflate
                                        //Resource                     Viewgroup  attachToRoot
        View layout = inflater.inflate(R.layout.fragment_paradox_list, container, false);
        //Butterknife zal deze klasse binden aan de net aangemaakte layout
        ButterKnife.bind(this,layout);
        //We halen de activity op en zullen deze selecteren als Listener, we zullen hierin de Listener methode overschrijven
        paradoxSelectedListener = (OnParadoxSelectedListener) getActivity();
        //We maken een nieuwe ArrayAdapter aan
        //Deze zullen we toevoegen aan de ListView
        //We geven de activity mee, we selecteren een layout en geven de lijst met paradoxNamen mee, hiermee zal de lijst worden gevult
        paradoxes.setAdapter(new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,Paradoxes.ParadoxNames));
        //We voegen een listener toe die de click op een item zal opvangen
        paradoxes.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            //De methode onItemClick krijgt enkele zaken mee, maar wij zijn enkel geinteresserd in de positie die werd aangeklikt
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //We geven de aangeklikte positie mee aan de selectedListener die zal de rest afhandelen
                paradoxSelectedListener.onParadoxSelected(position);
            }
        });
        return layout;
    }

    public interface OnParadoxSelectedListener {
        void onParadoxSelected(int index);
    }
}