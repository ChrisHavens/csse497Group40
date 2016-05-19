package edu.rose_hulman.srproject.humanitarianapp.controllers.data_fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import edu.rose_hulman.srproject.humanitarianapp.R;
import edu.rose_hulman.srproject.humanitarianapp.controllers.adapters.ListArrayAdapter;
import edu.rose_hulman.srproject.humanitarianapp.models.MessageThread;
import edu.rose_hulman.srproject.humanitarianapp.nonlocaldata.NonLocalDataService;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MessageThreadFragment.ThreadFragmentListener} interface
 * to handle interaction events.

 */
public class MessageThreadFragment extends Fragment implements AbsListView.OnItemClickListener{

    private ThreadFragmentListener mListener;
    private MessageThread messageThread;
    private ArrayList<MessageListMessage> messages=new ArrayList<>();
    private ArrayList<MessageListMessage> messages2=new ArrayList<>();
    private EditText mEditText;
    private Button mSendButton;
    private Comparator<MessageListMessage> comparator=new Comparator<MessageListMessage>() {
        @Override
        public int compare(MessageListMessage lhs, MessageListMessage rhs) {
            return rhs.getMessage().getDateTimeModified().compareTo(lhs.getMessage().getDateTimeModified());
        }
    };

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListArrayAdapter<MessageListMessage> mAdapter;

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

        mAdapter=new ListArrayAdapter<MessageListMessage>(getActivity(),
                R.layout.list_message, messages){

            @Override
            public View customiseView(View v, final MessageListMessage item) {
                RelativeLayout layout=(RelativeLayout)v.findViewById(R.id.message_layout);

                TextView nameField=(TextView)v.findViewById(R.id.nameField);
                TextView textField=(TextView)v.findViewById(R.id.textField);
                TextView timeField=(TextView)v.findViewById(R.id.timeField);

                nameField.setText(item.getPerson());
                textField.setText(item.message.getItem());
                timeField.setText(item.message.getDateTimeModified());
                if(mListener.getUserID().equals(item.personID)){
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
                return v;
            }
        };


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        TextView title=(TextView) view.findViewById(R.id.title);
        title.setText(messageThread.getTitle());
        // Set the adapter
        mListView = (AbsListView) view.findViewById(R.id.listView);
        mListView.setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        mEditText=(EditText)view.findViewById(R.id.editText);

        mSendButton=(Button)view.findViewById(R.id.sendButton);

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message=mEditText.getText().toString();
                if (!message.isEmpty() && !(message.trim().isEmpty())){
                    mEditText.setText("");
                    mListener.sendMessage(message);
                }


            }
        });




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
        if (this.mAdapter != null) {
            this.mAdapter.notifyDataSetChanged();
        }
        final NonLocalDataService service = new NonLocalDataService();
        service.getMessagesList(mListener.getSelectedThread().getID() + "", new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                ObjectMapper mapper = new ObjectMapper();
                TypeReference<HashMap<String, Object>> typeReference =
                        new TypeReference<HashMap<String, Object>>() {
                        };
                try {
                    HashMap<String, Object> o = mapper.readValue(response.getBody().in(), typeReference);
                    HashMap<String, Object> m1=(HashMap) o.get("hits");
                    int count=(Integer) m1.get("total");
                    Log.wtf("Count: ", count+"");
                    messageThread.setCount(count);
                    ArrayList<HashMap<String, Object>> list = (ArrayList) ((HashMap) o.get("hits")).get("hits");
                    for (int i = 0; i< list.size(); i++) {
                        HashMap<String, Object> map = list.get(i);
                        HashMap<String, Object> source = (HashMap) map.get("_source");
                        if (source != null) {
                            MessageThread.Message m = new MessageThread.Message((String) source.get("text"),
                                    (String) source.get("personID"), (String) source.get("sentDate"));
                            m.setID(Long.parseLong((String) map.get("_id")));
                            messageThread.addItem(m);
                            final MessageListMessage m2=new MessageListMessage(m, "", (String)source.get("personID"));
                            messages2.add(m2);
                            Collections.sort(messages, comparator);
                            Collections.sort(messages2, comparator);

                            if (mAdapter != null) {
                                mAdapter.notifyDataSetChanged();

                            }

                                Callback<Response> callback= new Callback<Response>() {
                                    @Override
                                    public void success(Response response, Response response2) {
                                        ObjectMapper mapper = new ObjectMapper();
                                        TypeReference<HashMap<String, Object>> typeReference =
                                                new TypeReference<HashMap<String, Object>>() {
                                                };
                                        try {
                                            HashMap<String, Object> map = mapper.readValue(response.getBody().in(), typeReference);
                                            HashMap<String, Object> source = (HashMap) map.get("_source");
                                            if (source != null) {
                                                String name = (String) source.get("name");
                                                m2.setPerson(name);
                                                messages.add(m2);
                                                Collections.sort(messages, comparator);
                                            }
                                            else{
                                                m2.setPerson("Shadow Broker");
                                                messages.add(m2);
                                                Collections.sort(messages, comparator);
                                            }
                                            mAdapter.notifyDataSetChanged();

                                        } catch (Exception e) {

                                        }
                                    }

                                    @Override
                                    public void failure(RetrofitError error) {
                                        Log.wtf("s40", error.getMessage());
                                        Log.wtf("s40", error.getUrl());
                                        m2.setPerson("Shadow Broker");
                                        messages.add(m2);
                                        Collections.sort(messages, comparator);
                                    }
                                };
                                service.service.getPerson(m2.personID, callback);


                        }
                    }
//                    Toast.makeText(getActivity(), "Size: "+messages2.size(), Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.wtf("RetrofitError", error.getMessage());
                Log.wtf("RetrofitError", error.getUrl());
            }
        });
//        Log.wtf("s40", messages2.size() + "");
//        Log.wtf("s40", messageThread.getItemList().size()+"");



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

        MessageThread getSelectedThread();
        String getUserID();
        void sendMessage(String message);
        //public void addNewMessage(MessageThread.Message message);

    }
    public class MessageListMessage {
        MessageThread.Message message;
        String person;
        String personID;

        public MessageListMessage(MessageThread.Message message, String person, String personID) {
            this.message = message;
            this.person = person;
            this.personID=personID;
        }

        public MessageThread.Message getMessage() {
            return message;
        }

        public void setMessage(MessageThread.Message message) {
            this.message = message;
        }

        public String getPerson() {
            return person;
        }

        public void setPerson(String person) {
            this.person = person;
        }
    }


}
