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
import edu.rose_hulman.srproject.humanitarianapp.localdata.ApplicationWideData;
import edu.rose_hulman.srproject.humanitarianapp.models.Checklist;
import edu.rose_hulman.srproject.humanitarianapp.models.MessageThread;
import edu.rose_hulman.srproject.humanitarianapp.models.Group;
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
public class MessageThreadsListFragment extends AbstractListFragment<MessageThread> {
    protected ThreadsListListener mListener;
    ListArrayAdapter<MessageThread> adapter;
    HashMap<Long, MessageThread> threads=new HashMap<>();
    private boolean showHidden=false;
    public MessageThreadsListFragment(){
    }


    @Override
    public ListArrayAdapter<MessageThread> getAdapter() {
        adapter=new ListArrayAdapter<MessageThread>(getActivity(),
                android.R.layout.simple_list_item_1, getItems()){

            @Override
            public View customiseView(View v, MessageThread thread) {
                TextView line1=(TextView) v.findViewById(android.R.id.text1);
                line1.setText(thread.getTitle());
                return v;
            }
        };
        return adapter;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (ThreadsListListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement ListSelectable<T>");
        }
        if (mListener==null){
            throw new NullPointerException("Parent fragment is null");
        }
        Group g= mListener.getSelectedGroup();
        long gId=g.getID();
        List<MessageThread> allThreads=ApplicationWideData.getAllMessageThreads();
        for (MessageThread thread: allThreads){
            if (thread.getParentID()==gId){
                threads.put(thread.getID(), thread);
            }
        }
        if (!ApplicationWideData.manualSnyc) {
            NonLocalDataService service = new NonLocalDataService();
            showHidden = mListener.getShowHidden();
            Log.wtf("s40", "Here");
            service.service.getThreadList(showHidden, mListener.getSelectedGroup().getID() + "", new ThreadListCallback());
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public String getTitle() {
        return getResources().getString(R.string.threads);
    }

    @Override
    public void onItemSelected(MessageThread thread) {

        mListener.onItemSelected(thread);
    }
    public void checkForArgs(){

    }


    public List<MessageThread> getItems(){
        List<MessageThread> l=new ArrayList<>();
        l.addAll(threads.values());
        return l;
    }
    public class ThreadListCallback implements Callback<Response> {



        @Override
        public void success(Response response, Response response2) {
            //Log.wtf("s40", "marge");
            ObjectMapper mapper=new ObjectMapper();
            TypeReference<HashMap<String, Object>> typeReference=
                    new TypeReference<HashMap<String, Object>>() {
                    };
            TypeReference<String> typeReference1=new TypeReference<String>() {
            };
            try {
                //Log.wtf("s40", "homer");
                HashMap<String, Object> o=mapper.readValue(response.getBody().in(), typeReference);
                //Log.wtf("s40", "bart");
                HashMap<String, Object> o2=(HashMap)o.get("hits");
                //Log.wtf("s40", "lisa");

                ArrayList<HashMap<String, Object>> list=(ArrayList)o2.get("hits");
                //Log.wtf("s40", "maggie"+o2.keySet().toString());
                NonLocalDataService service=new NonLocalDataService();

                for (HashMap<String, Object> map: list){
                    //Log.wtf("s40", "object");
                    HashMap<String, Object> source=(HashMap)map.get("_source");
                    MessageThread l= MessageThread.parseJSON(Long.parseLong((String)map.get("_id")),source);
//                    MessageThread l=new MessageThread(Integer.parseInt(((String)map.get("_id"))));
//                    l.setTitle((String)source.get("name"));
//                    if(source.get("dateArchived") == null)
//                        l.setHidden(false);
//                    else
//                        l.setHidden(true);
//                    l.setParentID(Long.parseLong((String)source.get("parentID")));

//                    ArrayList<HashMap<String, Object>> items=(ArrayList)source.get("messageItems");
//                    for (HashMap item: items) {
//                        MessageThread.Message threadItem = new MessageThread.Message((String) item.get("text"));
//                        Person p;
//                        p = ApplicationWideData.getPersonByID(Long.parseLong((String) item.get("personID")));
//                        if(p==null){
//                            p=new Person("Shadow Broker", null);
//                        }
//
//                        threadItem.setSender(p);
//
//                        l.addItem(threadItem);
//                    }
                    threads.put(l.getID(), l);
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
            Log.wtf("RetrofitError", error.getUrl());
        }
    }
    public interface ThreadsListListener extends Interfaces.UserIDGetter{
        void onItemSelected(MessageThread t);
        boolean getShowHidden();
        Group getSelectedGroup();
    }



}