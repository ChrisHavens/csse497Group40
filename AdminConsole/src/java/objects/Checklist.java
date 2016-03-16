/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author daveyle
 */
public class Checklist {
     private long id;
    private String title;
    private long parentID;
    private boolean isHidden=false;
    private List<ChecklistItem> itemList=new ArrayList<ChecklistItem>();
    public Checklist(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getParentID() {
        return parentID;
    }

    public void setParentID(long parentID) {
        this.parentID = parentID;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean isHidden) {
        this.isHidden = isHidden;
    }

    public List<ChecklistItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<ChecklistItem> itemList) {
        this.itemList = itemList;
    }
    public static Checklist parseChecklist(HashMap<String, Object> map){
        HashMap<String, Object> source=(HashMap)map.get("_source");
                  
                    final Checklist c =new Checklist();
                    c.setId(Integer.parseInt(((String)map.get("_id"))));
                    c.setTitle((String)source.get("name"));
                    
                    if(source.get("dateArchived") == null)
                        c.setHidden(false);
                    else
                        c.setHidden(true);
                    if (source.get("checklistItems")!=null){
                        ArrayList<ChecklistItem> itemlist=new ArrayList<ChecklistItem>();
                        ArrayList<HashMap<String, Object>> items=(ArrayList<HashMap<String, Object>>) source.get("checklistItems");
                        for (HashMap<String, Object> itemMap: items){
                            itemlist.add(ChecklistItem.parseChecklistItem(itemMap));
                        }
                        c.setItemList(itemlist);
                    }
                    return c;        
                     }
    
    
    
    
    public static class ChecklistItem{
        private long Id;
        private String item;
        private String assigned;
        private boolean done;
        private ArrayList<SublistItem> sublistItems=new ArrayList<SublistItem>();
        public ChecklistItem(){}

        public long getId() {
            return Id;
        }

        public void setId(long itemID) {
            this.Id = itemID;
        }

        public String getItem() {
            return item;
        }

        public void setItem(String item) {
            this.item = item;
        }

        public String getAssigned() {
            return assigned;
        }

        public void setAssigned(String assigned) {
            this.assigned = assigned;
        }

        public boolean isDone() {
            return done;
        }

        public void setDone(boolean done) {
            this.done = done;
        }

        public ArrayList<SublistItem> getSublistItems() {
            return sublistItems;
        }

        public void setSublistItems(ArrayList<SublistItem> sublistItems) {
            this.sublistItems = sublistItems;
        }
        public static ChecklistItem parseChecklistItem(HashMap<String, Object> map){
            ChecklistItem item=new ChecklistItem();
            item.setId(Long.parseLong((String) map.get("checklistItemID")));
            item.setDone((Boolean) map.get("isDone"));
            item.setAssigned((String)map.get("personID"));
            item.setItem((String)map.get("task"));
            if (map.get("sublistItems")!=null){
                ArrayList<SublistItem> sublistItems= new ArrayList<SublistItem>();
                ArrayList<HashMap<String, Object>> subitems=(ArrayList<HashMap<String, Object>>)map.get("sublistItems");
                for (HashMap<String, Object> subMap: subitems){
                    sublistItems.add(SublistItem.parseSublistItem(subMap));
                }
                item.setSublistItems(sublistItems);
            }
            return item;
            
        }
        
    }
    
     public static class SublistItem{
        private String item;
        private String assigned;



        private long Id;
        private boolean done;

        public SublistItem(){}

        public String getItem() {
            return item;
        }

        public void setItem(String item) {
            this.item = item;
        }

        public String getAssigned() {
            return assigned;
        }

        public void setAssigned(String assigned) {
            this.assigned = assigned;
        }

        public long getId() {
            return Id;
        }

        public void setId(long itemID) {
            this.Id = itemID;
        }

        public boolean isDone() {
            return done;
        }

        public void setDone(boolean done) {
            this.done = done;
        }
        public static SublistItem parseSublistItem(HashMap<String, Object> map){
            SublistItem item=new SublistItem();
            item.setId(Long.parseLong((String)map.get("sublistItemID")));
            item.setItem((String) map.get("task"));
            item.setDone((Boolean) map.get("isDone"));
            item.setAssigned((String)map.get("personID"));
            return item;
        }
        
        
     }
}
