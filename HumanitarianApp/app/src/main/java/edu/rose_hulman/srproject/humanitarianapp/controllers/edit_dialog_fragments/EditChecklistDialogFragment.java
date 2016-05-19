package edu.rose_hulman.srproject.humanitarianapp.controllers.edit_dialog_fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.rose_hulman.srproject.humanitarianapp.R;
import edu.rose_hulman.srproject.humanitarianapp.models.Checklist;

/**
 * Created by daveyle on 10/30/2015.
 */
public class EditChecklistDialogFragment extends DialogFragment {


    private EditChecklistListener mListener;
    private EditText nameField;
    private Checklist checklist;
    View mainView;
    //List<Checklist.ChecklistItem> checklistItemList=new ArrayList<>();
    HashMap<Checklist.ChecklistItem, View> map=new HashMap<>();
    HashMap<Checklist.SublistItem, View> subMap=new HashMap<>();



    public EditChecklistDialogFragment() {
        // Required empty public constructor
    }


    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        if (getArguments()!=null){
//            long checklistID=getArguments().getLong("checklistID");
//            NonLocalDataService service =new NonLocalDataService();
//            service.get("checklist", checklistID + "", new Callback<Response>() {
//                @Override
//                public void success(Response response, Response response2) {
//                    ObjectMapper mapper=new ObjectMapper();
//                    TypeReference<HashMap<String, Object>> typeReference=
//                            new TypeReference<HashMap<String, Object>>() {
//                            };
//                    try {
//                        HashMap<String, Object> map=mapper.readValue(response.getBody().in(), typeReference);
//                        HashMap<String, Object> source=(HashMap)map.get("_source");
//                        for (String s: source.keySet()){
//                            Log.e("Result", s);
//                        }
//                        Checklist l=new Checklist(Integer.parseInt(((String)map.get("_id"))));
//                        l.setTitle((String)source.get("name"));
//                        ArrayList<HashMap<String, Object>> items=(ArrayList)source.get("checklistItems");
//                        for (HashMap item: items) {
//                            Checklist.ChecklistItem checklistItem = new Checklist.ChecklistItem((String) item.get("task"));
//                            ArrayList<HashMap<String, Object>> subitems = (ArrayList) item.get("sublistItems");
//                            Log.wtf("s40", subitems.toString());
//                            for (HashMap subitem : subitems) {
//                                Checklist.SublistItem sublistItem = new Checklist.SublistItem((String) subitem.get("task"));
//                                //TODO:
//                                //sublistItem.setAssigned();
//                                sublistItem.setDone((boolean) subitem.get("isDone"));
//                                checklistItem.addNewSublistItem(sublistItem);
//                                Log.wtf("hi!", "hi!");
//                            }
//                            l.addItem(checklistItem);
//                        }
//                        checklist=l;
//                        mainView=onCreateView(getActivity().getLayoutInflater());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void failure(RetrofitError error) {
//                    Log.wtf("Retrofit Error", error.getMessage());
//                }
//            });
//        }
        checklist=mListener.getSelectedChecklist();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout

        mainView=onCreateView(inflater);
        builder.setView(mainView)
                // Add action buttons
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        checklist=new Checklist();
                        String name = nameField.getText().toString();
                        checklist.setTitle(name);
                        checklist.setParentID(checklist.getParentID());
                        List<Checklist.ChecklistItem> items=new ArrayList<Checklist.ChecklistItem>();
                        for (Checklist.ChecklistItem item : map.keySet()) {
                            View view = map.get(item);
                            EditText taskField = (EditText) view.findViewById(R.id.taskField);
                            item.setItem(taskField.getText().toString());
                            for (Checklist.SublistItem sublistItem : item.getSublistItems()) {
                                View v = subMap.get(sublistItem);
                                EditText subTaskField=(EditText) v.findViewById(R.id.taskField);
                                sublistItem.setItem(subTaskField.getText().toString());
                            }
                            items.add(item);
                        }
                        checklist.setItemList(items);

                        mListener.editChecklist(checklist);
                        EditChecklistDialogFragment.this.getDialog().dismiss();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EditChecklistDialogFragment.this.getDialog().dismiss();
                    }
                });
        return builder.create();

    }


    public View onCreateView(LayoutInflater inflater) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_add_checklist_dialog, null);
        nameField=(EditText) view.findViewById(R.id.nameField);
        if (checklist!=null) {
            nameField.setText(checklist.getTitle());
        }
        final LinearLayout layout= (LinearLayout) view.findViewById(R.id.checklistItemsLayout);
        populateChecklistItems(layout);
        Button button= (Button) view.findViewById(R.id.addChecklistItem);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.addView(showNewChecklistItem());
            }
        });

        return view;
    }
    private void populateChecklistItems(LinearLayout layout){
        if (checklist==null){return;}
        for (final Checklist.ChecklistItem item: checklist.getItemList()){
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View v= inflater.inflate(R.layout.layout_add_checklist_item, null);

            EditText taskField=(EditText)v.findViewById(R.id.taskField);
            taskField.setText(item.getItem());
            final LinearLayout sublayout=(LinearLayout) v.findViewById(R.id.sublistItemLayout);
            populateSublistItems(item, sublayout);
            Button b= (Button) v.findViewById(R.id.addSubListItem);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sublayout.addView(showNewSublistItem(item));
                }
            });
            layout.addView(v);


            map.put(item, v);
            //return v;
        }

    }
    private void populateSublistItems(Checklist.ChecklistItem item, LinearLayout layout){
        LayoutInflater inflater = getActivity().getLayoutInflater();
        for (Checklist.SublistItem sublistItem : item.getSublistItems()) {
            View v = inflater.inflate(R.layout.layout_add_sublist_item, null);
            EditText taskField = (EditText) v.findViewById(R.id.taskField);
            taskField.setText(sublistItem.getItem());
            layout.addView(v);
            //item.addNewSublistItem(sublistItem);

            subMap.put(sublistItem, v);
        }
        //return v;
    }
    private View showNewChecklistItem(){
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v= inflater.inflate(R.layout.layout_add_checklist_item, null);
        final Checklist.ChecklistItem item=new Checklist.ChecklistItem();
        EditText taskField=(EditText)v.findViewById(R.id.taskField);
        final LinearLayout layout=(LinearLayout) v.findViewById(R.id.sublistItemLayout);
        Button b= (Button) v.findViewById(R.id.addSubListItem);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.addView(showNewSublistItem(item));
            }
        });
        map.put(item, v);
        checklist.addItem(item);
        return v;


    }
    private View showNewSublistItem(Checklist.ChecklistItem item){
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v= inflater.inflate(R.layout.layout_add_sublist_item, null);
        final Checklist.SublistItem sublistItem=new Checklist.SublistItem();
        item.addNewSublistItem(sublistItem);
        //EditText taskField=(EditText)v.findViewById(R.id.taskField);
        subMap.put(sublistItem, v);
        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (EditChecklistListener) activity;
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
    public interface EditChecklistListener {
        // TODO: Update argument type and name
        void editChecklist(Checklist checklist);
        Checklist getSelectedChecklist();
    }
}
