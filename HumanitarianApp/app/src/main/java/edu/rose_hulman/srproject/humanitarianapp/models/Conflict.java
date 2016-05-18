package edu.rose_hulman.srproject.humanitarianapp.models;

/**
 * Created by daveyle on 5/10/2016.
 */
public class Conflict{
   // public Selectable item;
    public String fieldName;
    public String localVersion;
    public String serverVersion;
    private String chosenVersion;
    private boolean isChoiceMade=false;

    public Conflict(String fieldName, String serverVersion, String localVersion) {
        this.fieldName = fieldName;
        this.serverVersion = serverVersion;
        this.localVersion = localVersion;
    }


    public String getChosenVersion() {
        return chosenVersion;
    }

    public void setChosenVersion(String chosenVersion) {
        this.isChoiceMade=true;
        this.chosenVersion = chosenVersion;
    }
    public void setChosenVersionToServer(){
        this.chosenVersion=serverVersion;
        this.isChoiceMade=true;
    }
    public void setChosenVersionToLocal(){
        this.isChoiceMade=true;
        this.chosenVersion=localVersion;
    }

    public boolean isChoiceMade() {
        return isChoiceMade;
    }

    public void setIsChoiceMade(boolean isChoiceMade) {
        this.isChoiceMade = isChoiceMade;
    }
}