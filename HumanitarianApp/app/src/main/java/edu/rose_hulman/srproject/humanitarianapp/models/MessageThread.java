package edu.rose_hulman.srproject.humanitarianapp.models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import edu.rose_hulman.srproject.humanitarianapp.localdata.ApplicationWideData;

/**
 * Created by daveyle on 2/1/2016.
 */
public class MessageThread implements Selectable{
    private long id;
    private String title;
    private long parentID;
    private String newestTime;
    private boolean isHidden=false;
    private List<Message> itemList=new ArrayList<Message>();
    private int dirtyBits = 0;

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
     private List<Message> itemList; //2
     */

    public MessageThread(){

    }
    public MessageThread(long id){
        this.id=id;
    }

    public MessageThread(String title) {
        this.title = title;
    }

    public MessageThread(String title, List<Message> itemList) {
        this.title = title;
        this.itemList = itemList;
    }

    public MessageThread(String title, long id) {
        this.title = title;
        this.id = id;
    }

    public MessageThread(String title, List<Message> itemList, long id) {
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
        sb.append("\"messageItems\": [");
        sb.append(getMessagesJSON());
        sb.append("]}");
        return sb.toString();
    }
    private String getMessagesJSON(){
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
            Message item=getItemList().get(i);
            sb.append("{\"messageID\": \""+item.itemID+"\",");
            if (item.getSender()!=null) {

                sb.append("\"personID\": \"" + item.getSender().getID()+"\",");
            }
            sb.append("\"sentDate\": \""+item.getTime()+"\",");

            sb.append("\"text\": \""+item.getItem().replaceAll("\\\\n", "\\\\n")+"\"");
            sb.append("},");
        }
        if (getItemList().size()>0){
            Message item=getItemList().get(getItemList().size()-1);
            sb.append("{\"messageID\": \""+item.itemID+"\",");
            if (item.getSender()!=null) {

                sb.append("\"personID\": \"" + item.getSender().getID()+"\",");
            }
            sb.append("\"sentDate\": \""+item.getTime()+"\",");
            sb.append("\"text\": \""+item.getItem()+"\"");

            sb.append("}");
        }
        return sb.toString();

    }
    @Override
    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean hidden) {
        this.isHidden = hidden;
    }

    public List<Message> getItemList() {
        return itemList;
    }

    public void setItemList(List<Message> itemList) {
        this.itemList = itemList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addItem(Message item){
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
        long base=this.id*1000;
        for (int i=0; i<itemList.size(); i++){
            long base2=(base+i)*1000;
            Message item=itemList.get(i);
            item.setItemID(base+i);

        }
    }

    public String getNewestTime() {
        return newestTime;
    }

    public void setNewestTime(String newestTime) {
        this.newestTime = newestTime;
    }

    public int getDirtyBits() {
        return dirtyBits;
    }

    public void setDirtyBits(int dirtyBits) {
        this.dirtyBits = dirtyBits;
    }

    public static class Message{
        private long itemID;
        private String item;
        private Person sender;
        private String time;


        public Message(){

        }
        public Message(long itemID){
            this.itemID=itemID;
        }

        public Message(String item) {
            this(item, new Person("Shadow Broker", null));
//            this(item, null);
        }

        public Message(String item, Person sender) {
            this.item = item;
            this.sender = sender;
            this.time=MessageThread.getCurrTime();

        }
        public Message(String item, String senderID){
            this(item, MessageThread.getPersonNameFromID(senderID));

        }

        public Person getSender() {
            return sender;
        }

        public void setSender(Person sender) {
            this.sender = sender;
        }

        public String getItem() {
            return item;
        }

        public void setItem(String item) {
            this.item = item;
        }

        public String getMessageInfoString(){
            if (getSender()==null){
                return getItem();
            }
            return getItem()+" ("+getSender().getName()+")";

        }

        public long getItemID() {
            return itemID;
        }

        public void setItemID(long itemID) {
            this.itemID = itemID;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
    public static String getCurrTime(){
        Calendar cal=Calendar.getInstance();
        return cal.get(Calendar.YEAR)+"-"+String.format("%2d", cal.get(Calendar.MONTH)+1)+"-"
                +String.format("%2d", cal.get(Calendar.DAY_OF_MONTH))+
                " "+String.format("%2d", cal.get(Calendar.HOUR_OF_DAY))+":"+String.format("%2d", cal.get(Calendar.MINUTE));
    }

    public static Person getPersonNameFromID(String personID){
        Person p;
        p = ApplicationWideData.getPersonByID(Long.parseLong(personID));
        if(p==null){
            p=new Person("Shadow Broker", null);
        }
        return p;
    }




}
