package com.example.glenn.diary;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.glenn.diary.util.DateFormatter;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Glenn on 18/08/2016.
 */
public class DiaryDetailFragment extends Fragment {

    @Bind(R.id.diary_detail_title)
    TextView txtTitle;
    @Bind(R.id.diary_detail_content)
    TextView txtContent;
    @Bind(R.id.diary_detail_date)
    TextView txtDate;


    public DiaryDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout =  inflater.inflate(R.layout.fragment_diary_detail, container, false);
        ButterKnife.bind(this,layout);

        //Haalt argumenten op die zijn meegegeven door activity
        Bundle args = getArguments();
        //Haalt gegevens op die komen van de activity
        String title = args.getString("title");
        String content = args.getString("content");
        String date = args.getString("date");

        //stelt deze gegevens in op het scherm
        txtTitle.setText(title);
        txtContent.setText(content);
        DateFormatter dateFormatter = new DateFormatter();
        Date d = new Date(Long.valueOf(date));
        txtDate.setText(dateFormatter.formatDate(d));

        return layout;
    }
}

