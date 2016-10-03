package com.example.glenn.twist;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.volley.VolleyError;
import com.example.glenn.twist.models.Twist;
import com.example.glenn.twist.network.TwistDownloader;
import com.example.glenn.twist.persistence.Constants;
import com.example.glenn.twist.persistence.TwistDB;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Glenn on 18/08/2016.
 */
public class AddTwistFragment extends Fragment {

    private TwistDownloader downloader;
    private TwistDownloader.VolleyCallBack<Twist> callBack;

    @Bind(R.id.txtTwistTitle)
    EditText txtTwistTitle;

    @Bind(R.id.txtTwistee)
    EditText txtTwistee;

    @Bind(R.id.txtYourAnswer)
    EditText txtYourAnswer;

    @Bind(R.id.txtTwisteeAnswer)
    EditText txtTwistAnswer;

    @Bind(R.id.txtReward)
    EditText txtReward;

    private TwistDB db;

    public AddTwistFragment() {}

    private OnSubmitListener listener;

    public void setOnSubmitListener(OnSubmitListener listener){
        this.listener = listener;
    }

    public interface OnSubmitListener{
        void onEntrySubmit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_add_twist, container, false);
        ButterKnife.bind(this, layout);

        db = new TwistDB(getActivity());
        db.open();

        return layout;
    }

    @OnClick(R.id.btnSave)
    public void save(){

        //Haalt alle ingevoerde gegevens op
        final String title = txtTwistTitle.getText().toString();
        final String twistee = txtTwistee.getText().toString();
        final String yourAnswer = txtYourAnswer.getText().toString();
        final String twistAnswer = txtTwistAnswer.getText().toString();
        final String reward = txtReward.getText().toString();

        //Haalt instantie van downloader op
        downloader = TwistDownloader.getInstance(getContext());

        callBack = new TwistDownloader.VolleyCallBack<Twist>(){

            @Override
            public void onSuccess(Twist result) {
                //Maakt contentvalues aan en steekt er alle gegevens in
                ContentValues values = new ContentValues();
                values.put(Constants.TwistEntry.COLUMN_NAME_TWIST_TITLE,title);
                values.put(Constants.TwistEntry.COLUMN_NAME_TWIST_TWISTEE,twistee);
                values.put(Constants.TwistEntry.COLUMN_NAME_TWIST_MYANSWER,yourAnswer);
                values.put(Constants.TwistEntry.COLUMN_NAME_TWIST_TWISTEEANSWER,twistAnswer);
                values.put(Constants.TwistEntry.COLUMN_NAME_TWIST_REWARD,reward);

                //Voegt toe aan db
                db.insertTwist(Constants.TwistEntry.TABLE_NAME,
                        null,
                        values);

                //Sluit deze fragment af en gaat terug naar vorige
                getFragmentManager().popBackStack();
            }

            @Override
            public void onError(VolleyError error) {
                Log.i("info", "ERROR!");
            }
        };

        downloader.addTwist(callBack, "twist", 1, title, twistee, yourAnswer, twistAnswer, reward);
    }
}
