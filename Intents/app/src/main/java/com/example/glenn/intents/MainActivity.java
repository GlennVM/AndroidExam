package com.example.glenn.intents;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    //Butterknife bind lokale variabele aan txtView uit layout
    @Bind(R.id.txtSpeechInput)
    TextView txtSpeechInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Plaatst de juiste layout in de view
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //Stelt de titel in in de actionBar bovenaan het scherm
        getSupportActionBar().setTitle("Speech Intent");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Voegt het menu uit de res toe aan de actionbar bovenaan. Op de plaats waar het menu hoort te staan.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Deze functie zal clicks in het menu opvangen
        //Haalt het id op van het item dat is aangeklikt
        int id = item.getItemId();

        //Vergelijkt de id die aangeklikt is met die van de verschillende acties uit het menu
        switch(id){
            //Als de huidige actie wordt geselecteerd moet er niets gebeuren
            case R.id.action_speechintent:break;
            //Als de andere actie wordt geselecteerd moet er een andere activity worden gestart
            case R.id.action_multipleintents:
                //We maken hiervoor een nieuwe intent aan
                //We geven de huidige activity mee en de nieuwe activity die we willen starten
                Intent intent = new Intent(MainActivity.this, VariousIntents.class);
                //We starten de activity
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }



    @OnClick(R.id.btnSpeech)
    public void startSpeechIntent(){
        //Maakt nieuw intent aan voor spraakherkenning ==> implicit Intent
        Intent speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        //Voegt extra functionaliteit toe aan intent
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        try{
            //start de nieuwe intent
            //geeft de te starten intent mee, en geeft de request code mee
            //Door de forResult zullen we wachten op een resultaat van de activity
            startActivityForResult(speechIntent,100);
        }catch(ActivityNotFoundException anfe){
            //Fout die wordt getoond als microfoon niet wordt gevonden bijvoorbeeld
            txtSpeechInput.setText("Speech input is not supported");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        //Als we antwoord krijgen van de activity die requestCode 100 had weten we dat we deze moeten opvangen
        //We willen enkel het antwoord dat een goede resultaatcode heeft opvangen
        if(requestCode == 100 && resultCode == RESULT_OK && data != null){
            //Zal het antwoord van de Activity opvangen en in een ArrayList plaatsen
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            //Toont gesproken text op het scherm als de herkenning geslaagd is.
            txtSpeechInput.setText(result.get(0));
        }
    }
}