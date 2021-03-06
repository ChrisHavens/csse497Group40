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
        LocalDataSaver.saveNote(selectedNote);
        if (!ApplicationWideData.manualSnyc) {
            service.updateNote(this.selectedNote, userID, new Callback<Response>() {
                @Override
                public void success(Response response, Response response2) {
                    context.refreshLists();
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.w("RetrofitError", "Actions: SaveNote: "+error.getMessage());
                }
            });
        }
        else{
            LocalDataSaver.addUpdatedSelectable(selectedNote, "Note");
            context.refreshLists();
        }

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
        LocalDataSaver.savePerson(p);
        if (!ApplicationWideData.manualSnyc) {
            Callback<Response> responseCallback = new Callback<Response>() {
                @Override
                public void success(Response response, Response response2) {
                    Toast.makeText(context, "Successful adding of new person: " + name, Toast.LENGTH_LONG).show();
                    context.refreshLists();
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e("RetrofitError", "Actions: AddNewPerson: "+error.getMessage());
                }
            };
            //NonLocalDataService service=new NonLocalDataService();
            service.addNewPerson(p, userID, responseCallback);
        }
        else{
            LocalDataSaver.addNewSelectable(p, "Person");
            context.refreshLists();
        }


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
        LocalDataSaver.savePerson(p);
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
                        Log.e("RetrofitError", "Actions: RemovePersonFromProjectOrGroup: "+error.getMessage());
                        Log.e("RetrofitError", "Actions: RemovePersonFromProjectOrGroup: "+error.getUrl());
                    }
                };
                service.removePersonFromGroup(p.getID() + "", groupID + "", userID + "", responseCallback);
            }
            else{
                LocalDataSaver.addUpdatedSelectable(p, "Person");
            }

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
                        Log.e("RetrofitError", "Actions: RemovePersonFromProjectOrGroup: "+error.getMessage());
                        Log.e("RetrofitError", "Actions: RemovePersonFromProjectOrGroup: "+error.getUrl());
                    }
                };
                service.removePersonFromProject(p.getID() + "", projectID + "", userID + "", responseCallback);
            }
            else{
                LocalDataSaver.addUpdatedSelectable(p, "Person");
                context.refreshLists();
            }
            LocalDataSaver.savePerson(p);
        }
    }
    private void addPersonToProject(final Person p, final long projectID){


        p.addProjectID(projectID);
        LocalDataSaver.savePerson(p);
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
                    Log.e("RetrofitError", "Actions: AddPersonToProject: "+error.getMessage());
                    Log.e("RetrofitError", "Actions: AddPersonToProject: "+error.getUrl());
                }
            };
//        //NonLocalDataService service=new NonLocalDataService();
            service.addPersonToProject(p.getID() + "", projectID + "", userID, responseCallback);
        }
        else{
            LocalDataSaver.addUpdatedSelectable(p, "Person");
            context.refreshLists();
        }

    }
    private void addPersonToGroup(final Person p, long projectID, final long groupID){
        if (!p.isInProject(projectID)){
            addPersonToProject(p, projectID);
        }
        p.addGroupID(groupID);
        LocalDataSaver.savePerson(p);
        if (!ApplicationWideData.manualSnyc) {
            Callback<Response> responseCallback = new Callback<Response>() {
                @Override
                public void success(Response response, Response response2) {
                    Toast.makeText(context, "Successful adding of new person: " + p.getName() + " to group " + groupID, Toast.LENGTH_SHORT).show();
                    context.refreshLists();
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e("RetrofitError", "Actions: AddPersonToGroup: "+error.getMessage());
                }
            };
            service.addPersonToGroup(p.getID() + "", groupID + "", userID, responseCallback);
        }
        else{
            LocalDataSaver.addUpdatedSelectable(p, "Person");
            context.refreshLists();
        }

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
                Log.e("RetrofitError", "Actions: AddNewProject: "+error.getMessage());
            }
        };

