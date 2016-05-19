package edu.rose_hulman.srproject.humanitarianapp.localdata;

import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import edu.rose_hulman.srproject.humanitarianapp.controllers.MainActivity;
import edu.rose_hulman.srproject.humanitarianapp.controllers.list_fragments.AbstractListFragment;
import edu.rose_hulman.srproject.humanitarianapp.models.Conflict;
import edu.rose_hulman.srproject.humanitarianapp.models.Group;
import edu.rose_hulman.srproject.humanitarianapp.models.Project;
import edu.rose_hulman.srproject.humanitarianapp.models.Selectable;
import edu.rose_hulman.srproject.humanitarianapp.nonlocaldata.NonLocalDataService;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Chris on 5/18/2016.
 */
public class VeryNaughtyBoy {
    NonLocalDataService service=new NonLocalDataService();
    private List<Selectable> selectables=new ArrayList<>();
    private List<Selectable> failedSelectables=new ArrayList<>();
    private HashMap<Selectable, List<Conflict>> conflicts=new HashMap<>();
    private Selectable lastSelectable;
    private MainActivity activity;
   public VeryNaughtyBoy(List<Selectable> selectableHashMap, MainActivity activity){
       this.selectables=selectableHashMap;
       this.activity=activity;
   }
    public void doUpdates(){
        if (!selectables.isEmpty()) {
            LinkedList<Selectable> linkedList = new LinkedList<>();
            linkedList.addAll(selectables);
            lastSelectable = linkedList.remove();
            makeRightCall(lastSelectable, new BlessedCheesemakers(linkedList));
        }

    }
    public static List<Conflict> getConflicts(Response response){

        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String, Object>> typeReference =
                new TypeReference<HashMap<String, Object>>() {
                };
        List<Conflict> conflicts=new ArrayList<>();
        try {
            HashMap<String, Object> o = mapper.readValue(response.getBody().in(), typeReference);
            for (String key: o.keySet()){
                HashMap<String, Object> keyMap=(HashMap)o.get(key);
                Conflict c = new Conflict(key, (String)keyMap.get("server"), (String)keyMap.get("local"));
                conflicts.add(c);
            }
        }catch(Exception e){

        }
        return conflicts;

    }
    public void makeRightCall(Selectable s, Callback<Response> callback){
        if (s.getType().equals("Project")){
            service.updateProject((Project)s, "{\"doc\":" + ((Project) s).toJSON() + "}", PreferencesManager.getID(), callback);
        }
        else if (s.getType().equals("Group")){
            service.updateGroup((Group)s, "{\"doc\":" + ((Group) s).toJSON() + "}", PreferencesManager.getID(), callback);
        }

    }
    public class BlessedCheesemakers implements Callback<Response>{
       final private Queue<Selectable> queue;
        public BlessedCheesemakers(Queue<Selectable> list){
            queue=list;
        }

        @Override
        public void success(Response response, Response response2) {
            if(!queue.isEmpty()){
                LocalDataSaver.clearUpdatedSelectable(lastSelectable);
                lastSelectable=queue.remove();
                makeRightCall(lastSelectable, new BlessedCheesemakers(queue));
            }
            else{
                LocalDataSaver.clearUpdatedSelectables();
                activity.showConflictResolution(conflicts);
            }


        }

        @Override
        public void failure(RetrofitError error) {
            if (error.getResponse().getStatus() == 418) {
//                    Log.wtf("Conflict FOUND:", error.getResponse().getBody().toString());
                List<Conflict> currConflicts = getConflicts(error.getResponse());
                conflicts.put(lastSelectable, currConflicts);


            }
            else {
                failedSelectables.add(lastSelectable);
            }
            if (!queue.isEmpty()){
                lastSelectable=queue.remove();
                makeRightCall(lastSelectable, new BlessedCheesemakers(queue));
            }
            else{
                LocalDataSaver.clearUpdatedSelectables();
                activity.showConflictResolution(conflicts);
            }


        }
    }
}
