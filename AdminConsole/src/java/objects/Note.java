/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.util.HashMap;

/**
 *
 * @author daveyle
 */
public class Note {
     private String title;
    private String body;
    private String date;
    private String time;
    private boolean isHidden=false;
    private long id;
    public Note(){
        
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean isHidden) {
        this.isHidden = isHidden;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public static Note parseNote(HashMap<String, Object> map){
         HashMap<String, Object> source=(HashMap)map.get("_source");
                  
                    final Note s =new Note();
                    s.setId(Integer.parseInt(((String)map.get("_id"))));
                    s.setTitle((String) source.get("name"));
                    if(source.get("dateArchived") == null)
                        s.setHidden(false);
                    else
                        s.setHidden(true);
                    s.setBody((String)source.get("contents"));
                    
                    String s1=(String) source.get("lastModTime");
                    s.setTime(s1.split(" ")[1]);
                    s.setDate(s1.split(" ")[0]);
                    
        return s;
    }
}