//        boolean success = LocalDataSaver.addProject(p);
//        if (success) {
//            Toast.makeText(context, "Successful adding of new project: " + name + " to local database", Toast.LENGTH_LONG).show();
//        }
        if (!ApplicationWideData.getManualSync()) {
            service.addNewProject(p, userID, responseCallback);
        }
        else{
            LocalDataSaver.addNewSelectable(p, "Project");
            context.refreshLists();
        }
            LocalDataSaver.saveProject(p);
            Log.wtf("Added project", "to added table");


            Log.wtf("Size of added table", LocalDataRetriver.getAllAdded().size() + "");

    }
    public void updateProject(final Project p){
        LocalDataSaver.saveProject(p);
        setSelectedProject(p);
        if (!ApplicationWideData.manualSnyc) {
            Toast.makeText(context, "Auto Sync On!", Toast.LENGTH_SHORT).show();
            NonLocalDataService service = new NonLocalDataService();
            StringBuilder sb = new StringBuilder();
            sb.append("{\"doc\":{\"name\":\""+p.getName()+"\"}}");
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
                    Log.e("s40 RetroFitError", error.getUrl());
                    //Log.e("s40 RetroFitE")
                }
            });
        } else {
            Toast.makeText(context, "Adding updated item to table: ", Toast.LENGTH_SHORT).show();
            LocalDataSaver.addUpdatedSelectable(p, "Project");
            context.refreshLists();
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
        LocalDataSaver.saveGroup(g);
        LocalDataSaver.saveProject(selectedProject);

        Callback<Response> responseCallback=new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                Toast.makeText(context, "Successful adding of new group: " + name, Toast.LENGTH_LONG).show();
                context.refreshLists();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("RetrofitError", "Actions: AddNewGroup: "+error.getMessage());
            }
        };
        if (!ApplicationWideData.getManualSync()) {
            service.addNewGroup(g, userID, responseCallback);
        }
        else{
            LocalDataSaver.addNewSelectable(g, "Group");
            LocalDataSaver.addUpdatedSelectable(selectedProject, "Project");
            context.refreshLists();
        }



