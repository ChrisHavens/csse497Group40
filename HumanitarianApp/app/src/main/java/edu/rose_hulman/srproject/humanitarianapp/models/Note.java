package edu.rose_hulman.srproject.humanitarianapp.models;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import edu.rose_hulman.srproject.humanitarianapp.localdata.ApplicationWideData;

/**
 * Created by daveyle on 10/4/2015.
 */
public class Note implements Selectable{
    private String title;
    private String body;
    private String date;
    private String time;
    private boolean isHidden=false;
    private long parentID;
    private long id;
    private int dirtyBits = 0;

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
    public String getType(){return "Note";}
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

    @Override
    public String getDateTimeModified() {
        if(date == null || date.equals("") || time==null || time.equals("")){
            return ApplicationWideData.getCurrentTime();
        }
        return date+" "+time;
    }

    @Override
    public void setDateTimeModified(String dateTime) {
        String[] split=dateTime.split(" ");
        if (split.length==2){
            this.date=split[0];
            this.time=split[1];
        }
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
    @Override
    public void updateFromConflicts(List<Conflict> conflictList) {
        for (Conflict c : conflictList) {
            if (c.fieldName.equals("name")) {
                this.setTitle(c.getChosenVersion());
            }else if (c.fieldName.equals("contents")){
                this.setBody(c.getChosenVersion());
            }else if (c.fieldName.equals("parentID")){
                this.setParentID(Long.parseLong(c.getChosenVersion()));
            }else if (c.fieldName.equals("lastModTime")){
                this.setDateTimeModified(c.getChosenVersion());
            }
        }
    }

    public String toJSON(){
        StringBuilder sb=new StringBuilder();
        sb.append("{");
        sb.append("\"contents\": \""+getBody().replaceAll("\\\\n", "\\\\n")+"\",");
        sb.append("\"lastModTime\": \""+getLastModified()+"\",");
        sb.append("\"name\": \""+getTitle()+"\",");
        sb.append("\"parentID\": \""+getParentID()+"\"");
        sb.append("}");
        return sb.toString();
    }
    public static Note fromJSON(long lg, String json){
        ObjectMapper mapper=new ObjectMapper();
        TypeReference<HashMap<String, Object>> typeReference=
                new TypeReference<HashMap<String, Object>>() {
                };
        try {
            HashMap<String, Object> source = mapper.readValue(json, typeReference);
            Note l=parseJSON(lg, source);
            return l;





        }catch(Exception e){

        }
        return null;
    }
    public static Note parseJSON(long lg, HashMap<String, Object> source){
        Note n= new Note(lg);
        n.setTitle((String) source.get("name"));
        if(source.get("dateArchived") == null)
            n.setHidden(false);
        else
            n.setHidden(true);
        n.setBody((String) source.get("contents"));
        n.setLastModified((String) source.get("lastModTime"));
        return n;
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
}
