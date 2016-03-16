/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author daveyle
 */
public class MessageThread {
    private long id;
    private String title;
    private long parentID;
    private String newestTime;
    private boolean isHidden=false;
    private List<Message> itemList=new ArrayList<Message>();
    
    public MessageThread(){
        
    }

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

    public String getNewestTime() {
        return newestTime;
    }

    public void setNewestTime(String newestTime) {
        this.newestTime = newestTime;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean isHidden) {
        this.isHidden = isHidden;
    }

    public List<Message> getItemList() {
        
        return itemList;
    }

    public void setItemList(List<Message> itemList) {
        this.itemList = itemList;
        this.itemList.sort(new Comparator<Message>(){

            @Override
            public int compare(Message o1, Message o2) {
                return o1.time.compareTo(o2.time);
            }
            
        });
    }
    public void addItemToList(Message item){
        itemList.add(item);
        itemList.sort(new Comparator<Message>(){

            @Override
            public int compare(Message o1, Message o2) {
                return o1.time.compareTo(o2.time);
            }
            
        });
    }
   
    
    public static MessageThread parseThread(HashMap<String, Object> map){
         HashMap<String, Object> source=(HashMap)map.get("_source");
                  
                    final MessageThread s =new MessageThread();
                    s.setId(Integer.parseInt(((String)map.get("_id"))));
                    s.setTitle((String) source.get("name"));
                    s.setNewestTime((String) source.get("timeModified"));
                    
                    
                    
                    if(source.get("dateArchived") == null)
                        s.setHidden(false);
                    else
                        s.setHidden(true);
                    
        return s;
    }
    
    
    
    
    
    
    public static class Message{
        private long Id;
        private String item;
        private String sender;
        private String time;
    
    public Message(){
        
    }

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

        public String getSender() {
            return sender;
        }

        public void setSender(String sender) {
            this.sender = sender;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
        
        public static Message parseMessage(HashMap<String, Object> map){
         HashMap<String, Object> source=(HashMap)map.get("_source");
                  
                    final Message s =new Message();
                    s.setId(Integer.parseInt(((String)map.get("_id"))));
                    s.setItem((String) source.get("text"));
                    
                    
                   
                    s.setTime((String)source.get("sentDate"));
                    s.setSender((String)source.get("personID"));
                    
        return s;
    }
    
    }
    
}
