package com.example.glenn.reason;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.glenn.reason.persistence.Constants;
import com.example.glenn.reason.persistence.ReasonDB;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Glenn on 16/08/2016.
 */
public class ReasonAddFragment extends Fragment {
    @Bind(R.id.twistTitle)
    EditText Title;

    @Bind(R.id.txtTwistee)
    EditText twistee;

    @Bind(R.id.txtYourAnswer)
    EditText yourAnswer;

    @Bind(R.id.txtTwisteeAnswer)
    EditText twisteeAnswer;

    @Bind(R.id.txtReward)
    EditText reward;

    ReasonDB db;

    public ReasonAddFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        db = new ReasonDB(getActivity());
        db.open();
        View layout = inflater.inflate(R.layout.fragment_add_twist, container, false);
        Button button = (Button) layout.findViewById(R.id.btnSave);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String title = Title.getText().toString();
                String twisteeString = twistee.getText().toString();

                ContentValues values = new ContentValues();
                values.put(Constants.TwistEntry.COLUMN_NAME_TWIST_TITLE,title);
                values.put(Constants.TwistEntry.COLUMN_NAME_TWISTEE,twisteeString);
                values.put(Constants.TwistEntry.COLUMN_NAME_TWIST_REWARD,reward.getText().toString());

                db.insertReason(Constants.TwistEntry.TABLE_NAME,
                        null,
                        values);


                /** int id = Twists.twists.length - 4;
                Twists.twists[id] = Title.getText().toString();
                Twists.twistees[id] = twistee.getText().toString();
                Twists.yourAnswer[id] = yourAnswer.getText().toString();
                Twists.twisteeAnswer[id] = twisteeAnswer.getText().toString();
                Twists.Reward[id] = reward.getText().toString();*/

                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        ButterKnife.bind(this, layout);
        return layout;
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