//        sb.append("{\"doc\": {");
//        sb.append(project.getGroupString());
//        sb.append("}}");
        //service.updateProject(project.getID(), sb.toString(), responseCallback2);


    }



    public void addNewLocation(final Location l) {
        LocalDataSaver.saveLocation(l);
        if (!ApplicationWideData.manualSnyc) {

            Callback<Response> responseCallback = new Callback<Response>() {
                @Override
                public void success(Response response, Response response2) {
                    Toast.makeText(context, "Successful adding of new location: " + l.getName(), Toast.LENGTH_LONG).show();
                    context.refreshLists();
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e("RetrofitError", "Actions: AddNewLocation: "+error.getMessage());
                    Log.e("RetrofitError", "Actions: AddNewLocation: "+error.getUrl());
                }
            };

            service.addNewLocation(l, userID, responseCallback);
        }
        else{
            LocalDataSaver.addNewSelectable(l, "Location");
            context.refreshLists();
        }


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
        LocalDataSaver.saveNote(note);
        if (!ApplicationWideData.manualSnyc) {
            Callback<Response> responseCallback = new Callback<Response>() {
                @Override
                public void success(Response response, Response response2) {
                    Toast.makeText(context, "Successful adding of new note: " + note.getTitle(), Toast.LENGTH_LONG).show();
                    context.refreshLists();
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e("RetrofitError", "Actions: AddNewNote: "+error.getMessage());
                }
            };
            service.addNewNote(note, userID, responseCallback);
        }
        else{
            LocalDataSaver.addNewSelectable(note, "Note");
            context.refreshLists();
        }

    }

    public void addNewShipment(final Shipment l) {
        LocalDataSaver.saveShipment(l);
        if (!ApplicationWideData.manualSnyc) {
            Callback<Response> responseCallback = new Callback<Response>() {
                @Override
                public void success(Response response, Response response2) {
                    Toast.makeText(context, "Successful adding of new shipment: " + l.getName(), Toast.LENGTH_LONG).show();
                    context.refreshLists();
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e("RetrofitError", "Actions: AddNewShipment: "+error.getMessage());
                }
            };
            service.addNewShipment(l, userID, responseCallback);
        }
        else{
            LocalDataSaver.addNewSelectable(l, "Shipment");
            context.refreshLists();
        }

    }
    public void addNewChecklist(final Checklist checklist) {
        Random rand = new Random();
        long i = rand.nextInt(90000) + 10000;
        i += 700000;
        checklist.setID(i);
        checklist.setItemIDs();
        LocalDataSaver.saveChecklist(checklist);
        if (!ApplicationWideData.manualSnyc) {
            Callback<Response> responseCallback = new Callback<Response>() {
                @Override
                public void success(Response response, Response response2) {
                    Toast.makeText(context, "Successful adding of new checklist: " + checklist.getTitle(), Toast.LENGTH_LONG).show();
                    context.refreshLists();
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e("RetrofitError", "Actions: AddNewChecklist: "+error.getMessage());
                }
            };

            service.addNewChecklist(checklist, userID, responseCallback);
        }
        else{
            LocalDataSaver.addNewSelectable(checklist, "Checklist");
            context.refreshLists();
        }

    }
    public void addNewMessageThread(final MessageThread m){

        Random rand = new Random();
        long i = rand.nextInt(90000) + 10000;
        i += 700000;
        m.setID(i);
        m.setItemIDs();
        LocalDataSaver.saveMessageThread(m);
        if (!ApplicationWideData.manualSnyc) {
            Callback<Response> responseCallback = new Callback<Response>() {
                @Override
                public void success(Response response, Response response2) {
                    Toast.makeText(context, "Successful adding of new thread: " + m.getTitle(), Toast.LENGTH_LONG).show();
                    context.refreshLists();
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e("RetrofitError", "Actions: AddNewThread: "+error.getMessage());
                }
            };
            //Log.d("ED", "inside of addNewMessageThread");
            service.addNewThread(m, userID, responseCallback);
        }
        else{
            LocalDataSaver.addNewSelectable(m, "Message Thread");
            context.refreshLists();
        }



    }
    public void addNewMessage(String message, String userID){
        //Log.wtf("s40", userID);
        MessageThread.Message message1 = new MessageThread.Message(message, userID);
        //Log.wtf("s40", message1.getDateTimeModified());
        message1 = getSelectedMessageThread().addBuildNewMessage(message1);
        //Log.wtf("s40-6", message1.)
        //Log.wtf("s40-4", getSelectedMessageThread().getID() + "");
        //Log.wtf("s40-3", message1.getID() + "");
        LocalDataSaver.saveMessage(message1);
        if (!ApplicationWideData.manualSnyc) {
            Callback<Response> addResponse = new Callback<Response>() {
                @Override
                public void success(Response response, Response response2) {
                    Toast.makeText(context, "Message sent", Toast.LENGTH_SHORT).show();
                    context.refreshLists();
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e("Retrofit Error", error.getUrl());
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            };

            service.addNewMessage(getSelectedMessageThread().getID() + "", message1, userID, addResponse);
        }
        else{
            LocalDataSaver.addNewSelectable(message1, "Message");
            context.refreshLists();
        }

    }
    public void hideProject() {
        String hideOrShow = "hide";
        boolean curr=selectedProject.isHidden();
        selectedProject.setHidden(!curr);
        if(curr)
            hideOrShow = "show";
        LocalDataSaver.saveProject(selectedProject);

        if (!ApplicationWideData.manualSnyc) {
            Callback<Response> hideResponse = new Callback<Response>() {
                @Override
                public void success(Response response, Response response2) {
                    //Toast.makeText(context, "Successfully hid project", Toast.LENGTH_LONG).show();
                    context.refreshLists();
                }

                @Override
                public void failure(RetrofitError error) {
                    //Log.wtf("s40", error.getMessage());

                }
            };
            service.changeVisibilityProject(selectedProject.getID() + "", hideOrShow, userID, hideResponse);

        }
        else{
            LocalDataSaver.addUpdatedSelectable(selectedProject, "Project");
            context.refreshLists();
        }




        //hide("project", selectedProject.getID() + "", hideResponse);
    }



    public void hideGroup() {
        String hideOrShow = "hide";
        boolean curr=selectedGroup.isHidden();
        selectedGroup.setHidden(!curr);
        if(selectedGroup.isHidden())
            hideOrShow = "show";
        LocalDataSaver.saveGroup(selectedGroup);
        if (!ApplicationWideData.manualSnyc) {
        Callback<Response> hideResponse=new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                //Toast.makeText(context, "Successfully hid group", Toast.LENGTH_LONG).show();
                context.refreshLists();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("s40", error.getMessage());

            }
        };

        service.changeVisibilityGroup(selectedGroup.getID() + "", hideOrShow, userID, hideResponse);

        }
        else{
            LocalDataSaver.addUpdatedSelectable(selectedGroup, "Group");
            context.refreshLists();
        }

