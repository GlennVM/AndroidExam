package com.example.glenn.gameofthrones.character;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.glenn.gameofthrones.R;
import com.example.glenn.gameofthrones.models.GOTCharacter;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Glenn on 10/08/2016.
 */
public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder> {
    //Deze adapter extend de RecyclerViewAdapter, het is bij recyclerView verplicht om een adapter toe te voegen

    private List<GOTCharacter> characters;
    private Context context;
    private CardClickedListener listener;

    public CharacterAdapter(List<GOTCharacter> characters, Context context){
        this.characters = characters;
        this.context = context;
    }

    @Override
    public CharacterViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        //We maken een nieuwe viewHolder aan en steken daar de juiste layout in
        return new CharacterViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_character,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(CharacterViewHolder characterViewHolder, int i) {
        //Bij het binden stellen we het juiste personage in
        final GOTCharacter character = characters.get(i);
        //We zetten de naam van het personage op de juiste plaats in de viewHolder
        characterViewHolder.name.setText(character.getName());

        characterViewHolder.itemView.setOnClickListener(new View.OnClickListener(){
            //We voegen een listener toe die zal worden uitgevoerd wanneer er op een personage wordt geklicked
            @Override
            public void onClick(View v){
                //We roepen de clicked methode die gedefinieerd is in de meegegeven listener aan
                listener.cardClicked(character);
            }
        });
    }

    public void setCardClickedListener(CardClickedListener listener){
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return characters.size();
    }


    public static class CharacterViewHolder extends RecyclerView.ViewHolder{

        private final TextView name;
        // private final ImageView image;

        public CharacterViewHolder(View v) {
            super(v);
            name = ButterKnife.findById(v,R.id.card_name);
            //  image = ButterKnife.findById(v,R.id.card_image);
        }
    }

    public interface CardClickedListener{
        void cardClicked(GOTCharacter character);
    }
}
