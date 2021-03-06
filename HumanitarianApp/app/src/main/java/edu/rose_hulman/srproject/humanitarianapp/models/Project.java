package edu.rose_hulman.srproject.humanitarianapp.models;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import edu.rose_hulman.srproject.humanitarianapp.localdata.ApplicationWideData;
import edu.rose_hulman.srproject.humanitarianapp.serialisation.SerilizationConstants;

/**
 * Created by Chris Havens on 10/4/2015.
 */
public class Project implements Selectable {


     // A flag for each field denoting if it needs to be updated on the server.
     // This will need to be initulized in the constructors.
     private boolean[] isDirty = new boolean[9];
     //A flag for if the object was synced from the server or created locally
     private boolean onServer = false;

     // Any project with the same ID is said to be the same project, across all instances
     private long id;                       //#0
     private String name;                   //#1
     private String description;            //#2
     private List<Long> groupIDs;           //#3
     private List<Long> workerIDs;          //#4
     private List<Long> adminIDs;           //#5
     private List<Long> locationIDs;        //#6
     private List<Long> checklistIDs;       //#7
     private List<Long> shipmentIDs;        //#8
    private boolean isHidden=false;
    private String datetime;

    public boolean equals(Object obj){
        if(obj.getClass().equals(this.getClass())){
            Project proj = (Project) obj;
            return proj.getID() == this.getID();
        }
        return false;
    }

     public Project(){
         removeImplicitVariableDeclarations();
     }

    public Project(long id, String name, String description, boolean[] dirtyBits, boolean onServer) {
        removeImplicitVariableDeclarations();
        this.id = id;
        this.name = name;
        this.description = description;
        this.isDirty = dirtyBits;
        this.onServer = onServer;
    }

     public Project(long id){
        this.removeImplicitVariableDeclarations();
        this.id = id;
        ApplicationWideData.addNewProject(this);
     }

     public Project(long id, String name){
        this.removeImplicitVariableDeclarations();
        this.id = id;
        this.name = name;
        ApplicationWideData.addNewProject(this);
     }

     public Project(long id, String name, String description){
        this.removeImplicitVariableDeclarations();
        this.id = id;
        this.name = name;
        this.description = description;
        ApplicationWideData.addNewProject(this);
     }

     public Project(String name){
        this.setupAsNew();
        this.name = name;
     }

     public Project(String name, String description){
        this.setupAsNew();
        this.name = name;
        this.description = description;
     }


     public void setupAsNew() {
        this.removeImplicitVariableDeclarations();
        this.id = SerilizationConstants.generateID(SerilizationConstants.PROJECT_NUM);
        Arrays.fill(isDirty, true);
        this.onServer = false;
        ApplicationWideData.addNewProject(this);
     }

     private void removeImplicitVariableDeclarations() {
        this.name = "";
        this.description = "";
        this.groupIDs =  new ArrayList<Long>();
        this.workerIDs =  new ArrayList<Long>();
        this.adminIDs =  new ArrayList<Long>();
        this.locationIDs =  new ArrayList<Long>();
        this.checklistIDs = new ArrayList<Long>();
        this.shipmentIDs = new ArrayList<Long>();
     }

     public void fullClean() {
        Arrays.fill(isDirty, false);
        this.onServer = true;
    }

    public long getID() {
        return this.id;
    }

    public boolean[] getIsDirty() {
        return this.isDirty;
    }

    public int getDirtyBits() {
        int bits = 0;
        for(int i = 0; i < 9; i++) {
            if (isDirty[i]) {
                bits = bits | 1 << i;
            }
        }
        return bits;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name){
        this.name = name;
        this.isDirty[1] = true;
    }

    public void setNameClean (String name) {
        this.name = name;
        this.isDirty[1] = false;
    }

    public void setNameMaintain (String name) {
        this.name = name;
    }

    public void setDescription(String description){
        this.description = description;
        this.isDirty[2] = true;
    }

    public void setDescriptionClean (String description) {
        this.description = description;
        this.isDirty[2] = false;
    }

