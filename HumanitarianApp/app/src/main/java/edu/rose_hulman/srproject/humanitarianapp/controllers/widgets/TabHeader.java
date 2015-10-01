package edu.rose_hulman.srproject.humanitarianapp.controllers.widgets;


import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import edu.rose_hulman.srproject.humanitarianapp.R;
import edu.rose_hulman.srproject.humanitarianapp.controllers.TabSwitchListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabHeader extends Fragment {

    private TabSwitchListener mListener;
    public TabHeader() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            mListener = (TabSwitchListener) getParentFragment();
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
        Tab shipments=new Tab(this.getActivity(),null, getResources().getString(R.string.shipments));
        shipments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onShipmentsButtonClicked();
            }
        });
        Tab people=new Tab(this.getActivity(),null, getResources().getString(R.string.people));
        people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onPeopleButtonClicked();
            }
        });
        Tab notes=new Tab(this.getActivity(),null, getResources().getString(R.string.notes));
        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onNotesButtonClicked();
            }
        });
        Tab checklists=new Tab(this.getActivity(),null, getResources().getString(R.string.checklists));
        checklists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onChecklistsButtonClicked();
            }
        });
        LinearLayout layout=(LinearLayout) v.findViewById(R.id.tab_header_layout);
        layout.addView(shipments);
        layout.addView(people);
        layout.addView(notes);
        layout.addView(checklists);
        return v;
        
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
