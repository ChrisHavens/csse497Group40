package edu.rose_hulman.srproject.humanitarianapp.controllers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import edu.rose_hulman.srproject.humanitarianapp.localdata.ApplicationWideData;
import edu.rose_hulman.srproject.humanitarianapp.localdata.LocalDataRetriver;
import edu.rose_hulman.srproject.humanitarianapp.localdata.LocalDataSaver;
import edu.rose_hulman.srproject.humanitarianapp.models.Checklist;
import edu.rose_hulman.srproject.humanitarianapp.models.Conflict;
import edu.rose_hulman.srproject.humanitarianapp.models.Group;
import edu.rose_hulman.srproject.humanitarianapp.models.Location;
import edu.rose_hulman.srproject.humanitarianapp.models.Note;
import edu.rose_hulman.srproject.humanitarianapp.models.Person;
import edu.rose_hulman.srproject.humanitarianapp.models.Project;
import edu.rose_hulman.srproject.humanitarianapp.models.Selectable;
import edu.rose_hulman.srproject.humanitarianapp.models.Shipment;
import edu.rose_hulman.srproject.humanitarianapp.models.MessageThread;
import edu.rose_hulman.srproject.humanitarianapp.nonlocaldata.NonLocalDataService;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by daveyle on 12/1/2015.
 */
public class MainServiceActions {
    NonLocalDataService service;
    private Group selectedGroup;
    private Person selectedPerson;
    private Checklist selectedChecklist;
    private Note selectedNote;
    private Location selectedLocation;
    private Shipment selectedShipment;
    private Project selectedProject;
    private MessageThread selectedMessageThread;
    private long parentID;
    private List<Project> storedProjects;
    private boolean isFromProject=false;
    private MainActivity context;
    private String userID;

    //private List<Conflict> resolved;
    public MainServiceActions(MainActivity context, String userID){
        this.service=new NonLocalDataService();
        this.context=context;
        this.userID=userID;

    }

    public Group getSelectedGroup() {
        return selectedGroup;
    }

    public void setSelectedGroup(Group selectedGroup) {
        this.selectedGroup = selectedGroup;
    }

    public Person getSelectedPerson() {
        return selectedPerson;
    }

    public void setSelectedPerson(Person selectedPerson) {
        this.selectedPerson = selectedPerson;
    }

    public Checklist getSelectedChecklist() {
        return selectedChecklist;
    }

    public void setSelectedChecklist(Checklist selectedChecklist) {
        this.selectedChecklist = selectedChecklist;
    }
    public MessageThread getSelectedMessageThread() {
        return selectedMessageThread;
    }

    public void setSelectedMessageThread(MessageThread selectedThread) {
        this.selectedMessageThread = selectedThread;
    }

    public Note getSelectedNote() {
        return selectedNote;
    }

    public void setSelectedNote(Note selectedNote) {
        this.selectedNote = selectedNote;
    }

    public Location getSelectedLocation() {
        return selectedLocation;
    }

    public void setSelectedLocation(Location selectedLocation) {
        this.selectedLocation = selectedLocation;
    }

    public Shipment getSelectedShipment() {
        return selectedShipment;
    }

    public void setSelectedShipment(Shipment selectedShipment) {
        this.selectedShipment = selectedShipment;
    }

    public Project getSelectedProject() {
        return selectedProject;
    }

    public void setSelectedProject(Project selectedProject) {
        this.selectedProject = selectedProject;
    }

    public long getParentID() {
        return parentID;
    }

    public void setParentID(long parentID) {
        this.parentID = parentID;
    }

    public List<Project> getStoredProjects() {
        return storedProjects;
    }

    public void setStoredProjects(List<Project> storedProjects) {
        this.storedProjects = storedProjects;
    }

    public boolean isFromProject() {
        return isFromProject;
    }

    public void setIsFromProject(boolean isFromProject) {
        this.isFromProject = isFromProject;
    }


//    public HashMap<Selectable, List<Conflict>> getConflicts() {
//        return conflicts;
//    }
//
//    public void setConflicts(HashMap<Selectable, List<Conflict>> conflicts) {
//        this.conflicts = conflicts;
//    }

