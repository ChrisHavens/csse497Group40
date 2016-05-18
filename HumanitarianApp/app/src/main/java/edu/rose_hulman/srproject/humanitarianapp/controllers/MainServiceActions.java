package edu.rose_hulman.srproject.humanitarianapp.controllers;

import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;
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
        if (!ApplicationWideData.manualSnyc) {
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
        else{
            LocalDataSaver.addUpdatedSelectable(selectedNote, "Note");
        }
        LocalDataSaver.saveNote(selectedNote);
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
        if (!ApplicationWideData.manualSnyc) {
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
            service.addNewPerson(p, userID, responseCallback);
        }
        else{
            LocalDataSaver.addNewSelectable(p, "Person");
        }
        LocalDataSaver.savePerson(p);

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
            if (!ApplicationWideData.manualSnyc) {
                Callback<Response> responseCallback = new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                        Toast.makeText(context, "Successful removal of person: " + p.getName() + " from group " + groupID, Toast.LENGTH_SHORT).show();
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
                LocalDataSaver.addUpdatedSelectable(p, "Person");
            }
            LocalDataSaver.savePerson(p);
        }
        else{
            p.removeProjectID(projectID);
            if (!ApplicationWideData.manualSnyc) {
                Callback<Response> responseCallback = new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                        Toast.makeText(context, "Successful removal of person: " + p.getName() + " from project " + projectID, Toast.LENGTH_SHORT).show();
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
            else{
                LocalDataSaver.addUpdatedSelectable(p, "Person");
            }
            LocalDataSaver.savePerson(p);
        }
    }
    private void addPersonToProject(final Person p, final long projectID){


        p.addProjectID(projectID);
        if (!ApplicationWideData.manualSnyc) {
            Callback<Response> responseCallback = new Callback<Response>() {
                @Override
                public void success(Response response, Response response2) {
                    Toast.makeText(context, "Successful adding of new person: " + p.getName() + " to project " + projectID, Toast.LENGTH_SHORT).show();
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
            service.addPersonToProject(p.getID() + "", projectID + "", userID, responseCallback);
        }
        else{
            LocalDataSaver.addUpdatedSelectable(p, "Person");
        }
        LocalDataSaver.savePerson(p);
    }
    private void addPersonToGroup(final Person p, long projectID, final long groupID){
        if (!p.isInProject(projectID)){
            addPersonToProject(p, projectID);
        }
        p.addGroupID(groupID);
        if (!ApplicationWideData.manualSnyc) {
            Callback<Response> responseCallback = new Callback<Response>() {
                @Override
                public void success(Response response, Response response2) {
                    Toast.makeText(context, "Successful adding of new person: " + p.getName() + " to group " + groupID, Toast.LENGTH_SHORT).show();
                    context.refreshLists();
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e("RetrofitError", error.getMessage());
                }
            };
            service.addPersonToGroup(p.getID() + "", groupID + "", userID, responseCallback);
        }
        else{
            LocalDataSaver.addUpdatedSelectable(p, "Person");
        }
        LocalDataSaver.savePerson(p);
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
            service.addNewProject(p, userID, responseCallback);
        }
        else{
            LocalDataSaver.addNewSelectable(p, "Project");
        }
            LocalDataSaver.saveProject(p);
            Log.wtf("Added project", "to added table");


            Log.wtf("Size of added table", LocalDataRetriver.getAllAdded().size() + "");

    }
    public void updateProject(final Project p){
        LocalDataSaver.saveProject(p);
        if (!ApplicationWideData.manualSnyc) {
            NonLocalDataService service = new NonLocalDataService();
            StringBuilder sb = new StringBuilder();
            sb.append("{\"doc\":"+p.getName()+"}");
            service.updateProject(p, sb.toString(), userID, new Callback<Response>() {
                @Override
                public void success(Response response, Response response2) {
                    Log.wtf("s40", "Successful edit of project " + p.getName());
                    context.refreshLists();
                    //Toast.makeText(getActivity(), "Successful edit of project "+p.getName(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e("s40 RetroFitError", error.getResponse().getStatus()+"");
                    //Log.e("s40 RetroFitE")
                }
            });
        } else {
            LocalDataSaver.addUpdatedSelectable(p, "Project");
        }
    }


    public void addNewGroup(final String name) {
        Random rand=new Random();
        long i= rand.nextInt(90000)+10000;
        i+=200000;

        Group g= new Group(i);
        g.setName(name);
        g.setProject(selectedProject);
        selectedProject.getGroupIDs().add(g.getID());

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
        if (!ApplicationWideData.getManualSync()) {
            service.addNewGroup(g, userID, responseCallback);
        }
        else{
            LocalDataSaver.addNewSelectable(g, "Group");
            LocalDataSaver.addUpdatedSelectable(selectedProject, "Project");
        }


        LocalDataSaver.saveGroup(g);
        LocalDataSaver.saveProject(selectedProject);
//        sb.append("{\"doc\": {");
//        sb.append(project.getGroupString());
//        sb.append("}}");
        //service.updateProject(project.getID(), sb.toString(), responseCallback2);


    }



    public void addNewLocation(final Location l) {
        if (!ApplicationWideData.manualSnyc) {

            Callback<Response> responseCallback = new Callback<Response>() {
                @Override
                public void success(Response response, Response response2) {
                    Toast.makeText(context, "Successful adding of new location: " + l.getName(), Toast.LENGTH_LONG).show();
                    context.refreshLists();
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e("RetrofitError", error.getMessage());
                }
            };

            service.addNewLocation(l, userID, responseCallback);
        }
        else{
            LocalDataSaver.addNewSelectable(l, "Location");
        }
        LocalDataSaver.saveLocation(l);

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
        if (!ApplicationWideData.manualSnyc) {
            Callback<Response> responseCallback = new Callback<Response>() {
                @Override
                public void success(Response response, Response response2) {
                    Toast.makeText(context, "Successful adding of new note: " + note.getTitle(), Toast.LENGTH_LONG).show();
                    context.refreshLists();
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e("RetrofitError", error.getMessage());
                }
            };
            service.addNewNote(note, userID, responseCallback);
        }
        else{
            LocalDataSaver.addNewSelectable(note, "Note");
        }
        LocalDataSaver.saveNote(note);
    }

    public void addNewShipment(final Shipment l) {
        if (!ApplicationWideData.manualSnyc) {
            Callback<Response> responseCallback = new Callback<Response>() {
                @Override
                public void success(Response response, Response response2) {
                    Toast.makeText(context, "Successful adding of new shipment: " + l.getName(), Toast.LENGTH_LONG).show();
                    context.refreshLists();
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e("RetrofitError", error.getMessage());
                }
            };
            service.addNewShipment(l, userID, responseCallback);
        }
        else{
            LocalDataSaver.addNewSelectable(l, "Shipment");
        }
        LocalDataSaver.saveShipment(l);
    }
    public void addNewChecklist(final Checklist checklist) {
        Random rand = new Random();
        long i = rand.nextInt(90000) + 10000;
        i += 700000;
        checklist.setID(i);
        checklist.setItemIDs();
        if (!ApplicationWideData.manualSnyc) {
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
        else{
            LocalDataSaver.addNewSelectable(checklist, "Checklist");
        }
        LocalDataSaver.saveChecklist(checklist);
    }
    public void addNewMessageThread(final MessageThread m){

        Random rand = new Random();
        long i = rand.nextInt(90000) + 10000;
        i += 700000;
        m.setID(i);
        m.setItemIDs();
        if (!ApplicationWideData.manualSnyc) {
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
            Log.d("ED", "inside of addNewMessageThread");
            service.addNewThread(m, userID, responseCallback);
        }
        else{
            LocalDataSaver.addNewSelectable(m, "Message Thread");
        }
        LocalDataSaver.saveMessageThread(m);


    }
    public void addNewMessage(String message, String userID){
        Log.wtf("s40", userID);
        MessageThread.Message message1 = new MessageThread.Message(message, userID);
        Log.wtf("s40", message1.getDateTimeModified());
        message1 = getSelectedMessageThread().addBuildNewMessage(message1);
        //Log.wtf("s40-6", message1.)
        Log.wtf("s40-4", getSelectedMessageThread().getID() + "");
        Log.wtf("s40-3", message1.getID() + "");
        if (!ApplicationWideData.manualSnyc) {
            Callback<Response> addResponse = new Callback<Response>() {
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

            service.addNewMessage(getSelectedMessageThread().getID() + "", message1, userID, addResponse);
        }
        else{
            LocalDataSaver.addNewSelectable(message1, "Message");
        }
        LocalDataSaver.saveMessage(message1);
    }
    public void hideProject() {
        String hideOrShow = "hide";
        if(selectedProject.isHidden())
            hideOrShow = "show";
        if (!ApplicationWideData.manualSnyc) {
            Callback<Response> hideResponse = new Callback<Response>() {
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
            service.changeVisibilityProject(selectedProject.getID() + "", hideOrShow, userID, hideResponse);

        }
        else{
            LocalDataSaver.addUpdatedSelectable(selectedProject, "Project");
        }
        LocalDataSaver.saveProject(selectedProject);



        //hide("project", selectedProject.getID() + "", hideResponse);
    }



    public void hideGroup() {
        String hideOrShow = "hide";
        if(selectedGroup.isHidden())
            hideOrShow = "show";
        if (!ApplicationWideData.manualSnyc) {
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

        service.changeVisibilityGroup(selectedGroup.getID() + "", hideOrShow, userID, hideResponse);

        }
        else{
            LocalDataSaver.addUpdatedSelectable(selectedGroup, "Group");
        }
        LocalDataSaver.saveGroup(selectedGroup);
//        service.hide("group", selectedGroup.getID() + "", hideResponse);
    }


    public void hideChecklist() {
        String hideOrShow = "hide";
        if(selectedChecklist.isHidden())
            hideOrShow = "show";
        if (!ApplicationWideData.manualSnyc) {
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

        service.changeVisibilityChecklist(selectedChecklist.getID() + "", hideOrShow, userID, hideResponse);

        }
        else{
            LocalDataSaver.addUpdatedSelectable(selectedChecklist, "Checklist");
        }
        LocalDataSaver.saveChecklist(selectedChecklist);
    }


    public void hideLocation() {
        String hideOrShow = "hide";
        if(selectedLocation.isHidden())
            hideOrShow = "show";
        if (!ApplicationWideData.manualSnyc) {
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

        service.changeVisibilityLocation(selectedLocation.getID() + "", hideOrShow, userID, hideResponse);
//        service.hide("location", selectedLocation.getID()+"", hideResponse);
        }
        else{
            LocalDataSaver.addUpdatedSelectable(selectedLocation, "Location");
        }
        LocalDataSaver.saveLocation(selectedLocation);
    }


    public void hideNote() {
        String hideOrShow = "hide";
        if(selectedNote.isHidden())
            hideOrShow = "show";
        if (!ApplicationWideData.manualSnyc) {
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

        service.changeVisibilityNote(selectedNote.getID() + "", hideOrShow, userID, hideResponse);
        }
        else{
            LocalDataSaver.addUpdatedSelectable(selectedNote, "Note");
        }
        LocalDataSaver.saveNote(selectedNote);
//        service.hide("note", selectedNote.getID() + "", hideResponse);
    }

    public void hidePerson() {
        String hideOrShow = "hide";
        if(selectedPerson.isHidden())
            hideOrShow = "show";
        if (!ApplicationWideData.manualSnyc) {
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

        service.changeVisibilityPerson(selectedPerson.getID() + "", hideOrShow, userID, hideResponse);
        }
        else{
            LocalDataSaver.addUpdatedSelectable(selectedPerson, "Person");
        }
        LocalDataSaver.savePerson(selectedPerson);
//        service.hide("person", selectedPerson.getID()+"", hideResponse);
    }


    public void hideShipment() {
        String hideOrShow = "hide";
        if(selectedShipment.isHidden())
            hideOrShow = "show";
        if (!ApplicationWideData.manualSnyc) {
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
        service.changeVisibilityShipment(selectedShipment.getID() + "", hideOrShow, userID, hideResponse);
        }
        else{
            LocalDataSaver.addUpdatedSelectable(selectedShipment, "Shipment");
        }
        LocalDataSaver.saveShipment(selectedShipment);
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
            service.resolveConflict(type, s.getID() + "", ((Project) s).toJSON(), hideResponse);
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
