package edu.rose_hulman.srproject.humanitarianapp.models;

import java.util.Calendar;

/**
 * Created by daveyle on 10/4/2015.
 */
public class Note {
    private String title;
    private String body;
    private String date;
    private String time;
    private long parentID;
    private long id;

    public Note(long id) {
        Calendar c = Calendar.getInstance();
        date=c.get(Calendar.YEAR)+"-"+String.format("%02d", (c.get(Calendar.MONTH) + 1))+"-"+String.format("%02d", c.get(Calendar.DAY_OF_MONTH));
        time=String.format("%02d", c.get(Calendar.HOUR_OF_DAY))+":"+String.format("%02d", c.get(Calendar.MINUTE));
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        Calendar c = Calendar.getInstance();
        date=c.get(Calendar.YEAR)+"-"+String.format("%02d", (c.get(Calendar.MONTH) + 1))+"-"+String.format("%02d", c.get(Calendar.DAY_OF_MONTH));
        time=String.format("%02d", c.get(Calendar.HOUR_OF_DAY))+":"+String.format("%02d", c.get(Calendar.MINUTE));

        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public void setLastModified(String date, String time){
        this.date=date;
        this.time=time;
    }
    public void setLastModified(String datetime){
        String[] split=datetime.split(" ");
        if (split.length==2){
            this.date=split[0];
            this.time=split[1];
        }
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
    public String getLastModified(){
        return date+" "+time;
    }

    public long getID() {
        return id;
    }

    public void setID(long id) {
        this.id = id;
    }

    public long getParentID() {
        return parentID;
    }

    public void setParentID(long parentID) {
        this.parentID = parentID;
    }

    public String toJSON(){
        StringBuilder sb=new StringBuilder();
        sb.append("{");
        sb.append("\"contents\": \""+getBody()+"\",");
        sb.append("\"lastModTime\": \""+getLastModified()+"\",");
        sb.append("\"name\": \""+getTitle()+"\",");
        sb.append("\"parentID\": \""+getParentID()+"\"");
        sb.append("}");
        return sb.toString();
    }
}
