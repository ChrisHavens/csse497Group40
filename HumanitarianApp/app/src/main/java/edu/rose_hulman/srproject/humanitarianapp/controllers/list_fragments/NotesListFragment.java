package edu.rose_hulman.srproject.humanitarianapp.controllers.list_fragments;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.rose_hulman.srproject.humanitarianapp.R;
import edu.rose_hulman.srproject.humanitarianapp.controllers.Interfaces;
import edu.rose_hulman.srproject.humanitarianapp.controllers.adapters.ListArrayAdapter;
import edu.rose_hulman.srproject.humanitarianapp.localdata.LocalDataSaver;
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
    ArrayList<Note> notes=new ArrayList<>();
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
        NonLocalDataService service=new NonLocalDataService();
        showHidden=mListener.getShowHidden();
        service.service.getNoteList(showHidden, mListener.getSelectedGroup().getId()+"", new NoteListCallback());
//        service.getAllNotes(mListener.getSelectedGroup(), showHidden, new NoteListCallback());
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

        return notes;
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
                    for (String s: source.keySet()){
                        Log.e("Result", s);
                    }
                    Note n=new Note(Integer.parseInt(((String)map.get("_id"))));
                    n.setTitle((String) source.get("name"));
                    if(source.get("dateArchived") == null)
                        n.setHidden(false);
                    else
                        n.setHidden(true);
                    n.setBody((String) source.get("contents"));
                    n.setLastModified((String) source.get("lastModTime"));
                    notes.add(n);
                    //LocalDataSaver.addNote(n);
                    adapter.notifyDataSetChanged();
                    //adapter.add(p);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void failure(RetrofitError error) {
            Log.e("RetrofitError", error.getMessage());
        }
    }
    public interface NotesListListener extends Interfaces.UserIDGetter{
        void onItemSelected(Note t);
        boolean getShowHidden();
        Group getSelectedGroup();
    }


}