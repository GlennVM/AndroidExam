package com.example.glenn.diary;

import android.app.Fragment;
import android.content.ContentValues;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.glenn.diary.persistence.Constants;
import com.example.glenn.diary.persistence.DiaryDB;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Glenn on 18/08/2016.
 */
public class DiaryAddFragment extends Fragment
    {
        @Bind(R.id.btnSubmit)
        Button btnSubmit;
        @Bind(R.id.editTitle)
        EditText editTitle;
        @Bind(R.id.editContent)
        EditText editContent;

        private OnSubmitListener listener;

    public void setOnSubmitListener(OnSubmitListener listener){
        this.listener = listener;
    }

    public interface OnSubmitListener{
        void onEntrySubmit();
    }

    private DiaryDB db;


    public DiaryAddFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_diary_add, container, false);
        ButterKnife.bind(this, layout);

        //Maakt nieuwe instantie van de db
        db = new DiaryDB(getActivity());
        db.open();

        return layout;
    }


    @OnClick(R.id.btnSubmit)
    public void addEntry(){
        //Haalt gegevens uit de view op
        String title = editTitle.getText().toString();
        String content = editContent.getText().toString();

        //Maakt nieuw contentValues object aan om naar db te schrijven
        ContentValues values = new ContentValues();
        //Steekt gegevens in de contentvalues met de juiste veld
        values.put(Constants.DiaryEntry.COLUMN_NAME_DIARY_TITLE,title);
        values.put(Constants.DiaryEntry.COLUMN_NAME_DIARY_CONTENT, content);
        values.put(Constants.DiaryEntry.COLUMN_NAME_DIARY_DATE, Long.valueOf(System.currentTimeMillis()));

        //Voegt nieuwe diary toe aan db
        db.insertDiary(Constants.DiaryEntry.TABLE_NAME,
                null,
                values);

        //Voert listener uit die gedefinieerd is in de activity
        listener.onEntrySubmit();
    }
}