    public void setDescriptionMaintain (String description) {
        this.description = description;
    }

    public void setGroupIDs(List groupIDs){
        this.groupIDs = groupIDs;
        this.isDirty[3] = true;
    }

    public void setGroupIDsClean (List groupIDs) {
        this.groupIDs = groupIDs;
        this.isDirty[3] = false;
    }

    public void setGroupIDsMaintain (List groupIDs) {
        this.groupIDs = groupIDs;
    }

    public void setWorkerIDs(List workerIDs){
        this.workerIDs = workerIDs;
        this.isDirty[4] = true;
    }

    public void setWorkerIDsClean (List workerIDs) {
        this.workerIDs = workerIDs;
        this.isDirty[4] = false;
    }

    public void setWorkerIDsMaintain (List workerIDs) {
        this.workerIDs = workerIDs;
    }

    public void setAdminIDs(List adminIDs){
        this.adminIDs = adminIDs;
        this.isDirty[5] = true;
    }

    public void setAdminIDsClean (List adminIDs) {
        this.adminIDs = adminIDs;
        this.isDirty[5] = false;
    }

    public void setAdminIDsMaintain (List adminIDs) {
        this.adminIDs = adminIDs;
    }

    public void setLocationIDs(List locationIDs){
        this.locationIDs = locationIDs;
        this.isDirty[6] = true;
    }

    public void setLocationIDsClean (List locationIDs) {
        this.locationIDs = locationIDs;
        this.isDirty[6] = false;
    }

    public void setLocationIDsMaintain (List locationIDs) {
        this.locationIDs = locationIDs;
    }

    public void setChecklistIDs(List checklistIDs){
        this.checklistIDs = checklistIDs;
        this.isDirty[7] = true;
    }

    public void setChecklistIDsClean (List checklistIDs) {
        this.checklistIDs = checklistIDs;
        this.isDirty[7] = false;
    }

    public void setChecklistIDsMaintain (List checklistIDs) {
        this.checklistIDs = checklistIDs;
    }

    public void setShipmentIDs(List shipmentIDs){
        this.shipmentIDs = shipmentIDs;
        this.isDirty[8] = true;
    }

    public void setShipmentIDsClean (List shipmentIDs) {
        this.shipmentIDs = shipmentIDs;
        this.isDirty[8] = false;
    }

    public void setShipmentIDsMaintain (List shipmentIDs) {
        this.shipmentIDs = shipmentIDs;
    }

    public void addGroupIDs (Long id) {
        this.groupIDs.add(id);
        this.isDirty[3] = true;
    }

    public void addGroupIDsClean (Long id) {
        this.groupIDs.add(id);
        this.isDirty[3] = false;
    }

    public void addGroupIDsMaintain (Long id) {
        this.groupIDs.add(id);
    }

    public void addWorkerID (Long id) {
        this.workerIDs.add(id);
        this.isDirty[4] = true;
    }

    public void addWorkerIDClean (Long id) {
        this.workerIDs.add(id);
    }

    public void addWorkerIDMaintain (Long id) {
        this.workerIDs.add(id);
    }

    public void addAdminID (Long id) {
        this.adminIDs.add(id);
        this.isDirty[5] = false;
    }

    public void addAdminIDClean (Long id) {
        this.adminIDs.add(id);
        this.isDirty[5] = false;
    }

    public void addAdminIDMaintain (Long id) {
        this.adminIDs.add(id);
    }

    public void addLocationID (Long id) {
        this.locationIDs.add(id);
        this.isDirty[6] = true;
    }

    public void addLocationIDClean (Long id) {
        this.locationIDs.add(id);
        this.isDirty[6] = false;
    }

    public void addLocationIDMaintain (Long id) {
        this.locationIDs.add(id);
    }

    public void addChecklistID (Long id) {
        this.checklistIDs.add(id);
        this.isDirty[7] = true;
    }

    public void addChecklistIDClean (Long id) {
        this.checklistIDs.add(id);
        this.isDirty[7] = false;
    }

