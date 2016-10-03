package com.example.glenn.intents;

import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class VariousIntents extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Stelt de juiste view in
        setContentView(R.layout.activity_various_intents);
        ButterKnife.bind(this);
        //Zal de titel in de actionBar aanpassen
        getSupportActionBar().setTitle("Various Intents");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Voegt het menu uit de res toe aan de actionBar op de juiste plaats waar menu dient te staan
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Deze functie zal clicks in het menu afhandelen
        //Vraagt het id op van het menuItem dat aangeklickt is
        int id = item.getItemId();

        //Zal het id van het aangeklickte menuItem vergelijken met dat van de verschillende acties
        switch(id){
            //Als het gelijk is met dat van de andere ActivityActie moeten we de andere activity starten
            case R.id.action_speechintent:
                //We maken een nieuwe intent en geven daar de huidige activity aan mee, een geven de activity mee die we willen starten
                Intent intent = new Intent(VariousIntents.this, MainActivity.class);
                //We starten de nieuwe activity
                startActivity(intent);
                break;
            //Als de huidige activityActie wordt geselecteerd moet er niets gebeuren
            case R.id.action_multipleintents:break;
        }
        return super.onOptionsItemSelected(item);
    }



    @OnClick(R.id.btnOpenWebsite)
    public void openWebsite(){
        //Maakt een AlertBuilder aan
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //voegt titel toe aan builder
        builder.setTitle("Type in the url");
        //voegt inputText toe aan builder
        final EditText inputWebsite = new EditText(this);
        //stelt inputtype in voor editText
        inputWebsite.setInputType(InputType.TYPE_CLASS_TEXT);
        //stelt view in bij de builder
        builder.setView(inputWebsite);
        //creëert knop om te bevestigen
        builder.setPositiveButton("Go", new DialogInterface.OnClickListener() {
            //Voegt een clickListener toe
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Als er op go geclickt wordt dienen we naar de website te gaan
                //We maken een nieuwe intent aan
                Intent intent = new Intent(Intent.ACTION_VIEW);
                //We halen de url op die is ingevoerd
                String url = inputWebsite.getText().toString();
                //Als de url http:// bevat is hij geldig en kunnen we hem gebruiken
                if (url.contains("http://")) {
                    //We stellen de data in in de intent, en parsen de string naar een uri
                    intent.setData(Uri.parse(url));
                }else{
                    //Als het nog geen http:// bevat zullen we die zelf toevoegen en de data instellen in de intent
                    intent.setData(Uri.parse("http://"+url));
                }
                //We starten de net aangemaakt intent
                //We moeten hier geen data terugkrijgen dus we moeten niet wachten op een result
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            //We voegen ook een cancel button toe aan de builder
            // en hangen hier een listener aan
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Als er op cancel wordt geklickt sluiten we het dialoogvenster
                dialog.cancel();
            }
        });
        builder.show();
    }

    @OnClick(R.id.btnOpenContacts)
    public void openContacts(){
        //Creëert nieuwe intent om contacten te tonen
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        //Ook hier moeten we geen result terugrkijgen
        startActivity(intent);
    }

    @OnClick(R.id.btnOpenDialer)
    public void openDialer(){
        //We maken een nieuwe intent aan die de dialer zal openen
        Intent intent = new Intent(Intent.ACTION_DIAL);
        //We moeten niet wachten op een resultaat dus kunnen we deze gewoon starten
        startActivity(intent);
    }

    @OnClick(R.id.btnSearchGoogle)
    public void searchGoogle(){
        //We maken een nieuwe dialoogbuilder aan
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //We stellen de titel van het dialoogvenster in
        builder.setTitle("What do you want to search?");
        //We maken een nieuw textvak aan
        final EditText inputQuery = new EditText(this);
        //We stellen het inputType in
        inputQuery.setInputType(InputType.TYPE_CLASS_TEXT);
        //We voegen het aangemaakte textvak toe aan het dialoogvenster
        builder.setView(inputQuery);
        //We voegen een positieve button toe met de titel search
        builder.setPositiveButton("Search", new DialogInterface.OnClickListener() {
            //We hangen een listener vast aan deze knop
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //We maken een nieuwe intent aan die een websearch zal uitvoeren
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                //We halen de ingetypte zoekterm op uit het textvak
                String query = inputQuery.getText().toString();
                //we geven deze zoekterm mee aan de intent
                intent.putExtra(SearchManager.QUERY, query);
                //We starten de activiteit zonder dat we antwoord moeten terugkrijgen
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            //We voegen ook een cancel button toe en hangen er een listener aan
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Sluit het dialoogvenster af
                dialog.cancel();
            }
        });

        builder.show();
    }

    @OnClick(R.id.btnStartVoiceCommand)
    public void startVoiceCommand(){
        try {
            //maakt een nieuwe intent aan die op een voice commando zal uitvoeren
            Intent intent = new Intent(Intent.ACTION_VOICE_COMMAND);
            //We starten deze activiteit zonder dat we wachten op antwoord
            startActivity(intent);
        }catch(ActivityNotFoundException e){
            e.printStackTrace();
        }
    }
}

