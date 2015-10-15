package edu.rose_hulman.srproject.humanitarianapp.controllers.data_fragments;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import edu.rose_hulman.srproject.humanitarianapp.R;
import edu.rose_hulman.srproject.humanitarianapp.controllers.MainActivity;
import edu.rose_hulman.srproject.humanitarianapp.models.Person;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WorkerFragment.WorkerFragmentListener} interface
 * to handle interaction events.
 *
 */
public class WorkerFragment extends Fragment {

    private WorkerFragmentListener mListener;



    public WorkerFragment() {
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
        View v=inflater.inflate(R.layout.fragment_worker, container, false);
        TextView name=(TextView) v.findViewById(R.id.nameField);
        final TextView phone=(TextView) v.findViewById(R.id.phoneNumberField);
        name.setText(mListener.getSelectedPerson().getName());
        phone.setText(mListener.getSelectedPerson().getPhoneNumber());
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu menu=new PopupMenu(getActivity(), phone);
                menu.getMenuInflater().inflate(R.menu.menu_call, menu.getMenu());
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId()==R.id.action_call) {
                            ((MainActivity) getActivity()).makePhoneCall(mListener.getSelectedPerson().getPhoneNumber());
                        }
                        else{
                            ((MainActivity) getActivity()).makeText(mListener.getSelectedPerson().getPhoneNumber());
                        }
                        return true;
                    }
                });
                menu.show();
            }
        });
        return v;
    }

   
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (WorkerFragmentListener) getParentFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement WorkerFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
    public interface WorkerFragmentListener {

        public Person getSelectedPerson();
    }

}