    public void saveNote(String title, String body){
        body=body.replaceAll("\n", "\\\n");
        this.selectedNote.setLastModified(ApplicationWideData.getCurrentTime());
        this.selectedNote.setTitle(title);
        this.selectedNote.setBody(body);
        service.updateNote(this.selectedNote, userID, new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                context.refreshLists();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.w("RetrofitError", error.getMessage());
            }
        });
    }

    public void addNewPerson(final String name, String phone, String email) {
        long projectID=selectedProject.getID();
        long groupID=-1;
        if (getSelectedGroup()!=null) {
            groupID = getSelectedGroup().getID();
        }
        Person p=new Person(((long)((int)Calendar.getInstance().getTimeInMillis())), name, phone, email);

        edu.rose_hulman.srproject.humanitarianapp.models.Person.PersonLocation location=new edu.rose_hulman.srproject.humanitarianapp.models.Person.PersonLocation();
        location.setName("Omega 4 Relay");
        location.setTime("1985-04-05 14:45");
        location.setLat(34.56f);
        location.setLng(-5.45f);

        //location.setID(10000);
        p.setLastCheckin(location);
        p.addProjectID(projectID);
        if (groupID!=-1) {
            p.addGroupID(groupID);
        }

        //location.setId(10000);
        p.setLastCheckin(location);
        p.addProjectID(projectID);
        if (groupID != -1) {
            p.addGroupID(groupID);
        }
        Callback<Response> responseCallback = new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                Toast.makeText(context, "Successful adding of new person: " + name, Toast.LENGTH_LONG).show();
                context.refreshLists();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("RetrofitError", error.getMessage());
            }
        };
        //NonLocalDataService service=new NonLocalDataService();
        service.addNewPerson(p,  userID, responseCallback);
    }
    public void addPersonToProjectOrGroup(final Person p) {
        long projectID=selectedProject.getID();

        if (getSelectedGroup()!=null) {
            Log.wtf("Adding to group", getSelectedGroup().getID() + "");
            addPersonToGroup(p, projectID, getSelectedGroup().getID());

        }
        else{
            addPersonToProject(p, projectID);
        }

    }
    public void removePersonFromProjectOrGroup(final Person p){
        final long projectID=selectedProject.getID();
        if (getSelectedGroup()!=null){
            final long groupID=getSelectedGroup().getID();
            p.removeGroupID(groupID);
            Callback<Response> responseCallback = new Callback<Response>() {
                @Override
                public void success(Response response, Response response2) {
                    Toast.makeText(context, "Successful removal of person: " + p.getName()+" from group "+groupID, Toast.LENGTH_SHORT).show();
                    context.refreshLists();
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e("RetrofitError", error.getMessage());
                    Log.e("RetrofitError", error.getUrl());
                }
            };
            service.removePersonFromGroup(p.getID() + "", groupID + "", userID + "", responseCallback);
        }
        else{
            p.removeProjectID(projectID);
            Callback<Response> responseCallback = new Callback<Response>() {
                @Override
                public void success(Response response, Response response2) {
                    Toast.makeText(context, "Successful removal of person: " + p.getName()+" from project "+projectID, Toast.LENGTH_SHORT).show();
                    context.refreshLists();
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e("RetrofitError", error.getMessage());
                    Log.e("RetrofitError", error.getUrl());
                }
            };
            service.removePersonFromProject(p.getID() + "", projectID + "", userID + "", responseCallback);
        }
    }
    private void addPersonToProject(final Person p, final long projectID){


        p.addProjectID(projectID);

        Callback<Response> responseCallback = new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                Toast.makeText(context, "Successful adding of new person: " + p.getName()+" to project "+projectID, Toast.LENGTH_SHORT).show();
                context.refreshLists();
            }

            @Override
            public void failure(RetrofitError error) {
                //Log.e("RetrofitError", error.g)
                Log.e("RetrofitError", error.getMessage());
                Log.e("RetrofitError", error.getUrl());
            }
        };
//        //NonLocalDataService service=new NonLocalDataService();
        service.addPersonToProject(p.getID()+"",projectID+"",  userID, responseCallback);
    }
    private void addPersonToGroup(final Person p, long projectID, final long groupID){
        if (!p.isInProject(projectID)){
            addPersonToProject(p, projectID);
        }
        p.addGroupID(groupID);
        Callback<Response> responseCallback = new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                Toast.makeText(context, "Successful adding of new person: " + p.getName()+" to group "+groupID, Toast.LENGTH_SHORT).show();
                context.refreshLists();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("RetrofitError", error.getMessage());
            }
        };
        service.addPersonToGroup(p.getID() + "", groupID + "", userID, responseCallback);
    }

    public void addNewProject(final String name) {
//        Random rand=new Random();
//        long i= rand.nextInt(90000)+10000;
//        i+=100000;
//        Project p= new Project(name, i);
        Project p = new Project(name);
        ApplicationWideData.addNewProject(p);

        Callback<Response> responseCallback = new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                context.refreshLists();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("RetrofitError", error.getMessage());
            }
        };

        boolean success = LocalDataSaver.addProject(p);
        if (success) {
            Toast.makeText(context, "Successful adding of new project: " + name + " to local database", Toast.LENGTH_LONG).show();
        }
        if (!ApplicationWideData.getManualSync()) {
            service.addNewProject(p,  userID, responseCallback);
        }
        else{
            Log.wtf("Added project", "to added table");
            LocalDataSaver.addNewSelectable(p, "Project");
            Log.wtf("Size of added table", LocalDataRetriver.getAllAdded().size()+"");
        }
    }


    public void addNewGroup(final String name) {
        Random rand=new Random();
        long i= rand.nextInt(90000)+10000;
        i+=200000;

        Group g= new Group(i);
        g.setName(name);
        g.setProject(selectedProject);
        Callback<Response> responseCallback=new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                Toast.makeText(context, "Successful adding of new group: " + name, Toast.LENGTH_LONG).show();
                context.refreshLists();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("RetrofitError", error.getMessage());
            }
        };

        service.addNewGroup(g,  userID, responseCallback);
        Callback<Response> responseCallback2 = new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                Toast.makeText(context, "Successful editing of project", Toast.LENGTH_LONG).show();
                context.refreshLists();

            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("RetrofitError", error.getMessage());
            }
        };
        service.addNewProject(selectedProject,  userID, responseCallback);
