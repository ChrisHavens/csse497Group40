package edu.rose_hulman.srproject.humanitarianapp.controllers;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.rose_hulman.srproject.humanitarianapp.R;
import edu.rose_hulman.srproject.humanitarianapp.controllers.list_fragments.ShipmentsListFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.widgets.TabHeader;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TabSwitchListener} interface
 * to handle interaction events.
 *
 */
public class MainFragment extends Fragment implements TabSwitchListener{




    private ShipmentsListFragment shipmentsListFragment;

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
        Fragment fragment = new TabHeader();

        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.tabheadercontainer, fragment);
        transaction.commit();




//        mTabHost = (TabHost)v.findViewById(android.R.id.tabhost);
//        mTabHost.setup();
//        addTabs();
        return v;
    }
    private void addTabs(){
//        TabHost.TabSpec shipments=mTabHost.newTabSpec(this.getResources().getString(R.string.shipments));
//        TabHost.TabSpec people=mTabHost.newTabSpec(this.getResources().getString(R.string.people));
//        TabHost.TabSpec checklists=mTabHost.newTabSpec(this.getResources().getString(R.string.checklists));
//        TabHost.TabSpec notes=mTabHost.newTabSpec(this.getResources().getString(R.string.notes));
//        mTabHost.addTab(shipments);
//        mTabHost.addTab(people);
//        mTabHost.addTab(checklists);
//        mTabHost.addTab(notes);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


    @Override
    public void onShipmentsButtonClicked() {
        Fragment fragment = new ShipmentsListFragment();

        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.tabContentContainer, fragment);
        transaction.commit();
    }

    @Override
    public void onPeopleButtonClicked() {

    }

    @Override
    public void onNotesButtonClicked() {

    }

    @Override
    public void onChecklistsButtonClicked() {

    }
}
