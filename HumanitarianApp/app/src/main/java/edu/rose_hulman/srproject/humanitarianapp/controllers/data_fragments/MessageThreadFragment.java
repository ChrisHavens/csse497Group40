package edu.rose_hulman.srproject.humanitarianapp.controllers.data_fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import edu.rose_hulman.srproject.humanitarianapp.R;
import edu.rose_hulman.srproject.humanitarianapp.controllers.adapters.ListArrayAdapter;
import edu.rose_hulman.srproject.humanitarianapp.models.MessageThread;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MessageThreadFragment.ThreadFragmentListener} interface
 * to handle interaction events.

 */
public class MessageThreadFragment extends Fragment implements AbsListView.OnItemClickListener{

    private ThreadFragmentListener mListener;
    private MessageThread messageThread;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListArrayAdapter<MessageThread.Message> mAdapter;

    public MessageThreadFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        messageThread =mListener.getSelectedThread();

        mAdapter=new ListArrayAdapter<MessageThread.Message>(getActivity(),
                R.layout.list_message, messageThread.getItemList()){

            @Override
            public View customiseView(View v, final MessageThread.Message item) {
                RelativeLayout layout=(RelativeLayout)v.findViewById(R.id.message_layout);

                TextView nameField=(TextView)v.findViewById(R.id.nameField);
                TextView textField=(TextView)v.findViewById(R.id.textField);

                nameField.setText(item.getSender().getName());
                textField.setText(item.getItem());
                if(Long.parseLong(mListener.getUserID())==item.getSender().getID()){
                    layout.setBackgroundColor(getResources().getColor(R.color.SenderBlue));

                    //nameField.setBackgroundColor(getResources().getColor(R.color.accent_material_dark));
                    nameField.setTextColor(getResources().getColor(R.color.ColorPrimaryDark));
                    //textField.setBackgroundColor(getResources().getColor(R.color.accent_material_dark));
                    textField.setTextColor(getResources().getColor(R.color.ColorPrimaryDark));
                }
                else{
                    layout.setBackgroundColor(getResources().getColor(R.color.RecipientGreen));

                    //nameField.setBackgroundColor(getResources().getColor(R.color.accent_material_dark));
                    nameField.setTextColor(getResources().getColor(R.color.ColorPrimaryDark));
                    //textField.setBackgroundColor(getResources().getColor(R.color.accent_material_dark));
                    textField.setTextColor(getResources().getColor(R.color.ColorPrimaryDark));
                }
//
//                            AbsListView listView = (AbsListView) v.findViewById(R.id.sublistView);
//                    ListArrayAdapter<MessageThread.SublistItem> adapter=
//                            new ListArrayAdapter<MessageThread.SublistItem>(getActivity(),
//                                    R.layout.list_subthread, item.getSublistItems()) {
//                        @Override
//                        public View customiseView(View v, final MessageThread.SublistItem object) {
//                            CheckBox box=(CheckBox)v.findViewById(R.id.checkBox);
//                            box.setText(object.getCheckBoxInfoString());
//                            box.setChecked(object.isDone());
//                            box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                                @Override
//                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                                    object.setDone(isChecked);
//                                }
//                            });
//                            return v;
//                        }
//                    };
//                    Log.wtf("count", adapter.getCount()+"");
//                    listView.setAdapter(adapter);
                return v;
            }
        };


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        TextView title=(TextView) view.findViewById(R.id.title);
        title.setText(messageThread.getTitle());
        // Set the adapter
        mListView = (AbsListView) view.findViewById(R.id.listView);
        mListView.setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        return view;
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (ThreadFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement ThreadFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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
    public interface ThreadFragmentListener {

        public MessageThread getSelectedThread();
        public String getUserID();

    }

}