//        sb.append("{\"doc\": {");
//        sb.append(project.getGroupString());
//        sb.append("}}");
        //service.updateProject(project.getID(), sb.toString(), responseCallback2);


    }



    public void addNewLocation(final Location l) {

        Callback<Response> responseCallback=new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                Toast.makeText(context, "Successful adding of new location: "+l.getName(), Toast.LENGTH_LONG).show();
                context.refreshLists();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("RetrofitError", error.getMessage());
            }
        };

        service.addNewLocation(l,  userID, responseCallback);

    }



    public void addNewNote(String name, String contents){
        Random rand=new Random();
        long i= rand.nextInt(90000)+10000;
        i+=500000;
        final Note note=new Note(i);
        note.setParentID(selectedGroup.getID());
        note.setTitle(name);
        note.setBody(contents);
//        Date date=new Date();
//
//        SimpleDateFormat sdf=new SimpleDateFormat("YYYY-MM-dd HH:mm");
//        String s=sdf.format(date);
//        //LocalDateTime time;
//        note.setLastModified(s);
        Callback<Response> responseCallback=new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                Toast.makeText(context, "Successful adding of new note: "+note.getTitle(), Toast.LENGTH_LONG).show();
                context.refreshLists();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("RetrofitError", error.getMessage());
            }
        };
        service.addNewNote(note,  userID, responseCallback);
    }

    public void addNewShipment(final Shipment l) {
        Callback<Response> responseCallback=new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                Toast.makeText(context, "Successful adding of new shipment: "+l.getName(), Toast.LENGTH_LONG).show();
                context.refreshLists();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("RetrofitError", error.getMessage());
            }
        };
        service.addNewShipment(l,  userID, responseCallback);
    }
    public void addNewChecklist(final Checklist checklist) {
        Random rand = new Random();
        long i = rand.nextInt(90000) + 10000;
        i += 700000;
        checklist.setID(i);
        checklist.setItemIDs();

        Callback<Response> responseCallback = new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                Toast.makeText(context, "Successful adding of new checklist: " + checklist.getTitle(), Toast.LENGTH_LONG).show();
                context.refreshLists();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("RetrofitError", error.getMessage());
            }
        };

        service.addNewChecklist(checklist, userID, responseCallback);
    }
    public void addNewMessageThread(final MessageThread m){

        Random rand = new Random();
        long i = rand.nextInt(90000) + 10000;
        i += 700000;
        m.setID(i);
        m.setItemIDs();
        Callback<Response> responseCallback = new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                Toast.makeText(context, "Successful adding of new thread: " + m.getTitle(), Toast.LENGTH_LONG).show();
                context.refreshLists();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("RetrofitError", error.getMessage());
            }
        };
        Log.d("ED","inside of addNewMessageThread");
        service.addNewThread(m, userID, responseCallback);


    }
    public void addNewMessage(String message, String userID){
        Log.wtf("s40", userID);
        Callback<Response> addResponse=new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                Toast.makeText(context, "Message sent", Toast.LENGTH_SHORT).show();
                context.refreshLists();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.wtf("Retrofit Error", error.getUrl());
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        };
        MessageThread.Message message1=new MessageThread.Message(message, userID);
        Log.wtf("s40",message1.getTime());
        message1=getSelectedMessageThread().addBuildNewMessage(message1);
        //Log.wtf("s40-6", message1.)
        Log.wtf("s40-4", getSelectedMessageThread().getID() + "");
        Log.wtf("s40-3", message1.getItemID() + "");
        service.addNewMessage(getSelectedMessageThread().getID() + "", message1, userID, addResponse);
    }
    public void hideProject() {
        Callback<Response> hideResponse=new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                //Toast.makeText(context, "Successfully hid project", Toast.LENGTH_LONG).show();
                context.refreshLists();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.wtf("s40", error.getMessage());

            }
        };
        String hideOrShow = "hide";
        if(selectedProject.isHidden())
            hideOrShow = "show";

        service.changeVisibilityProject(selectedProject.getID() + "", hideOrShow, userID, hideResponse);
        //hide("project", selectedProject.getID() + "", hideResponse);
    }



    public void hideGroup() {
        Callback<Response> hideResponse=new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                //Toast.makeText(context, "Successfully hid group", Toast.LENGTH_LONG).show();
                context.refreshLists();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.wtf("s40", error.getMessage());

            }
        };
        String hideOrShow = "hide";
        if(selectedGroup.isHidden())
            hideOrShow = "show";

        service.changeVisibilityGroup(selectedGroup.getID() + "", hideOrShow, userID, hideResponse);
