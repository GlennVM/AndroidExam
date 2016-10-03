package com.example.glenn.a2048;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.btnAbout)
    public Button btnAbout;
    @Bind(R.id.btnExit)
    public Button btnExit;
    @Bind(R.id.btnContinue)
    public Button btnContinue;
    @Bind(R.id.btnNewGame)
    public Button btnNewGame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle("UI2048");
    }

    @OnClick(R.id.btnContinue)
    public void continueGame(){
        //Maakt nieuwe intent om naar de volgende activity te gaan. We kiezen de PlayActivity
        Intent intent=new Intent(getApplicationContext(),PlayActivity.class);
        //We zetten continue op waar, om verder te kunnen spelen
        intent.putExtra("continue", true);
        //Start de nieuwe activiteit
        startActivity(intent);
    }

    @OnClick(R.id.btnNewGame)
    public void newGame(){
        //Maakt nieuwe intent om naar de volgende activity te gaan. We kiezen de PlayActivity
        Intent intent=new Intent(getApplicationContext(),PlayActivity.class);
        //we zetten continue op false om een nieuw spel op te starten
        intent.putExtra("continue",false);
        //start de nieuwe activiteit
        startActivity(intent);
    }

    @OnClick(R.id.btnAbout)
    public void about(View view) {
        String title = getResources().getString(R.string.dialog_about_title);
        String msg = getResources().getString(R.string.dialog_about_description);
        Context context = (Context) this;

        //Maakt builder om een dialoogscherm te bouwen
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        //Voegt een titel toe
        builder.setTitle(title);
        //Voegt een bericht toe
        builder.setMessage(msg);
        //Voegt een cancel button toe
        builder.setCancelable(true);
        //Voeg een OK button toe en voegt daar een onclickListener aan toe. Hier zou je een actie aan kunnen koppelen
        //Ik heb ervoor gekozen dit niet te doen.
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        //Toont de net opgebouwde dialoogbox
        builder.show();
    }

    //Sluit de app af.
    @OnClick(R.id.btnExit)
    public void exitApp() {
        this.finish();
    }
}