//        service.hide("group", selectedGroup.getID() + "", hideResponse);
    }


    public void hideChecklist() {
        String hideOrShow = "hide";
        boolean curr=selectedChecklist.isHidden();
        selectedChecklist.setHidden(!curr);
        if(selectedChecklist.isHidden())
            hideOrShow = "show";
        LocalDataSaver.saveChecklist(selectedChecklist);
        if (!ApplicationWideData.manualSnyc) {
        Callback<Response> hideResponse=new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                //Toast.makeText(context, "Successfully hid checklist", Toast.LENGTH_LONG).show();
                context.refreshLists();
            }

            @Override
            public void failure(RetrofitError error) {
                //Log.wtf("s40", error.getMessage());

            }
        };

        service.changeVisibilityChecklist(selectedChecklist.getID() + "", hideOrShow, userID, hideResponse);

        }
        else{
            LocalDataSaver.addUpdatedSelectable(selectedChecklist, "Checklist");
            context.refreshLists();
        }

    }


    public void hideLocation() {
        String hideOrShow = "hide";
        boolean curr=selectedLocation.isHidden();
        selectedLocation.setHidden(!curr);
        if(selectedLocation.isHidden())
            hideOrShow = "show";
        LocalDataSaver.saveLocation(selectedLocation);
        if (!ApplicationWideData.manualSnyc) {
        Callback<Response> hideResponse=new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                //Toast.makeText(context, "Successfully hid location", Toast.LENGTH_LONG).show();
                context.refreshLists();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("s40", error.getMessage());

            }
        };

        service.changeVisibilityLocation(selectedLocation.getID() + "", hideOrShow, userID, hideResponse);
//        service.hide("location", selectedLocation.getID()+"", hideResponse);
        }
        else{
            LocalDataSaver.addUpdatedSelectable(selectedLocation, "Location");
            context.refreshLists();
        }

    }


    public void hideNote() {
        String hideOrShow = "hide";
        boolean curr=selectedNote.isHidden();
        selectedNote.setHidden(!curr);
        if(selectedNote.isHidden())
            hideOrShow = "show";
        LocalDataSaver.saveNote(selectedNote);
        if (!ApplicationWideData.manualSnyc) {
        Callback<Response> hideResponse=new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                //Toast.makeText(context, "Successfully hid note", Toast.LENGTH_LONG).show();
                context.refreshLists();
            }

            @Override
            public void failure(RetrofitError error) {
                //Log.wtf("s40", error.getMessage());

            }

        };

        service.changeVisibilityNote(selectedNote.getID() + "", hideOrShow, userID, hideResponse);
        }
        else{
            LocalDataSaver.addUpdatedSelectable(selectedNote, "Note");
            context.refreshLists();
        }

//        service.hide("note", selectedNote.getID() + "", hideResponse);
    }

    public void hidePerson() {
        String hideOrShow = "hide";
        boolean curr=selectedPerson.isHidden();
        selectedPerson.setHidden(!curr);
        if(selectedPerson.isHidden())
            hideOrShow = "show";
        LocalDataSaver.savePerson(selectedPerson);
        if (!ApplicationWideData.manualSnyc) {
        Callback<Response> hideResponse=new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                //Toast.makeText(context, "Successfully hid person", Toast.LENGTH_LONG).show();
                context.refreshLists();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("s40", error.getMessage());

            }
        };

        service.changeVisibilityPerson(selectedPerson.getID() + "", hideOrShow, userID, hideResponse);
        }
        else{
            LocalDataSaver.addUpdatedSelectable(selectedPerson, "Person");
            context.refreshLists();
        }

//        service.hide("person", selectedPerson.getID()+"", hideResponse);
    }


    public void hideShipment() {
        String hideOrShow = "hide";
        boolean curr=selectedShipment.isHidden();
        selectedShipment.setHidden(!curr);
        if(selectedShipment.isHidden())
            hideOrShow = "show";
        LocalDataSaver.saveShipment(selectedShipment);
        if (!ApplicationWideData.manualSnyc) {
        Callback<Response> hideResponse=new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                //Toast.makeText(context, "Successfully hid shipment", Toast.LENGTH_LONG).show();
                context.refreshLists();
            }

            @Override
            public void failure(RetrofitError error) {
                //Log.wtf("s40", error.getMessage());

            }
        };
        service.changeVisibilityShipment(selectedShipment.getID() + "", hideOrShow, userID, hideResponse);
        }
        else{
            LocalDataSaver.addUpdatedSelectable(selectedShipment, "Shipment");
            context.refreshLists();
        }

