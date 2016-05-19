package edu.rose_hulman.srproject.humanitarianapp.controllers.list_fragments;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.rose_hulman.srproject.humanitarianapp.R;
import edu.rose_hulman.srproject.humanitarianapp.controllers.Interfaces;
import edu.rose_hulman.srproject.humanitarianapp.controllers.adapters.ListArrayAdapter;
import edu.rose_hulman.srproject.humanitarianapp.localdata.ApplicationWideData;
import edu.rose_hulman.srproject.humanitarianapp.localdata.LocalDataSaver;
import edu.rose_hulman.srproject.humanitarianapp.models.Checklist;
import edu.rose_hulman.srproject.humanitarianapp.models.Group;
import edu.rose_hulman.srproject.humanitarianapp.models.Note;
import edu.rose_hulman.srproject.humanitarianapp.nonlocaldata.NonLocalDataService;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the
 * interface.
 */
public class NotesListFragment extends AbstractListFragment<Note>{
    protected NotesListListener mListener;
    HashMap<Long, Note> notes=new HashMap<>();
    ListArrayAdapter<Note> adapter;
    private boolean showHidden=false;
    public NotesListFragment(){
//        Note a=new Note("Note for Bob");
//        a.setBody("Bob-- Please remember to go to Little Village today.");
//        notes.add(a);

    }


    @Override
    public ListArrayAdapter<Note> getAdapter() {
        adapter=new ListArrayAdapter<Note>(getActivity(),
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
            mListener = (NotesListListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement ListSelectable<T>");
        }
        if (mListener==null){
            throw new NullPointerException("Parent fragment is null");
        }
        updateItems();
//        service.getAllNotes(mListener.getSelectedGroup(), showHidden, new NoteListCallback());
    }

    @Override
    public void updateItems() {
        Group g= mListener.getSelectedGroup();
        long gId=g.getID();
        List<Note> allNotes=ApplicationWideData.getAllNotes();
        Toast.makeText(getActivity(), allNotes.size()+"", Toast.LENGTH_SHORT).show();
        //adapter.clear();
        for (Note c: allNotes){
            if (c.getParentID()==gId){
                notes.put(c.getID(), c);
            }
        }
        if (!ApplicationWideData.manualSnyc) {
            NonLocalDataService service = new NonLocalDataService();
            showHidden = mListener.getShowHidden();
            service.service.getNoteList(showHidden, mListener.getSelectedGroup().getID() + "", new NoteListCallback());
        } else{
            for (long l: notes.keySet()) {
                Note n = notes.get(l);
                adapter.add(n);
                adapter.notifyDataSetChanged();
                }
            }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public String getTitle() {
        return getResources().getString(R.string.notes);
    }

    @Override
    public void onItemSelected(Note note) {
        mListener.onItemSelected(note);
    }

    public void checkForArgs(){

    }

    public List<Note> getItems(){
        List<Note> l=new ArrayList<>();
        l.addAll(notes.values());
        return l;
    }
    public class NoteListCallback implements Callback<Response> {

        @Override
        public void success(Response response, Response response2) {
            Log.e("here", "success");
            ObjectMapper mapper=new ObjectMapper();
            TypeReference<HashMap<String, Object>> typeReference=
                    new TypeReference<HashMap<String, Object>>() {
                    };
            try {
                HashMap<String, Object> o=mapper.readValue(response.getBody().in(), typeReference);

                ArrayList<HashMap<String, Object>> list=(ArrayList)((HashMap) o.get("hits")).get("hits");
                for (HashMap<String, Object> map: list){

                    HashMap<String, Object> source=(HashMap)map.get("_source");

                    Note n=Note.parseJSON(Long.parseLong((String)map.get("_id")), source);

                    notes.put(n.getID(), n);
                    LocalDataSaver.saveNote(n);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (long l: notes.keySet()) {
                Note n = notes.get(l);
                adapter.add(n);
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void failure(RetrofitError error) {
            Log.e("RetrofitError", "NotesListCallback: "+error.getMessage());
        }
    }
    public interface NotesListListener extends Interfaces.UserIDGetter{
        void onItemSelected(Note t);
        boolean getShowHidden();
        Group getSelectedGroup();
    }


}