    public void addChecklistIDMaintain (Long id) {
        this.checklistIDs.add(id);
    }

    public void addShipmentID (Long id) {
        this.shipmentIDs.add(id);
        this.isDirty[8] = true;
    }

    public void addShipmentIDClean (Long id) {
        this.shipmentIDs.add(id);
        this.isDirty[8] = false;
    }

    public void addShipmentIDMaintain (Long id) {
        this.shipmentIDs.add(id);
    }



    public List<Person> getWorkers() {
        List<Person> persons = new ArrayList<Person>();
        for (Long ID : this.workerIDs) {
            persons.add(Person.getWorkerByID(ID));
        }
        return persons;
    }

    public void setWorkers(List<Person> persons) {
        List<Long> newWorkerIDs = new ArrayList<Long>();
        for (Person person : persons) {
            newWorkerIDs.add(person.getID());
        }
        this.workerIDs = newWorkerIDs;
    }

    public List<Group> getGroups() {
        List<Group> groups = new ArrayList<Group>();
        for (Long ID : this.groupIDs) {
            groups.add(Group.getGroupByID(ID));
        }
        return groups;
    }

    public void updateWorkerID(long oldID, long newID) {
        if (this.workerIDs.contains(oldID)) {
            this.workerIDs.remove(oldID);
            this.workerIDs.add(newID);
        }
    }

    public void updateGroupIDs(long oldID, long newID) {
        if (this.groupIDs.contains(oldID)) {
            this.groupIDs.remove(oldID);
            this.groupIDs.add(newID);
        }
    }

    public void addWorkerByID(long ID) {
        if (this.workerIDs == null) {
            this.workerIDs = new ArrayList<Long>();
        }
        this.workerIDs.add(ID);
    }

    public void addGroupByID(long ID) {
        if (this.groupIDs == null) {
            this.groupIDs = new ArrayList<Long>();
        }
        this.groupIDs.add(ID);
    }
    public List<Long> getGroupIDs(){
        return this.groupIDs;
    }

    public void updateID(long newID) {
        long oldID = this.id;
        this.id = newID;
        for (Person person : Person.getKnownPersons()) {
            person.updateGroupIDs(oldID, newID);
        }
        for (Group group : Group.getKnownGroups()) {
            group.updateWorkerID(oldID, newID);
        }
    }

    public static Project getProjectByID(long id) {
        return ApplicationWideData.getProjectByID(id);
    }

    public static List<Project> getKnownProjects() {
        return ApplicationWideData.getAllProjects();
    }

    public String getType(){return "Project";}
    /*
    {
  "name": "Stop the Reapers",
  "groupIDs": [
    {
      "groupID": "grp00000"
    },
    {
      "groupID": "grp01000"
    }
    ]

}
     */



