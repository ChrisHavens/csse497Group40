package edu.rose_hulman.srproject.humanitarianapp.controllers.list_fragments;

import android.widget.ListAdapter;

import edu.rose_hulman.srproject.humanitarianapp.controllers.ListSelectable;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the {@link ListSelectable}
 * interface.
 */
public class NotesListFragment extends AbstractListFragment{

    public NotesListFragment(){

    }


    @Override
    public ListAdapter getAdapter() {
        return null;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public void selectItem(int position) {

    }
}
