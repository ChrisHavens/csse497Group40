package edu.rose_hulman.srproject.humanitarianapp.models;

import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

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
    private HashMap<String, Message> itemList=new HashMap<String, Message>();
    private int dirtyBits = 0;
    private int count=0;
    private Comparator<Message> comparator= new Comparator<Message>() {
        @Override
        public int compare(Message lhs, Message rhs) {
            return lhs.getTime().compareTo(rhs.getTime());
        }
    };
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
        for (Message item: itemList){
            this.itemList.put(item.getItemID()+"", item);
        }
            //this.itemList = itemList;
    }

    public MessageThread(String title, long id) {
        this.title = title;
        this.id = id;
    }

    public MessageThread(String title, List<Message> itemList, long id) {
        this.title = title;
        for (Message item: itemList){
            this.itemList.put(item.getItemID()+"", item);
        }
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
        List<Message> itemList=getItemList();
        Collections.sort(itemList,comparator);
        StringBuilder sb= new StringBuilder();
        for (int i=0; i<itemList.size(); i++){
            Message item=itemList.get(i);
            sb.append(item.toJSON());
            sb.append(",");
        }
        if (sb.toString().endsWith(",")){
            sb.deleteCharAt(sb.length()-1);
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
        List<Message> messages=new ArrayList<>();
        messages.addAll(itemList.values());
        return messages;
    }

    public void setItemList(List<Message> itemList) {
        for (Message item: itemList){
            this.itemList.put(item.getItemID()+"", item);
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addItem(Message item){
        this.itemList.put(item.getItemID() + "", item);
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
        int i=0;
        for (String index: itemList.keySet()){
            long base2=(base+i)*1000;
            Message item=itemList.get(index);
            item.setItemID(base2+i);
            i++;

        }
    }
    public Message addBuildNewMessage(Message message){
        Log.wtf("s40-5", itemList.size()+"");

        long base=this.id*1000;
        count++;
        long base2=(base+count+1);

        message.setItemID(base2);
        Log.wtf("s40-2", message.getTime());
        itemList.put(message.getItemID()+"", message);
        //2016-01-10 > 2016-01-09

        if (this.newestTime==null || message.getTime().compareTo(this.newestTime)>0){
            this.newestTime=message.getTime();
        }
        return message;
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public static class Message{
        private long itemID;
        private String item;
        private String sender;
        private String time;


        public Message(){

        }
        public Message(long itemID){
            this.itemID=itemID;
        }

        public Message(String item) {
            this(item, "");
//            this(item, null);
        }

//        public Message(String item, Person sender) {
//            this.item = item;
//            this.sender = sender;
//            this.time=MessageThread.getCurrTime();
//
//        }
        public Message(String item, String senderID){
            this.item = item;
            this.sender = senderID;
            this.time=getCurrTime();

        }
        public Message(String item, String senderID, String time){
            this.item=item;
            this.sender=senderID;
            this.time=time;
        }
        public String toJSON(){
            StringBuilder sb=new StringBuilder();
            sb.append("{\"messageID\": \""+itemID+"\",");
            if (getSender()!=null) {

                sb.append("\"personID\": \"" + sender+"\",");
            }
            sb.append("\"sentDate\": \""+getTime()+"\",");
            sb.append("\"text\": \""+getItem()+"\"");

            sb.append("}");
            return sb.toString();
        }
        public String toMessageSendString(){
            StringBuilder sb=new StringBuilder();
            sb.append("{");

                sb.append("\"personID\": \"" + sender+"\",");

            sb.append("\"sentDate\": \""+getTime()+"\",");
            // sb.append("\"text\": \""+item.getItem().replaceAll("\\\\n", "\\\\n")+"\"");
            sb.append("\"text\": \""+getItem()+"\"");

            sb.append("}");
            Log.wtf("S40", sb.toString());
            return sb.toString();
        }

        public String getSender() {
            return sender;
        }

        public void setSender(String sender) {
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
            return getItem()+" ("+sender+")";

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
        return cal.get(Calendar.YEAR)+"-"+String.format("%02d", cal.get(Calendar.MONTH)+1)+"-"
                +String.format("%02d", cal.get(Calendar.DAY_OF_MONTH))+
                " "+String.format("%02d", cal.get(Calendar.HOUR_OF_DAY))+":"+String.format("%02d", cal.get(Calendar.MINUTE));
    }

    public static Person getPersonNameFromID(String personID){
        try {
            Person p;
            p = ApplicationWideData.getPersonByID(Long.parseLong(personID));
            if (p == null) {
                p = new Person("Shadow Broker", null);
            }
            return p;
        }catch (Exception e){
            return new Person("Shadow Broker", null);
        }
    }

    @Override
    public String getDateTimeModified() {
        return datetime;
    }

    @Override
    public void setDateTimeModified(String dateTime) {
        this.datetime=dateTime;
    }


}
