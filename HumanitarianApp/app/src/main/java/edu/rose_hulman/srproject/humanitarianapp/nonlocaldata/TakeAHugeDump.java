package edu.rose_hulman.srproject.humanitarianapp.nonlocaldata;

import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.rose_hulman.srproject.humanitarianapp.localdata.ApplicationWideData;
import edu.rose_hulman.srproject.humanitarianapp.localdata.LocalDataSaver;
import edu.rose_hulman.srproject.humanitarianapp.models.*;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by daveyle on 5/17/2016.
 */
public class TakeAHugeDump {
    NonLocalDataService service=new NonLocalDataService();
    String userID;
    HashMap<Long, Project> projectList = new HashMap<>();
    HashMap<Long, Group> groupList = new HashMap<>();
    HashMap<Long, Checklist> checklistList = new HashMap<>();
    HashMap<Long, Location> locationList = new HashMap<>();
    HashMap<Long, MessageThread> threadList = new HashMap<>();
    HashMap<Long, Note> noteList = new HashMap<>();
    HashMap<Long, Person> personList = new HashMap<>();
    HashMap<Long, Shipment> shipmentList = new HashMap<>();
    public TakeAHugeDump(long userID){
        this.userID=userID+"";
    }
    private void getAllProjects(){
        service.service.getProjectList(userID, new ProjectListCallback());
    }
    private class ProjectListCallback implements Callback<Response> {