//        service.hide("group", selectedGroup.getID() + "", hideResponse);
    }


    public void hideChecklist() {
        Callback<Response> hideResponse=new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                //Toast.makeText(context, "Successfully hid checklist", Toast.LENGTH_LONG).show();
                context.refreshLists();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.wtf("s40", error.getMessage());

            }
        };
        String hideOrShow = "hide";
        if(selectedChecklist.isHidden())
            hideOrShow = "show";

        service.changeVisibilityChecklist(selectedChecklist.getID() + "", hideOrShow, userID, hideResponse);
    }


    public void hideLocation() {
        Callback<Response> hideResponse=new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                //Toast.makeText(context, "Successfully hid location", Toast.LENGTH_LONG).show();
                context.refreshLists();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.wtf("s40", error.getMessage());

            }
        };
        String hideOrShow = "hide";
        if(selectedLocation.isHidden())
            hideOrShow = "show";

        service.changeVisibilityLocation(selectedLocation.getID() + "", hideOrShow, userID, hideResponse);
//        service.hide("location", selectedLocation.getID()+"", hideResponse);
    }


    public void hideNote() {
        Callback<Response> hideResponse=new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                //Toast.makeText(context, "Successfully hid note", Toast.LENGTH_LONG).show();
                context.refreshLists();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.wtf("s40", error.getMessage());

            }
        };
        String hideOrShow = "hide";
        if(selectedNote.isHidden())
            hideOrShow = "show";

        service.changeVisibilityNote(selectedNote.getID() + "", hideOrShow, userID, hideResponse);
//        service.hide("note", selectedNote.getID() + "", hideResponse);
    }

    public void hidePerson() {
        Callback<Response> hideResponse=new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                //Toast.makeText(context, "Successfully hid person", Toast.LENGTH_LONG).show();
                context.refreshLists();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.wtf("s40", error.getMessage());

            }
        };
        String hideOrShow = "hide";
        if(selectedPerson.isHidden())
            hideOrShow = "show";

        service.changeVisibilityPerson(selectedPerson.getID() + "", hideOrShow, userID, hideResponse);

//        service.hide("person", selectedPerson.getID()+"", hideResponse);
    }


    public void hideShipment() {
        Callback<Response> hideResponse=new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                //Toast.makeText(context, "Successfully hid shipment", Toast.LENGTH_LONG).show();
                context.refreshLists();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.wtf("s40", error.getMessage());

            }
        };
        service.changeVisibilityShipment(selectedShipment.getID() + "", "hide", userID, hideResponse);
//        service.hide("shipment", selectedShipment.getID() + "", hideResponse);

    }
    public void editChecklist(final Checklist c){
        Callback<Response> responseCallback=new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                Toast.makeText(context, "Successful editing of checklist: "+c.getTitle(), Toast.LENGTH_LONG).show();
                context.refreshLists();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("RetrofitError", error.getMessage());
            }
        };

        service.addNewChecklist(c, userID, responseCallback);
    }
    public void resolveConflicts(Selectable s, List<Conflict> conflicts){
        Callback<Response> hideResponse=new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                //Toast.makeText(context, "Successfully hid shipment", Toast.LENGTH_LONG).show();
                context.refreshLists();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.wtf("s40R", error.getMessage());

            }
        };
        String type="";
        if (s instanceof Project){
            type="project";
            service.resolveConflict(type,s.getID()+"",((Project)s).toJSON(), hideResponse);
        }
        else if (s instanceof Group){
            type="group";
        }
        else if (s instanceof Person){
            type="person";
        }
        else if (s instanceof Note){
            type="note";
        }
        else if (s instanceof Shipment){
            type="shipment";
        }

    }

//    public void resolveConflicts(List<Conflict> conflicts){
//        //resolved=conflicts;
//        //WHATEVERS
//
//    }

}
