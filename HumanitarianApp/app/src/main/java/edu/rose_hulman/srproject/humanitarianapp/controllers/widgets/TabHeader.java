package edu.rose_hulman.srproject.humanitarianapp.controllers.widgets;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import edu.rose_hulman.srproject.humanitarianapp.R;
import edu.rose_hulman.srproject.humanitarianapp.controllers.CRUDInterface;
import edu.rose_hulman.srproject.humanitarianapp.controllers.Backable;
import edu.rose_hulman.srproject.humanitarianapp.controllers.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabHeader extends Fragment {
    private Button addButton;
    private Button editButton;
    private Button hideButton;
    private Backable mListener;
    private CRUDInterface CRUDInterface;
    public TabHeader() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            mListener = (Backable) getParentFragment();
            Log.wtf("s40", getParentFragment().toString());
        } catch (ClassCastException e) {
            throw new ClassCastException(getParentFragment().toString()
                    + " must implement TabSwitchListener");
        } catch (NullPointerException e){

            throw new NullPointerException("Parent Fragment is null");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_tab_header, container, false);
        Button back=(Button) v.findViewById(R.id.button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.goBack();
            }
        });
        Button myLoc=(Button)v.findViewById(R.id.button2);
        myLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Checking In", Toast.LENGTH_LONG).show();
                ((MainActivity)getActivity()).checkIn();
            }
        });
        addButton=(Button)v.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CRUDInterface.add();
            }
        });

        editButton=(Button)v.findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CRUDInterface.edit();
            }
        });

        hideButton=(Button)v.findViewById(R.id.hideButton);
        hideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CRUDInterface.hide();
            }
        });
//        Tab projects=new Tab(this.getActivity(),null, getResources().getString(R.string.projects));
//        projects.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mListener.onProjectsButtonClicked();
//            }
//        });
//        Tab people=new Tab(this.getActivity(),null, getResources().getString(R.string.people));
//        people.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mListener.onPeopleButtonClicked();
//            }
//        });
//        Tab notes=new Tab(this.getActivity(),null, getResources().getString(R.string.notes));
//        notes.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mListener.onNotesButtonClicked();
//            }
//        });
//        Tab checklists=new Tab(this.getActivity(),null, getResources().getString(R.string.checklists));
//        checklists.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mListener.onChecklistsButtonClicked();
//            }
//        });
//        LinearLayout layout=(LinearLayout) v.findViewById(R.id.tab_header_layout);
//        layout.addView(projects);
//        layout.addView(people);
//        layout.addView(notes);
//        layout.addView(checklists);
        return v;
        
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (Backable) getParentFragment();
            CRUDInterface =(CRUDInterface)getParentFragment();
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
    public void setAddButtonVisible(int visible){
        addButton.setVisibility(visible);
    }
    public void setEditButtonVisible(int visible){
        editButton.setVisibility(visible);
    }
    public void setHideButtonVisible(int visible){  hideButton.setVisibility(visible);
    }



}
