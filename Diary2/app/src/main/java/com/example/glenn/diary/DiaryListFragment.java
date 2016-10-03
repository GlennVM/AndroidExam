package com.example.glenn.diary;

import android.app.ListFragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.glenn.diary.persistence.Constants;
import com.example.glenn.diary.persistence.DiaryDB;
import com.example.glenn.diary.util.DateFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;

/**
 * Created by Glenn on 18/08/2016.
 */
public class DiaryListFragment extends ListFragment {

    private DiaryDB db;
    private SimpleCursorAdapter adapter;
    private OnItemClickedListener listener;

    @Bind(R.id.list)
    ListView listDiaries;

    public interface OnItemClickedListener {
        void onItemClicked(String[] args);
    }

    public void setOnItemClickedListener(OnItemClickedListener listener) {
        this.listener = listener;
    }

    public DiaryListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //Maakt nieuwe databaseObject aan
        db = new DiaryDB(getActivity());
        //Opent de database
        db.open();

        //Array die de projectie van de database voorstelt
        String[] projection = {
                Constants.DiaryEntry._ID,
                Constants.DiaryEntry.COLUMN_NAME_DIARY_TITLE,
                Constants.DiaryEntry.COLUMN_NAME_DIARY_CONTENT,
                Constants.DiaryEntry.COLUMN_NAME_DIARY_DATE
        };

        //maakt string om de order van de gegevens uit de db voor te stellen
        String sortOrder = Constants.DiaryEntry.COLUMN_NAME_DIARY_DATE+ " DESC";

        //Vraagt alle diaries op uit de databank
        //Dit wordt teruggeven als een CursorObject
        Cursor c = db.getDiaries(Constants.DiaryEntry.TABLE_NAME,
                projection, null, null, null, null, sortOrder, null);

        //We maken een nieuwe CursorAdapter aan
        //Hierin steken we de layout die elke entry moet hebben
        //We geven de opgehaalde cursor met de gegevens mee
        //We geven de namen van de kolommen mee die we willen gebruiken
        //We zeggen waar hij deze dient in te vullen in de view
        adapter = new SimpleCursorAdapter(getContext(),
                R.layout.diary_entry,
                c,
                new String[] {Constants.DiaryEntry.COLUMN_NAME_DIARY_TITLE,Constants.DiaryEntry.COLUMN_NAME_DIARY_DATE},
                new int[]{R.id.diary_entry_title,R.id.diary_entry_date},
                0);

        //We voegen een viewBinder toe, deze hebben we nodig voor de datum
        adapter.setViewBinder( new SimpleCursorAdapter.ViewBinder(){
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                //We controlleren het veld het datum veld is, als dit het geval is
                if(columnIndex == 3){
                    //halen we de datum op uit de cursor
                    long date = cursor.getLong(columnIndex);
                    //We halen de textView op
                    TextView dateView = (TextView) view;
                    //We maken nieuwe datumFormats aan
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
                    //We maken een nieuwe datum
                    Date recordDate = new Date(date);
                    //We maken een nieuwe datuFormatter
                    DateFormatter dateFormatter = new DateFormatter();

                    //We formatteren de datum uit de cursor en voeren deze toe aan de view
                    dateView.setText(dateFormatter.formatDate(recordDate));
                    return true;
                }
                return false;
            }
        });
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        //We roepen de basisimplementatie van de onClickListener aan
        super.onListItemClick(l, v, position, id);

        //We halen de SQLCursor voor de huidige positie aan
        SQLiteCursor wrapper = (SQLiteCursor) l.getItemAtPosition(position);

        //We halen de waarden op
        String title= wrapper.getString(wrapper.getColumnIndex(Constants.DiaryEntry.COLUMN_NAME_DIARY_TITLE));
        String content = wrapper.getString(wrapper.getColumnIndex(Constants.DiaryEntry.COLUMN_NAME_DIARY_CONTENT));
        String date = wrapper.getString(wrapper.getColumnIndex(Constants.DiaryEntry.COLUMN_NAME_DIARY_DATE));
        String[] arguments = {title,content,date};

        //We geven deze daar aan de onclickListener die is gedefinieerd in de Activity
        listener.onItemClicked(arguments);
    }
}