//        service.hide("shipment", selectedShipment.getID() + "", hideResponse);

    }
    public void editChecklist(final Checklist c){
        setSelectedChecklist(c);
        LocalDataSaver.saveChecklist(c);
        if (!ApplicationWideData.manualSnyc) {
            Callback<Response> responseCallback = new Callback<Response>() {
                @Override
                public void success(Response response, Response response2) {
                    Toast.makeText(context, "Successful editing of checklist: " + c.getTitle(), Toast.LENGTH_LONG).show();
                    context.refreshLists();
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e("RetrofitError", "Actions: EditChecklist: "+error.getMessage());
                }
            };


            service.addNewChecklist(c, userID, responseCallback);
        }else{
            LocalDataSaver.addUpdatedSelectable(c, "Checklist");
        }

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
                Log.e("s40R", error.getMessage());

            }
        };
        String type="";
        if (s instanceof Project){
            type="project";
            service.resolveConflict(type, s.getID() + "", ((Project) s).toJSON(), hideResponse);
        }
        else if (s instanceof Group){
            type="group";
            service.resolveConflict(type, s.getID() + "", ((Group) s).toJSON(), hideResponse);
        }
        else if (s instanceof Person){
            type="person";
            service.resolveConflict(type, s.getID() + "", ((Person) s).toJSON(), hideResponse);
        }
        else if (s instanceof Note){
            type="note";
            service.resolveConflict(type, s.getID() + "", ((Note) s).toJSON(), hideResponse);
        }
        else if (s instanceof Shipment){
            type="shipment";
            service.resolveConflict(type, s.getID() + "", ((Shipment) s).toJSON(), hideResponse);
        }

    }
    public void updateGroup(final Group group){
        setSelectedGroup(group);
        LocalDataSaver.saveGroup(group);
        if (!ApplicationWideData.manualSnyc) {

            StringBuilder sb = new StringBuilder();
            sb.append("{\"doc\":" + group.toJSON() + "}");
            service.updateGroup(group, sb.toString(), userID, new Callback<Response>() {
                @Override
                public void success(Response response, Response response2) {
                    //Log.wtf("s40", "Successful edit of group " + group.getName());
                    context.refreshLists();
                    //Toast.makeText(getActivity(), "Successful edit of project "+p.getName(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e("s40 RetroFitError", error.getMessage());
                }
            });
        }
        else{
            LocalDataSaver.addUpdatedSelectable(group, "Group");
            context.refreshLists();
        }

    }
    public void updateLocation(final Location location){
        setSelectedLocation(location);
        LocalDataSaver.saveLocation(location);
        if (!ApplicationWideData.manualSnyc) {
            String doc = "{\"doc\":" + location.toJSON() + "}";
            service.updateLocation(location, doc, userID, new Callback<Response>() {
                @Override
                public void success(Response response, Response response2) {
                    //Log.wtf("s40", "Successful edit of location " + location.getName());
                    context.refreshLists();
                    //Toast.makeText(getActivity(), "Successful edit of project "+p.getName(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e("s40 RetroFitError", error.getMessage());
                }
            });
        }else{
            LocalDataSaver.addUpdatedSelectable(location, "Location");
            context.refreshLists();
        }


    }
    public void updatePerson(final Person person){
        setSelectedPerson(person);
        LocalDataSaver.savePerson(person);
        if (!ApplicationWideData.manualSnyc) {
            String json = "{\"doc\":" + person.toJSON() + "}";
            service.updatePerson(person, json, userID, new Callback<Response>() {
                @Override
                public void success(Response response, Response response2) {
                    //Log.wtf("s40", "Successful edit of person " + person.getName());
                    context.refreshLists();
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e("s40 RetroFitError", error.getMessage());
                }
            });
        }
        else{
            LocalDataSaver.addUpdatedSelectable(person, "Person");
            context.refreshLists();
        }

    }


//    public void resolveConflicts(List<Conflict> conflicts){
//        //resolved=conflicts;
//        //WHATEVERS
//
//    }

}
