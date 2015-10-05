package edu.rose_hulman.srproject.humanitarianapp.controllers;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import edu.rose_hulman.srproject.humanitarianapp.R;


/**
 * A placeholder fragment containing a simple view.
 */
public class SplashFragment extends Fragment {
    private TabSwitchListener mListener;

    public SplashFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_splash, container, false);
        ImageButton shipmentsLayout=(ImageButton) v.findViewById(R.id.imageView);
        LinearLayout peopleLayout=(LinearLayout) v.findViewById(R.id.peopleLayout);
        LinearLayout notesLayout=(LinearLayout) v.findViewById(R.id.notesLayout);
        LinearLayout checklistsLayout=(LinearLayout) v.findViewById(R.id.checklistsLayout);
        shipmentsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onProjectsButtonClicked();
            }
        });
        checklistsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onChecklistsButtonClicked();
            }
        });
        notesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onNotesButtonClicked();
            }
        });
        peopleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onPeopleButtonClicked();
            }
        });
        return v;

    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }





    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (TabSwitchListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement TabSwitchListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }





}
