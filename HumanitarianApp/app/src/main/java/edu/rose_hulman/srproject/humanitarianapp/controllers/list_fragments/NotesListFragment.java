package edu.rose_hulman.srproject.humanitarianapp.controllers.list_fragments;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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
    ArrayList<Note> notes=new ArrayList<>();
    public NotesListFragment(){
        Note a=new Note("Note for Bob");
        a.setBody("Bob-- Please remember to go to Little Village today.");
        notes.add(a);

    }


    @Override
    public ListArrayAdapter<Note> getAdapter() {
        ListArrayAdapter<Note> adapter=new ListArrayAdapter<Note>(getActivity(),
                android.R.layout.simple_list_item_2, getItems()){

            @Override
            public View customiseView(View v, Note note) {
                TextView line1=(TextView) v.findViewById(android.R.id.text1);
                TextView line2=(TextView) v.findViewById(android.R.id.text2);
                line1.setText(note.getTitle());
                line2.setText(note.getLastModified());
                return v;
            }
        };
        return adapter;
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

    public List<Note> getItems(){

        return notes;
    }
    public interface NotesListListener{
        void onItemSelected(Note t);
    }


}