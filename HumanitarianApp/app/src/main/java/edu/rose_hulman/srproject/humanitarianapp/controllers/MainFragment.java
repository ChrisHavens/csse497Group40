package edu.rose_hulman.srproject.humanitarianapp.controllers;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import edu.rose_hulman.srproject.humanitarianapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TabSwitchListener} interface
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private TabHost mTabHost;
    private TabSwitchListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_main, container, false);
        mTabHost = (TabHost)v.findViewById(android.R.id.tabhost);
        mTabHost.setup();
        addTabs();
        return v;
    }
    private void addTabs(){
        TabHost.TabSpec shipments=mTabHost.newTabSpec(this.getResources().getString(R.string.shipments));
        TabHost.TabSpec people=mTabHost.newTabSpec(this.getResources().getString(R.string.people));
        TabHost.TabSpec checklists=mTabHost.newTabSpec(this.getResources().getString(R.string.checklists));
        TabHost.TabSpec notes=mTabHost.newTabSpec(this.getResources().getString(R.string.notes));
        mTabHost.addTab(shipments);
        mTabHost.addTab(people);
        mTabHost.addTab(checklists);
        mTabHost.addTab(notes);
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
