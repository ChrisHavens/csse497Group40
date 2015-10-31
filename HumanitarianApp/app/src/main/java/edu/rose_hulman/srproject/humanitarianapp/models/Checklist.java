package edu.rose_hulman.srproject.humanitarianapp.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by daveyle on 10/4/2015.
 */
public class Checklist {
    private long id;
    private String title;
    private long parentID;
    private List<ChecklistItem> itemList=new ArrayList<ChecklistItem>();

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

    /*
     * New constructors
     //Creates a new checklist, but does not give it an ID, or add it to the list of knowns
     public Checklist() {
     }

     //Note: if you are passing in the ID, but it is not on the server, you have to manually set that.
     public Checklist(long id) {
        this.itemsList = new ArrayList<ChecklistItem>();
        this.id = id;
        this.title = "";
        Arrays.fill(this.isDirty, false);
        this.onServer = true;
        ApplicationWideData.addExistingChecklist(this);
     }

     public Checklist(long id, String title) {
        this.itemsList = new ArrayList<ChecklistItem>();
        this.id = id;
        this.title = title;
        Arrays.fill(this.isDirty, false);
        this.onServer = true;
        ApplicationWideData.addExistingChecklist(this);
     }

     public Checklist(long id, String title, List<ChecklistItem> itemsList) {
        this.itemsList = itemsList;
        this.id = id;
        this.title = title;
        Arrays.fill(this.isDirty, false);
        this.onServer = true;
        ApplicationWideData.addExistingChecklist(this);
     }

     public Checklist(String title) {
        this.setupAsNew();
        this.title = title;
     }

     public Checklist(String title, List<ChecklistItem> itemsList) {
        this.setupAsNew();
        this.itemsList = itemsList;
        this.title = title;

     }

     public setupAsNew() {
        this.id = SerilizationConstants.generateID(SerilizationConstants.CHECKLIST_NUM);
        this.title = "";
        this.itemsList = new ArrayList<ChecklistItem>();
        Arrays.fill(this.isDirty, true);
        this.onServer = false;
        ApplicationWideData.addNewChecklist(this);
     }

     public void fullClean() {
        Arrays.fill(this.isDirty, false);
        this.onServer = true;
     }

    public void setItemList(List<ChecklistItem> itemList) {
        this.itemList = itemList;
        this.isDirty[2] = true;
    }

    public void setItemListClean(List<ChecklistItem> itemList) {
        this.itemList = itemList;
        this.isDirty[2] = false;
    }

    public void setItemListMaintain(List<ChecklistItem> itemList) {
        this.itemList = itemList;
    }

    public void setTitle(String title) {
        this.title = title;
        this.isDirty[1] = true;
    }

    public void setTitleClean(String title) {
        this.title = title;
        this.isDirty[1] = false;
    }

    public void setTitleMaintain(String title) {
        this.title = title;
    }

     */
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