        @Override
        public void success(Response response, Response response2) {
            ObjectMapper mapper = new ObjectMapper();



            TypeReference<HashMap<String, Object>> typeReference =
                    new TypeReference<HashMap<String, Object>>() {
                    };
            try {
                HashMap<String, Object> o = mapper.readValue(response.getBody().in(), typeReference);
                ArrayList<HashMap<String, Object>> list = (ArrayList) ((HashMap) o.get("hits")).get("hits");
                for (HashMap<String, Object> map : list) {
                    HashMap<String, Object> source = (HashMap) map.get("_source");

                    Project p=Project.parseJSON(Long.parseLong(((String) map.get("_id"))), source);


                    if(!projectList.containsKey(p.getID())) {
                        projectList.put(p.getID(), p);
                    }
                    service.service.getGroupList(userID, false, p.getID()+"", new GroupListCallback());

                    service.service.getPersonListByProjectID(false, p.getID() + "", new PersonListCallback());
                    service.service.getLocationListByProjectID(false, p.getID()+"", new LocationListCallback());

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void failure(RetrofitError error) {
            Log.wtf("Error in Project List Dump", error.getMessage());
            Log.wtf("Error in Project List Dump", error.getUrl());
        }
    }
    private class GroupListCallback implements Callback<Response> {



        @Override
        public void success(Response response, Response response2) {
            ObjectMapper mapper = new ObjectMapper();



            TypeReference<HashMap<String, Object>> typeReference =
                    new TypeReference<HashMap<String, Object>>() {
                    };
            try {
                HashMap<String, Object> o = mapper.readValue(response.getBody().in(), typeReference);
                ArrayList<HashMap<String, Object>> list = (ArrayList) ((HashMap) o.get("hits")).get("hits");
                for (HashMap<String, Object> map : list) {
                    HashMap<String, Object> source = (HashMap) map.get("_source");

                    Group g=Group.parseJSON(Long.parseLong(((String) map.get("_id"))), source);


                    if(!groupList.containsKey(g.getID())) {
                        groupList.put(g.getID(), g);
                    }
                    service.service.getPersonListByGroupID(false, g.getID() + "", new PersonListCallback());
                    service.service.getNoteList(false, g.getID() + "", new NoteListCallback());
                    service.service.getChecklistList(false, g.getID() + "", new ChecklistListCallback());
                    service.service.getShipmentList(false, g.getID() + "", new ShipmentListCallback());
                    service.service.getThreadList(false, g.getID()+"", new ThreadListCallback());


                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void failure(RetrofitError error) {
            Log.wtf("Error in Group List Dump", error.getMessage());
            Log.wtf("Error in Group List Dump", error.getUrl());
        }
    }
    private class PersonListCallback implements Callback<Response> {



        @Override
        public void success(Response response, Response response2) {
            ObjectMapper mapper = new ObjectMapper();



            TypeReference<HashMap<String, Object>> typeReference =
                    new TypeReference<HashMap<String, Object>>() {
                    };
            try {
                HashMap<String, Object> o = mapper.readValue(response.getBody().in(), typeReference);
                ArrayList<HashMap<String, Object>> list = (ArrayList) ((HashMap) o.get("hits")).get("hits");
                for (HashMap<String, Object> map : list) {
                    HashMap<String, Object> source = (HashMap) map.get("_source");

                    Person p=Person.parseJSON(Long.parseLong(((String) map.get("_id"))), source);


                    if(!personList.containsKey(p.getID())) {
                        personList.put(p.getID(), p);
                    }

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void failure(RetrofitError error) {
            Log.wtf("Error in Person List Dump", error.getMessage());
            Log.wtf("Error in Person List Dump", error.getUrl());
        }
    }
    private class LocationListCallback implements Callback<Response> {



        @Override
        public void success(Response response, Response response2) {
            ObjectMapper mapper = new ObjectMapper();



            TypeReference<HashMap<String, Object>> typeReference =
                    new TypeReference<HashMap<String, Object>>() {
                    };
            try {
                HashMap<String, Object> o = mapper.readValue(response.getBody().in(), typeReference);
                ArrayList<HashMap<String, Object>> list = (ArrayList) ((HashMap) o.get("hits")).get("hits");
                for (HashMap<String, Object> map : list) {
                    HashMap<String, Object> source = (HashMap) map.get("_source");

                    Location l=Location.parseJSON(Long.parseLong(((String) map.get("_id"))), source);


                    if(!locationList.containsKey(l.getID())) {
                        locationList.put(l.getID(), l);
                    }

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void failure(RetrofitError error) {
            Log.wtf("Error in Location List Dump", error.getMessage());
            Log.wtf("Error in Location List Dump", error.getUrl());
        }
    }
    private class NoteListCallback implements Callback<Response> {



        @Override
        public void success(Response response, Response response2) {
            ObjectMapper mapper = new ObjectMapper();



            TypeReference<HashMap<String, Object>> typeReference =
                    new TypeReference<HashMap<String, Object>>() {
                    };
            try {
                HashMap<String, Object> o = mapper.readValue(response.getBody().in(), typeReference);
                ArrayList<HashMap<String, Object>> list = (ArrayList) ((HashMap) o.get("hits")).get("hits");
                for (HashMap<String, Object> map : list) {
                    HashMap<String, Object> source = (HashMap) map.get("_source");

                    Note n=Note.parseJSON(Long.parseLong(((String) map.get("_id"))), source);


                    if(!noteList.containsKey(n.getID())) {
                        noteList.put(n.getID(), n);
                    }

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void failure(RetrofitError error) {
            Log.wtf("Error in Note List Dump", error.getMessage());
            Log.wtf("Error in Note List Dump", error.getUrl());
        }
    }
    private class ChecklistListCallback implements Callback<Response> {



        @Override
        public void success(Response response, Response response2) {
            ObjectMapper mapper = new ObjectMapper();



            TypeReference<HashMap<String, Object>> typeReference =
                    new TypeReference<HashMap<String, Object>>() {
                    };
            try {
                HashMap<String, Object> o = mapper.readValue(response.getBody().in(), typeReference);
                ArrayList<HashMap<String, Object>> list = (ArrayList) ((HashMap) o.get("hits")).get("hits");
                for (HashMap<String, Object> map : list) {
                    HashMap<String, Object> source = (HashMap) map.get("_source");

                    Checklist c=Checklist.parseJSON(Long.parseLong(((String) map.get("_id"))), source);


                    if(!checklistList.containsKey(c.getID())) {
                        checklistList.put(c.getID(), c);
                    }

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void failure(RetrofitError error) {

        }
    }
    private class ShipmentListCallback implements Callback<Response> {



        @Override
        public void success(Response response, Response response2) {
            ObjectMapper mapper = new ObjectMapper();



            TypeReference<HashMap<String, Object>> typeReference =
                    new TypeReference<HashMap<String, Object>>() {
                    };
            try {
                HashMap<String, Object> o = mapper.readValue(response.getBody().in(), typeReference);
                ArrayList<HashMap<String, Object>> list = (ArrayList) ((HashMap) o.get("hits")).get("hits");
                for (HashMap<String, Object> map : list) {
                    HashMap<String, Object> source = (HashMap) map.get("_source");

                    Shipment c=Shipment.parseJSON(Long.parseLong(((String) map.get("_id"))), source);


                    if(!shipmentList.containsKey(c.getID())) {
                        shipmentList.put(c.getID(), c);
                    }

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void failure(RetrofitError error) {
            Log.wtf("Error in Shipment List Dump", error.getMessage());
            Log.wtf("Error in Shipment List Dump", error.getUrl());
        }
    }
    private class ThreadListCallback implements Callback<Response> {



        @Override
        public void success(Response response, Response response2) {
            ObjectMapper mapper = new ObjectMapper();



            TypeReference<HashMap<String, Object>> typeReference =
                    new TypeReference<HashMap<String, Object>>() {
                    };
            try {
                HashMap<String, Object> o = mapper.readValue(response.getBody().in(), typeReference);
                ArrayList<HashMap<String, Object>> list = (ArrayList) ((HashMap) o.get("hits")).get("hits");
                for (HashMap<String, Object> map : list) {
                    HashMap<String, Object> source = (HashMap) map.get("_source");

                    MessageThread c=MessageThread.parseJSON(Long.parseLong(((String) map.get("_id"))), source);


                    if(!threadList.containsKey(c.getID())) {
                        //threadList.put(c.getID(), c);
                        service.service.getMessagesList(c.getID() + "", null, null, null, new MessageListCallback(c));
                    }


                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void failure(RetrofitError error) {
            Log.wtf("Error in Thread List Dump", error.getMessage());
            Log.wtf("Error in Thread List Dump", error.getUrl());
        }
    }
    private class MessageListCallback implements Callback<Response> {
        private final MessageThread thread;
        public MessageListCallback(MessageThread thread){
            this.thread=thread;
        }



        @Override
        public void success(Response response, Response response2) {
            ObjectMapper mapper = new ObjectMapper();



            TypeReference<HashMap<String, Object>> typeReference =
                    new TypeReference<HashMap<String, Object>>() {
                    };
            try {
                HashMap<String, Object> o = mapper.readValue(response.getBody().in(), typeReference);
                ArrayList<HashMap<String, Object>> list = (ArrayList) ((HashMap) o.get("hits")).get("hits");
                List<MessageThread.Message> messages=new ArrayList<>();
                for (HashMap<String, Object> map : list) {
                    HashMap<String, Object> source = (HashMap) map.get("_source");

                    MessageThread.Message c=MessageThread.Message.parseJSON(Long.parseLong(((String) map.get("_id"))), source);


                    if (!messages.contains(c)){
                        messages.add(c);
                    }


                }
                thread.setItemList(messages);
                threadList.put(thread.getID(), thread);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void failure(RetrofitError error) {
            Log.wtf("Error in Message List Dump", error.getMessage());
            Log.wtf("Error in Message List Dump", error.getUrl());
        }
    }

}