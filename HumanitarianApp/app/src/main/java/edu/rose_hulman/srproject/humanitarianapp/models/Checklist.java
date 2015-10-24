package edu.rose_hulman.srproject.humanitarianapp.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daveyle on 10/4/2015.
 */
public class Checklist {
    private long id;
    private String title;
    private List<ChecklistItem> itemList=new ArrayList<ChecklistItem>();
    public Checklist(){

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

    public static class ChecklistItem{
        private String item;
        private Person assigned;
        private boolean done;

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
        public String getCheckBoxInfoString(){
            if (getAssigned()==null){
                return getItem();
            }
            return getItem()+" ("+getAssigned().getName()+")";

        }
    }
}
