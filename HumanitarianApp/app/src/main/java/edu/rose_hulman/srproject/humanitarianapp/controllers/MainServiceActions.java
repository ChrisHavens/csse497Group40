package edu.rose_hulman.srproject.humanitarianapp.controllers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.List;
import java.util.Random;

import edu.rose_hulman.srproject.humanitarianapp.localdata.LocalDataSaver;
import edu.rose_hulman.srproject.humanitarianapp.models.Checklist;
import edu.rose_hulman.srproject.humanitarianapp.models.Group;
import edu.rose_hulman.srproject.humanitarianapp.models.Location;
import edu.rose_hulman.srproject.humanitarianapp.models.Note;
import edu.rose_hulman.srproject.humanitarianapp.models.Person;
import edu.rose_hulman.srproject.humanitarianapp.models.Project;
import edu.rose_hulman.srproject.humanitarianapp.models.Roles;
import edu.rose_hulman.srproject.humanitarianapp.models.Shipment;
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
    private long parentID;
    private List<Project> storedProjects;
    private boolean isFromProject=false;
    private Context context;
    public MainServiceActions(Context context){
        this.service=new NonLocalDataService();
        this.context=context;
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

    public void saveNote(String title, String body){
        body=body.replaceAll("\n", "\\\n");
        this.selectedNote.setTitle(title);
        this.selectedNote.setBody(body);
        service.updateNote(this.selectedNote.getID(), title, body, new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {

            }

            @Override
            public void failure(RetrofitError error) {
                Log.w("RetrofitError", error.getMessage());
            }
        });
    }

    public void addNewPerson(final String name, String phone, String email, Roles.PersonRoles role) {
        long projectID=selectedProject.getId();
        long groupID=-1;
        if (getSelectedGroup()!=null) {
            groupID = getSelectedGroup().getId();
        }
        Person p=new Person(name, phone, email);
        edu.rose_hulman.srproject.humanitarianapp.models.Person.PersonLocation location=new edu.rose_hulman.srproject.humanitarianapp.models.Person.PersonLocation();
        location.setName("Omega 4 Relay");
        location.setTime("2185-04-05 14:45");
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
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("RetrofitError", error.getMessage());
            }
        };
        //NonLocalDataService service=new NonLocalDataService();
        service.addNewPerson(p, responseCallback);
    }
    

    public void addNewProject(final String name) {
//        Random rand=new Random();
//        long i= rand.nextInt(90000)+10000;
//        i+=100000;
//        Project p= new Project(name, i);
        Project p = new Project(name);


        Callback<Response> responseCallback = new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                Toast.makeText(context, "Successful adding of new project: " + name, Toast.LENGTH_LONG).show();
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
        service.addNewProject(p, responseCallback);
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
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("RetrofitError", error.getMessage());
            }
        };

        service.addNewGroup(g, responseCallback);
        Callback<Response> responseCallback2 = new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                Toast.makeText(context, "Successful editing of project", Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("RetrofitError", error.getMessage());
            }
        };
        service.addNewProject(selectedProject, responseCallback);
//        sb.append("{\"doc\": {");
//        sb.append(project.getGroupString());
//        sb.append("}}");
        //service.updateProject(project.getId(), sb.toString(), responseCallback2);


    }



    public void addNewLocation(final Location l) {

        Callback<Response> responseCallback=new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                Toast.makeText(context, "Successful adding of new location: "+l.getName(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("RetrofitError", error.getMessage());
            }
        };

        service.addNewLocation(l, responseCallback);

    }



    public void addNewNote(String name, String contents){
        Random rand=new Random();
        long i= rand.nextInt(90000)+10000;
        i+=500000;
        final Note note=new Note(i);
        note.setParentID(selectedGroup.getId());
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
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("RetrofitError", error.getMessage());
            }
        };
        service.addNewNote(note, responseCallback);
    }

    public void addNewShipment(final Shipment l) {
        Callback<Response> responseCallback=new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                Toast.makeText(context, "Successful adding of new shipment: "+l.getName(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("RetrofitError", error.getMessage());
            }
        };
        service.addNewShipment(l, responseCallback);
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
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("RetrofitError", error.getMessage());
            }
        };

        service.addNewChecklist(checklist, responseCallback);
    }
    public void hideProject() {
        Callback<Response> hideResponse=new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                Toast.makeText(context, "Successfully hid project", Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.wtf("s40", error.getMessage());

            }
        };
        service.service.changeVisibilityProject(selectedProject.getId() + "", "hide",hideResponse);
        //hide("project", selectedProject.getId() + "", hideResponse);
    }



    public void hideGroup() {
        Callback<Response> hideResponse=new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                Toast.makeText(context, "Successfully hid group", Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.wtf("s40", error.getMessage());

            }
        };
        service.service.changeVisibilityGroup(selectedGroup.getId()+"", "hide", hideResponse);
//        service.hide("group", selectedGroup.getId() + "", hideResponse);
    }


    public void hideChecklist() {
        Callback<Response> hideResponse=new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                Toast.makeText(context, "Successfully hid checklist", Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.wtf("s40", error.getMessage());

            }
        };
        service.service.changeVisibilityChecklist(selectedChecklist.getID() + "", "hide", hideResponse);
    }


    public void hideLocation() {
        Callback<Response> hideResponse=new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                Toast.makeText(context, "Successfully hid location", Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.wtf("s40", error.getMessage());

            }
        };
        service.service.changeVisibilityLocation(selectedLocation.getID()+"", "hide", hideResponse);
//        service.hide("location", selectedLocation.getID()+"", hideResponse);
    }


    public void hideNote() {
        Callback<Response> hideResponse=new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                Toast.makeText(context, "Successfully hid note", Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.wtf("s40", error.getMessage());

            }
        };
        service.service.changeVisibilityNote(selectedNote.getID()+"", "hide", hideResponse);
//        service.hide("note", selectedNote.getID() + "", hideResponse);
    }

    public void hidePerson() {
        Callback<Response> hideResponse=new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                Toast.makeText(context, "Successfully hid person", Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.wtf("s40", error.getMessage());

            }
        };
        service.service.changeVisibilityPerson(selectedPerson.getID()+"", "show", hideResponse);

//        service.hide("person", selectedPerson.getID()+"", hideResponse);
    }


    public void hideShipment() {
        Callback<Response> hideResponse=new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                Toast.makeText(context, "Successfully hid shipment", Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.wtf("s40", error.getMessage());

            }
        };
        service.service.changeVisibilityShipment(selectedShipment.getID()+"", "hide", hideResponse);
//        service.hide("shipment", selectedShipment.getID() + "", hideResponse);

    }
    public void editChecklist(final Checklist c){
        Callback<Response> responseCallback=new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                Toast.makeText(context, "Successful editing of checklist: "+c.getTitle(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("RetrofitError", error.getMessage());
            }
        };

        service.addNewChecklist(c, responseCallback);
    }
}
