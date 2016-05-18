package edu.rose_hulman.srproject.humanitarianapp.models;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import edu.rose_hulman.srproject.humanitarianapp.localdata.ApplicationWideData;

/**
 * Created by daveyle on 10/4/2015.
 */
public class Checklist implements Selectable{
    private long id;
    private String title;
    private long parentID;
    private boolean isHidden=false;
    private List<ChecklistItem> itemList=new ArrayList<ChecklistItem>();
    private int dirtyBits = 0;
    private String datetime;

    /**
     * All of the variables post refactoring. Also, the order is important and based off type NOT
     * what logically belongs where.

     // A flag for each field denoting if it needs to be updated on the server.
     // This will need to be initulized in the constructors.
     private boolean[] isDirty = new boolean[3];
     //A flag for if the object was synced from the server or created locally
     private boolean[] onServer = false;

     private long id; //0
     private String title; //1
     private List<ChecklistItem> itemList; //2
     */

    public Checklist(){

    }
    public Checklist(long id){
        this.id=id;
    }

    public Checklist(String title) {
        this.title = title;
    }

    public Checklist(String title, List<ChecklistItem> itemList) {
        this.title = title;
        this.itemList = itemList;
    }

    public Checklist(String title, long id) {
        this.title = title;
        this.id = id;
    }

    public Checklist(String title, List<ChecklistItem> itemList, long id) {
        this.title = title;
        this.itemList = itemList;
        this.id = id;
    }