    public String toJSON() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"name\": \"" + this.name + "\",");
        //sb.append("\"role\": \""+Roles.roles[role.ordinal()]+"\",");

        sb.append(getGroupString());
        sb.append("}");
        return sb.toString();
    }

    public String getGroupString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\"groupIDs\": [");

        for (int i=0; i<groupIDs.size()-1; i++){
//            String formatted = String.format("grp%05d", );
            sb.append("{\"groupID\": \""+groupIDs.get(i)+"\"},");
        }
        if (groupIDs.size()>0){
//            String formatted = String.format("grp%05d",);
            sb.append("{\"groupID\": \""+groupIDs.get(groupIDs.size()-1)+"\"}");

//        for (int i = 0; i < groupIDs.size() - 1; i++) {
//            String formatted = String.format("grp%05d", groupIDs.get(i));
//            sb.append("{\"groupID\": \"" + formatted + "\"},");
//        }
//        if (groupIDs.size() > 0) {
//            String formatted = String.format("grp%05d", groupIDs.get(groupIDs.size() - 1));
//            sb.append("{\"groupID\": \"" + formatted + "\"}");
//>>>>>>> origin/RefactorThings
        }
        sb.append("]");
        return sb.toString();
    }

    public static Project fromJSON(long lg, String json){
        ObjectMapper mapper=new ObjectMapper();
        TypeReference<HashMap<String, Object>> typeReference=
                new TypeReference<HashMap<String, Object>>() {
                };
        try {
            HashMap<String, Object> source = mapper.readValue(json, typeReference);
            Project l=parseJSON(lg, source);
            return l;





        }catch(Exception e){

        }
        return null;
    }
    public static Project parseJSON(long lg, HashMap<String, Object> source){
       Project p= new Project(lg);
        String name = (String) source.get("name");

        p.setName(name);
        if(source.get("dateArchived") == null)
            p.setHidden(false);
        else
            p.setHidden(true);
        parseGroupIDs(p, (ArrayList<HashMap<String, Object>>)source.get("groupIDs"));
        return p;

    }
    public static void parseGroupIDs(Project p, ArrayList<HashMap<String, Object>> list){
        if (list!=null) {
            for (HashMap<String, Object> item : list) {
                String itemID = (String) item.get("groupID");
                p.addGroupByID(Long.parseLong(itemID));

            }
        }
    }
    @Override
    public void updateFromConflicts(List<Conflict> conflictList) {
        for (Conflict c : conflictList) {
            if (c.fieldName.equals("name")) {
                this.setName(c.getChosenVersion());
            }else if (c.fieldName.equals("parentIDs")){
                ObjectMapper mapper=new ObjectMapper();
                TypeReference<HashMap<String, Object>> typeReference=
                        new TypeReference<HashMap<String, Object>>() {
                        };
                try {
                    HashMap<String, Object> source = mapper.readValue(c.getChosenVersion(), typeReference);

                    ArrayList<HashMap<String, Object>> list=(ArrayList<HashMap<String, Object>>)source.get("parentIDs");
                    if (list!=null) {
                        for (HashMap<String, Object> parent :list) {
                            if (parent.containsKey("parentID")) {
                                addGroupByID(Long.parseLong((String) parent.get("parentID")));
                            }
                        }
                    }

                }catch (Exception e){

                }
            }else if (c.fieldName.equals("timeModified")){
                this.setDateTimeModified(c.getChosenVersion());
            }
        }
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean hidden) {
        this.isHidden = hidden;
    }
    @Override
    public String getDateTimeModified() {
        if(datetime == null || datetime.equals("")){
            return ApplicationWideData.getCurrentTime();
        }
        return datetime;
    }

    @Override
    public void setDateTimeModified(String dateTime) {
        this.datetime=dateTime;
    }
}

//    public Project() {
//        this.setUpDefaultID();
//    }
//
//    public Project(long ID) {
//        this.ID = ID;
//        knownProjects.add(this);
//    }
//
//    public Project(String name) {
//        this.name = name;
//        this.setUpDefaultID();
//    }
//
//    public Project(String name, long ID) {
//        this.name = name;
//        this.ID = ID;
//        knownProjects.add(this);
//
//    }
//
//    private void setUpDefaultID() {
//        Random rand = new Random();
//        int localIDNum = rand.nextInt();
//        this.ID = ((long) localIDNum) | SerilizationConstants.PROJECT_NUM;
//        localIDProjects.add(this);
//        knownProjects.add(this);
//    }

    /*
    public static Project createFullProject(String name, String description, long managerID) {
        Project project = new Project();
        if (name == null || name.length() == 0) {
            return null;
        }
        project.name = name;
        project.description = description;
        project.managerID = managerID;
        return project;
    }



    public long getID() {
        return ID;
    }

    public Person getManager() {
        return Person.getWorkerByID(this.managerID);
    }

    public void setManager(Person manager) {
        this.adminIDs.add(manager.getID()) = ;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void addLocations(Location location) {
        this.locations.add(location);
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public List<Checklist> getChecklist() {
        return checklists;
    }

    public void addChecklist(Checklist checklist) {
        this.checklists.add(checklist);
    }

    public void setChecklist(List<Checklist> checklist) {
        this.checklists = checklist;
    } */
