package com.example.glenn.twist;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.glenn.twist.models.Twist;
import com.example.glenn.twist.network.TwistDownloader;
import com.example.glenn.twist.persistence.Constants;
import com.example.glenn.twist.persistence.TwistDB;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Glenn on 19/08/2016.
 */
public class TwistDetailFragment extends Fragment {

    TwistDB db;
    private TwistDownloader downloader;
    private TwistDownloader.VolleyCallBack<Twist> callBack;

    @Bind(R.id.txtTwistDetailTitle)
    TextView txtTitle;

    @Bind(R.id.txtDetailTwistee)
    TextView txtTwistee;

    @Bind(R.id.txtDetailYourAnswer)
    TextView txtYourAnswer;

    @Bind(R.id.txtDetailTwisteeAnswer)
    TextView txtTwisteeAnswer;

    @Bind(R.id.txtDetailReward)
    TextView txtReward;

    @Bind(R.id.btnTwisteeRight)
    Button btnTwisteeRight;

    @Bind(R.id.btnIRight)
    Button btnIRight;

    @Bind(R.id.txtDetailWon)
    TextView txtDetailWon;

    public TwistDetailFragment (){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Maakt nieuwe instantie van de db aan
        db = new TwistDB(getActivity());
        db.open();

        //Inflate layout in fragment
        View layout = inflater.inflate(R.layout.fragment_twist_detail, container, false);
        ButterKnife.bind(this, layout);

        //Haalt doorgegeven argumenten op
        Bundle args = getArguments();
        Log.i("info", "" + args.toString());
        String title = args.getString("title");
        String twistee = args.getString("twistee");
        String yourAnswer = args.getString("myAnswer");
        String twisteeAnswer = args.getString("twisteeAnswer");
        String reward = args.getString("reward");
        String won = args.getString("won");

        //Plaatst de gegevens in de txtViews op het scherm
        txtTitle.setText(title);
        txtTwistee.setText(twistee);
        txtYourAnswer.setText(yourAnswer);
        txtTwisteeAnswer.setText(twisteeAnswer);
        txtReward.setText(reward);
        btnTwisteeRight.setText(twistee + " is right");
        txtDetailWon.setVisibility(View.GONE);

        //Controlleert of er al een winnaar beslecht
        //Afhankelijk van de uitkomst toont hij knoppen of label met naam winnaar
        if (won!=null) {
            btnTwisteeRight.setVisibility(View.GONE);
            btnIRight.setVisibility(View.GONE);
            if (won.equals("1")) {
                txtDetailWon.setText("You won");
                txtDetailWon.setVisibility(View.VISIBLE);
            } else {
                txtDetailWon.setText(twistee + " won");
                txtDetailWon.setVisibility(View.VISIBLE);
            }
        }

        return layout;
    }

    @OnClick(R.id.btnIRight)
    public void iRight(){
        ContentValues values = new ContentValues();
        values.put(Constants.TwistEntry.COLUMN_NAME_TWIST_WON, 1);
        String where = "title=?";
        db.updateTwist(Constants.TwistEntry.TABLE_NAME,values,where,new String[]{txtTitle.getText().toString()});
        btnTwisteeRight.setVisibility(View.GONE);
        btnIRight.setVisibility(View.GONE);
        txtDetailWon.setText("You won");
        txtDetailWon.setVisibility(View.VISIBLE);


        downloader = TwistDownloader.getInstance(getContext());

        callBack = new TwistDownloader.VolleyCallBack<Twist>(){

            @Override
            public void onSuccess(Twist result) {
                Log.i("info", "SUCCESSSSS!!!!");
            }

            @Override
            public void onError(VolleyError error) {
                Log.i("info", error.toString());
            }
        };

        downloader.twistWon(callBack, "won", 1, txtTitle.getText().toString(), true);
    }

    @OnClick(R.id.btnTwisteeRight)
    public void twisteeRight(){
        ContentValues values = new ContentValues();
        values.put(Constants.TwistEntry.COLUMN_NAME_TWIST_WON, 0);
        String where = "title=?";
        db.updateTwist(Constants.TwistEntry.TABLE_NAME,values,where,new String[]{txtTitle.getText().toString()});
        btnTwisteeRight.setVisibility(View.GONE);
        btnIRight.setVisibility(View.GONE);
        txtDetailWon.setText(txtTwistee.getText().toString() + " won");
        txtDetailWon.setVisibility(View.VISIBLE);

        downloader = TwistDownloader.getInstance(getContext());

        callBack = new TwistDownloader.VolleyCallBack<Twist>(){

            @Override
            public void onSuccess(Twist result) {
                Log.i("info", "SUCCESSSSS!!!!");
            }

            @Override
            public void onError(VolleyError error) {
                Log.i("info", error.toString());
            }
        };

        downloader.twistWon(callBack, "won", 1, txtTitle.getText().toString(), false);
    }
}
