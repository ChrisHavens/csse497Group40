package edu.rose_hulman.srproject.humanitarianapp.controllers.list_fragments;

import android.app.Activity;

import edu.rose_hulman.srproject.humanitarianapp.controllers.Backable;
import edu.rose_hulman.srproject.humanitarianapp.controllers.adapters.ListArrayAdapter;
import edu.rose_hulman.srproject.humanitarianapp.models.Note;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the {@link Backable}
 * interface.
 */
public class NotesListFragment extends AbstractListFragment<Note>{
    protected NotesListListener mListener;
    public NotesListFragment(){

    }


    @Override
    public ListArrayAdapter<Note> getAdapter() {
        return null;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (NotesListListener) getParentFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement ListSelectable<T>");
        }
        if (mListener==null){
            throw new NullPointerException("Parent fragment is null");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public void onItemSelected(Note note) {
        mListener.onItemSelected(note);
    }
    public interface NotesListListener{
        void onItemSelected(Note t);
    }


}