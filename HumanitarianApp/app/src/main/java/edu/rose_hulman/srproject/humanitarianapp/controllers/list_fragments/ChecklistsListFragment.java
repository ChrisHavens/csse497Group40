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
public class ChecklistsListFragment extends AbstractListFragment {

    public ChecklistsListFragment(){

    }


    @Override
    public ListAdapter getAdapter() {
//        new ArrayAdapter<DummyContent.DummyItem>(getActivity(),
//                android.R.layout.simple_list_item_1, android.R.id.text1, DummyContent.ITEMS);
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