    public String toJSON(){
        /*
        {
  "name": "Shepard's to do list",
  "parentID":"200000",
  "checklistItems": [

    ]

}
         */
        StringBuilder sb=new StringBuilder();
        sb.append("{\"name\": \""+getTitle()+"\",");
        sb.append("\"parentID\": \""+parentID+"\",");
        sb.append("\"checklistItems\": [");
        sb.append(getChecklistItemsJSON());
        sb.append("]}");
        return sb.toString();
    }
    private String getChecklistItemsJSON(){
        /*
        {"checklistItemID": "7010001",
      "isDone": false,
      "personID": "3000",
      "task": "Show people how you really feel",
      "sublistItems": [

        ]
      }
         */
        StringBuilder sb= new StringBuilder();
        for (int i=0; i<getItemList().size()-1; i++){
            ChecklistItem item=getItemList().get(i);
            sb.append("{\"checklistItemID\": \""+item.itemID+"\",");
            if (item.getAssigned()!=null) {

                sb.append("\"personID\": \"" + item.getAssigned().getID()+"\",");
            }
            sb.append("\"isDone\": "+item.isDone()+",");
            sb.append("\"task\": \""+item.getItem()+"\",");
            sb.append("\"sublistItems\": [");
            sb.append(getSublistItemsJSON(item));
            sb.append("]},");
        }
        if (getItemList().size()>0){
            ChecklistItem item=getItemList().get(getItemList().size()-1);
            sb.append("{\"checklistItemID\": \""+item.itemID+"\",");
            if (item.getAssigned()!=null) {

                sb.append("\"personID\": \"" + item.getAssigned().getID()+"\",");
            }
            sb.append("\"isDone\": "+item.isDone()+",");
            sb.append("\"task\": \""+item.getItem()+"\",");
            sb.append("\"sublistItems\": [");
            sb.append(getSublistItemsJSON(item));
            sb.append("]}");
        }
        return sb.toString();

    }
    private String getSublistItemsJSON(ChecklistItem item){
        /*
        {
          "sublistItemID": "70100011",
          "task": "Tell the Council where to shove it.",
          "isDone": false,
          "personID": "3000"
        }
         */
        StringBuilder sb= new StringBuilder();
        for (int i=0; i<item.getSublistItems().size()-1; i++){
            SublistItem sublistItem=item.getSublistItems().get(i);
            sb.append("{\"sublistItemID\": \""+sublistItem.itemID+"\",");
            if (sublistItem.getAssigned()!=null) {

                sb.append("\"personID\": \"" + sublistItem.getAssigned().getID()+"\",");
            }
            sb.append("\"isDone\": "+sublistItem.isDone()+",");
            sb.append("\"task\": \""+sublistItem.getItem()+"\"");
            sb.append("},");
        }
        if (item.getSublistItems().size()>0){
            SublistItem sublistItem=item.getSublistItems().get(item.getSublistItems().size()-1);
            sb.append("{\"sublistItemID\": \""+sublistItem.itemID+"\",");
            if (sublistItem.getAssigned()!=null) {

                sb.append("\"personID\": \"" + sublistItem.getAssigned().getID()+"\",");
            }
            sb.append("\"isDone\": "+sublistItem.isDone()+",");
            sb.append("\"task\": \""+sublistItem.getItem()+"\"");
            sb.append("}");
        }
        return sb.toString();
    }
    public static Checklist fromJSON(long lg, String json){

        ObjectMapper mapper=new ObjectMapper();
        TypeReference<HashMap<String, Object>> typeReference=
                new TypeReference<HashMap<String, Object>>() {
                };
        try {
            HashMap<String, Object> source = mapper.readValue(json, typeReference);
            Checklist l=parseJSON(lg, source);
            return l;





        }catch(Exception e){

        }
        return null;
    }
    public static Checklist parseJSON(long lg, HashMap<String, Object> source) {
        Checklist l = new Checklist(lg);
        l.setTitle((String) source.get("name"));
        if (source.get("dateArchived") == null)
            l.setHidden(false);
        else
            l.setHidden(true);
        l.setParentID(Long.parseLong((String) source.get("parentID")));

        ArrayList<HashMap<String, Object>> items = (ArrayList) source.get("checklistItems");
        if (items!=null) {
            for (HashMap item : items) {
                Checklist.ChecklistItem checklistItem = new Checklist.ChecklistItem((String) item.get("task"));
                ArrayList<HashMap<String, Object>> subitems = (ArrayList) item.get("sublistItems");

                for (HashMap subitem : subitems) {
                    Checklist.SublistItem sublistItem = new Checklist.SublistItem((String) subitem.get("task"));
                    //TODO:
                    //sublistItem.setAssigned();
                    if (subitem.containsKey("sublistItemID") && !((String) subitem.get("sublistItemID")).equals("null")) {
                        sublistItem.setItemID(Long.parseLong((String) subitem.get("sublistItemID")));
                    }
                    if (subitem.containsKey("isDone")) {
                        sublistItem.setDone((boolean) subitem.get("isDone"));
                    }
                    checklistItem.addNewSublistItem(sublistItem);

                }
                if (item.containsKey("checklistItemID") && !((String) item.get("checklistItemID")).equals("null")) {
                    checklistItem.setItemID(Long.parseLong((String) item.get("checklistItemID")));
                }
                if (item.containsKey("isDone")) {
                    checklistItem.setDone((boolean) item.get("isDone"));
                }
                l.addItem(checklistItem);
            }
        }
        return l;
    }
    public List<ChecklistItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<ChecklistItem> itemList) {
        this.itemList = itemList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addItem(ChecklistItem item){
        this.itemList.add(item);
    }

    public long getParentID() {
        return parentID;
    }

    public void setParentID(long parentID) {
        this.parentID = parentID;
    }

    public long getID() {
        return id;
    }

    @Override
    public String getDateTimeModified() {
        if(datetime == null || datetime.equals("")){
            return ApplicationWideData.getCurrentTime();
        }
        return datetime;

    }

    @Override
    public void setDateTimeModified(String dateTime) {
        this.datetime=dateTime;
    }

    public void setID(long id) {
        this.id = id;
    }
    public void setItemIDs(){
        long base=this.id*100;
        for (int i=0; i<itemList.size(); i++){
            long base2=(base+i)*100;
            ChecklistItem item=itemList.get(i);
            item.setItemID(base+i);
            for (int j=0; j<item.getSublistItems().size(); j++){
                SublistItem sublistItem=item.getSublistItems().get(j);
                sublistItem.setItemID(base2+j);
            }

        }
    }

    @Override
     public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean hidden) {
        this.isHidden = hidden;
    }

    public int getDirtyBits() {
        return dirtyBits;
    }

    public void setDirtyBits(int dirtyBits) {
        this.dirtyBits = dirtyBits;
    }

    public static class ChecklistItem{
        private long itemID;
        private String item;
        private Person assigned;
        private boolean done;
        private ArrayList<SublistItem> sublistItems=new ArrayList<>();

        public ChecklistItem(){

        }
        public ChecklistItem(long itemID){
            this.itemID=itemID;
        }

        public ChecklistItem(String item) {
            this(item, false, null);
        }

        public ChecklistItem(String item, boolean done) {
            this(item, done, null);
        }

        public ChecklistItem(String item, Person assigned) {
            this(item, false, assigned);
        }

        public ChecklistItem(String item, boolean done, Person assigned) {
            this.item = item;
            this.done = done;
            this.assigned = assigned;
        }

        public Person getAssigned() {
            return assigned;
        }

        public void setAssigned(Person assigned) {
            this.assigned = assigned;
        }

        public boolean isDone() {
            return done;
        }

        public void setDone(boolean done) {
            this.done = done;
        }

        public String getItem() {
            return item;
        }

        public void setItem(String item) {
            this.item = item;
        }
        public void addNewSublistItem(SublistItem sublistItem){
            this.sublistItems.add(sublistItem);
        }
        public ArrayList<SublistItem> getSublistItems(){
            return sublistItems;
        }
        public String getCheckBoxInfoString(){
            if (getAssigned()==null){
                return getItem();
            }
            return getItem()+" ("+getAssigned().getName()+")";

        }

        public long getItemID() {
            return itemID;
        }

        public void setItemID(long itemID) {
            this.itemID = itemID;
        }
    }
    public static class SublistItem{
        private String item;
        private Person assigned;



        private long itemID;
        private boolean done;

        public SublistItem(){}

        public SublistItem(long id){
            this.itemID=id;
        }
        public SublistItem(String item) {
            this(item, false, null);
        }

        public SublistItem(String item, boolean done) {
            this(item, done, null);
        }

        public SublistItem(String item, Person assigned) {
            this(item, false, assigned);
        }

        public SublistItem(String item, boolean done, Person assigned) {
            this.item = item;
            this.done = done;
            this.assigned = assigned;
        }

        public Person getAssigned() {
            return assigned;
        }

        public void setAssigned(Person assigned) {
            this.assigned = assigned;
        }

        public boolean isDone() {
            return done;
        }

        public void setDone(boolean done) {
            this.done = done;
        }

        public String getItem() {
            return item;
        }

        public void setItem(String item) {
            this.item = item;
        }
        public String getCheckBoxInfoString(){
            if (getAssigned()==null){
                return getItem();
            }
            return getItem()+" ("+getAssigned().getName()+")";

        }
        public long getItemID() {
            return itemID;
        }

        public void setItemID(long itemID) {
            this.itemID = itemID;
        }
    }
}
