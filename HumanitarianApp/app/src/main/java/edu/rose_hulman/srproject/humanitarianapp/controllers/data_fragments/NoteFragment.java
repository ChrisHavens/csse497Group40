package edu.rose_hulman.srproject.humanitarianapp.controllers.data_fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

import edu.rose_hulman.srproject.humanitarianapp.R;
import edu.rose_hulman.srproject.humanitarianapp.models.Note;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NoteFragment.NoteFragmentListener} interface
 * to handle interaction events.
 *
 */
public class NoteFragment extends Fragment implements AbstractDataFragment{
    
    private NoteFragmentListener mListener;
    EditText title;
    EditText body;
    Note note;
   

    public NoteFragment() {
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
        note=mListener.getSelectedNote();
        View view= inflater.inflate(R.layout.fragment_note, container, false);
        title=(EditText) view.findViewById(R.id.title);
        title.setText(note.getTitle());
        body=(EditText) view.findViewById(R.id.noteBody);
        body.setText(note.getBody());
        final TextView lastMod=(TextView) view.findViewById(R.id.dateTime);
        lastMod.setText(note.getLastModified());
        Button saveButton=(Button)view.findViewById(R.id.saveButton);
        Button cancelButton=(Button) view.findViewById(R.id.cancelButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                String d=c.get(Calendar.YEAR)+"/"+c.get(Calendar.MONTH)+"/"+c.get(Calendar.DAY_OF_MONTH);
                String t=c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE);
                lastMod.setText(d+" "+t);
                mListener.saveNote(title.getText().toString(), body.getText().toString());
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelNote();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (NoteFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement NoteFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    private void cancelNote(){
        title.setText(note.getTitle());
        body.setText(note.getBody());
    }
    @Override
    public void refreshContent() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface NoteFragmentListener {

        Note getSelectedNote();
        void saveNote(String title, String body);

    }

}
