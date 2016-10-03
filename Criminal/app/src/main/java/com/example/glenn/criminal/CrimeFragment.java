package com.example.glenn.criminal;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.glenn.criminal.model.Crime;
import com.example.glenn.criminal.model.DaoSession;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.Date;
import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CrimeFragment extends Fragment {

    private static final String ARG_CRIME_ID = "crime_id";
    private static final String DIALOG_DATE = "DialogDate";

    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_CONTACT = 1;
    private static final int REQUEST_PHOTO= 2;

    private Crime mCrime;
    private File mPhotoFile;
    @Bind(R.id.crime_title)
    EditText mTitleField;
    @Bind(R.id.crime_date)
    Button mDateButton;
    @Bind(R.id.crime_solved)
    CheckBox mSolvedCheckbox;
    @Bind(R.id.crime_report)
    Button mReportButton;
    @Bind(R.id.crime_suspect)
    Button mSuspectButton;
    @Bind(R.id.crime_camera)
    ImageButton mPhotoButton;
    @Bind(R.id.crime_photo)
    ImageView mPhotoView;
    private Callbacks mCallbacks;

    public interface Callbacks {
        void onCrimeUpdated(Crime crime);
    }

    public static CrimeFragment newInstance(Long crimeId) {
        //Maakt nieuwe bundel aan
        Bundle args = new Bundle();
        //Steekt id in bundle
        args.putSerializable(ARG_CRIME_ID, crimeId);

        //Maakt nieuw fragment aan
        CrimeFragment fragment = new CrimeFragment();
        //Geeft args mee
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallbacks = (Callbacks)activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Haalt UUID van crimeId op uit args
        UUID crimeId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
        //Haalt DaoSession op
        DaoSession session = getSession();
        //Haalt crime op die correspondeert met id
        mCrime = session.load(Crime.class, crimeId);
        //Haalt photoFile op die hoort bij crim
        mPhotoFile = session.getPhotoFile(getContext(),mCrime);
    }

    @Override
    public void onPause() {
        super.onPause();

        //update de crime waarop we gepauseerd zijn
        DaoSession session = getSession();
        session.update(mCrime);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime, container, false);

        ButterKnife.bind(this, v);

        //plaatst titel op scherm
        mTitleField.setText(mCrime.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (getActivity() == null) {
                    return;
                }
                //We passen de titel aan in het crime object
                mCrime.setTitle(s.toString());
                //geven aan dat er moet geupdate worden
                updateCrime();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        updateDate();
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Maakt nieuw fragment aan om datum te kiezen
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment
                        .newInstance(mCrime.getDate());
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });

        //We stellen de solved checkbox in met de waarde uit het crime object
        mSolvedCheckbox.setChecked(mCrime.getSolved());
        mSolvedCheckbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //Als dit wordt aangepast moet dit ook geupdate worden in de databank
                mCrime.setSolved(isChecked);
                updateCrime();
            }
        });

        updatePhotoView();
        mPhotoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //We maken een nieuwe intent aan om een foto te nemen
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(mPhotoFile != null){
                    //We starten de nieuwe activiteit en geven aan dat we een resultaat terug willen
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(mPhotoFile));
                    startActivityForResult(intent,REQUEST_PHOTO);
                }

            }
        });

        updateSuspect();
        mSuspectButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //We starten een nieuwe intent om een verdachte te selecteren en geven aan dat we resultaat terug willen
                Intent intent = new Intent(Intent.ACTION_PICK, Contacts.CONTENT_URI);
                startActivityForResult(intent,REQUEST_CONTACT);
            }
        });

        mReportButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //als we op de reportButton drukken tonen we een toast op het scherm
                Toast.makeText(getContext(),getCrimeReport(),Toast.LENGTH_LONG).show();
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data
                    .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mCrime.setDate(date);
            updateCrime();
            updateDate();
        } else if (requestCode == REQUEST_CONTACT && data != null) {
            Uri contactUri = data.getData();
            // Specify which fields you want your query to return
            // values for.
            String[] queryFields = new String[] {
                    ContactsContract.Contacts.DISPLAY_NAME,
            };
            // Perform your query - the contactUri is like a "where"
            // clause here
            ContentResolver resolver = getActivity().getContentResolver();
            Cursor c = resolver
                    .query(contactUri, queryFields, null, null, null);

            try {
                // Double-check that you actually got results
                if (c.getCount() == 0) {
                    return;
                }

                // Pull out the first column of the first row of data -
                // that is your suspect's name.
                c.moveToFirst();

                String suspect = c.getString(0);
                mCrime.setSuspect(suspect);
                updateCrime();
                updateSuspect();
            } finally {
                c.close();
            }
        } else if (requestCode == REQUEST_PHOTO) {
            updateCrime();
            updatePhotoView();
        }
    }

    private void updateSuspect(){
        String suspect = mCrime.getSuspect();
        if(suspect != null){
            mSuspectButton.setText(suspect);
        }
    }

    private void updateCrime() {
        //update de databank met de aangepaste crime
        DaoSession session = getSession();
        session.update(mCrime);
        mCallbacks.onCrimeUpdated(mCrime);
    }

    private void updateDate() {
        mDateButton.setText(mCrime.getDate().toString());
    }

    private String getCrimeReport() {
        String solvedString = null;
        if (mCrime.getSolved()) {
            solvedString = getString(R.string.crime_report_solved);
        } else {
            solvedString = getString(R.string.crime_report_unsolved);
        }

        String dateFormat = "EEE, MMM dd";
        String dateString = DateFormat.format(dateFormat, mCrime.getDate()).toString();
        String suspect = mCrime.getSuspect();

        if (suspect == null) {
            suspect = getString(R.string.crime_report_no_suspect);
        } else {
            suspect = getString(R.string.crime_report_suspect, suspect);
        }

        String report = getString(R.string.crime_report,
                mCrime.getTitle(), dateString, solvedString, suspect);
        return report;
    }

    private void updatePhotoView() {
        Picasso picasso = Picasso.with(getContext());
        picasso.setIndicatorsEnabled(true);
        picasso.load(mPhotoFile)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(mPhotoView);
    }


    private DaoSession getSession(){
        return ((CrimeApplication)getActivity().getApplicationContext()).getDaoSession();
    }
}
