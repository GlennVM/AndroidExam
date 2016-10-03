package com.example.glenn.twist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.volley.VolleyError;
import com.example.glenn.twist.models.User;
import com.example.glenn.twist.network.TwistDownloader;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Glenn on 18/08/2016.
 */
public class LoginFragment extends Fragment {
    private TwistDownloader downloader;
    private TwistDownloader.VolleyCallBack<List<User>> callBack;


    @Bind(R.id.txtName)
    EditText name;

    @Bind(R.id.txtPass)
    EditText pass;

    public LoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //inflate login view in deze fragment
        View layout = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, layout);
        name.setText("Glenn");
        pass.setText("123456789");
        return layout;
    }

    @OnClick(R.id.btnSubmit)
    public void login() {
        //haalt downloader op
        downloader = TwistDownloader.getInstance(getContext());

        callBack = new TwistDownloader.VolleyCallBack<List<User>>(){

            @Override
            public void onSuccess(List<User> result) {
                //Als login geslaagd is, maken we nieuwe activity aan
                Intent intent = new Intent(getActivity(), TwistActivity.class);
                //Geef id door
                intent.putExtra("userID", "" + result.get(0).getID());
                //Start activiteit
                startActivity(intent);
            }

            @Override
            public void onError(VolleyError error) {
                Log.i("info", "ERROR!");
            }
        };

        downloader.getUser(callBack, "login", name.getText().toString(), pass.getText().toString());
    }
}
