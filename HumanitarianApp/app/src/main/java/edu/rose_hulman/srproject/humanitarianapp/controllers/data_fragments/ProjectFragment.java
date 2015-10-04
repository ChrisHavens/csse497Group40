package edu.rose_hulman.srproject.humanitarianapp.controllers.data_fragments;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import edu.rose_hulman.srproject.humanitarianapp.R;
import edu.rose_hulman.srproject.humanitarianapp.controllers.ListSelectable;
import edu.rose_hulman.srproject.humanitarianapp.controllers.list_fragments.GroupsListFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.list_fragments.PeopleListFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.list_fragments.ProjectsListFragment;
import edu.rose_hulman.srproject.humanitarianapp.controllers.list_fragments.ShipmentsListFragment;
import edu.rose_hulman.srproject.humanitarianapp.models.Group;
import edu.rose_hulman.srproject.humanitarianapp.models.Project;
import edu.rose_hulman.srproject.humanitarianapp.models.Shipment;
import edu.rose_hulman.srproject.humanitarianapp.models.Worker;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProjectFragment.ProjectFragmentListener} interface
 * to handle interaction events.
 *
 */
public class ProjectFragment extends Fragment implements PeopleListFragment.PeopleListListener, GroupsListFragment.GroupsListListener,
        WorkerFragment.WorkerFragmentListener, GroupFragment.OnFragmentInteractionListener {

    private Group selectedGroup;
    private Worker selectedWorker;
    private ProjectFragmentListener mListener;


    public ProjectFragment() {
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
        View view= inflater.inflate(R.layout.fragment_project, container, false);
        TextView title=(TextView) view.findViewById(R.id.title);
        title.setText(mListener.getSelectedProject().getName());
        Button peopleButton=(Button) view.findViewById(R.id.peopleButton);
        Button groupsButton=(Button) view.findViewById(R.id.groupsButton);
        peopleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPeople();
            }
        });
        groupsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGroups();
            }
        });
        return view;
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (ProjectFragmentListener) getParentFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemSelected(Group group) {
        selectedGroup=group;
        Fragment fragment = new GroupFragment();

        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentContainer, fragment);
        transaction.commit();

    }

    @Override
    public Project getSelectedProject() {
        return mListener.getSelectedProject();
    }

    @Override
    public void onItemSelected(Worker t) {
        selectedWorker=t;
        Fragment fragment = new WorkerFragment();

        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentContainer, fragment);
        transaction.commit();

    }

    @Override
    public Group getSelectedGroup() {
        return selectedGroup;
    }

    @Override
    public Worker getSelectedWorker() {
        return selectedWorker;
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
    public interface ProjectFragmentListener {
        public Project getSelectedProject();
    }


    private void showGroups(){
        Fragment fragment = new GroupsListFragment();

        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentContainer, fragment);
        transaction.commit();
    }
    private void showPeople(){
        Fragment fragment = new PeopleListFragment();

        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentContainer, fragment);
        transaction.commit();
    }

}
