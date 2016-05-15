/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import beans.ProjectBean;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import objects.Checklist;
import objects.Checklist.ChecklistItem;
import objects.Checklist.SublistItem;
import objects.Group;
import objects.Location;
import objects.MessageThread;
import objects.MessageThread.Message;
import objects.Note;
import objects.Person;
import objects.Project;
import objects.Shipment;
import rest.ChecklistREST;
import rest.GroupREST;
import rest.LocationREST;
import rest.MessageThreadREST;
import rest.NoteREST;
import rest.PersonREST;
import rest.ProjectREST;
import rest.ShipmentREST;
import rest.UserREST;

/**
 *
 * @author daveyle
 */

public class AdminServlet extends HttpServlet {
    private String projectID;
    private String groupID;
    private String groupName;
    private String projectName;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
//    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        PrintWriter out = response.getWriter();
//        try {
//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet AdminServlet</title>");            
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet AdminServlet at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
//        } finally {
//            out.close();
//        }
//    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println(request.getParameter("getAllGroups"));
        Enumeration enumeration=request.getParameterNames();
        while (enumeration.hasMoreElements()){
            String s= (String)enumeration.nextElement();
            System.out.println(s+"\t"+request.getParameter(s));
        }
        
         HttpSession session =request.getSession();
        if (request.getParameter("getProjectByID")!=null){
           
            getProjectByID(session, request, response);
            
        }
        else if (request.getParameter("getAllProjects")!=null){
            getAllProjects(session, request, response);
            
        }
       
        else if (request.getParameter("getGroupByID")!=null){
            getGroupByID(session, request, response);
        }
        else if (request.getParameter("getAllGroups")!=null){
            getAllGroups(session, request, response);
        }
        else if (request.getParameter("getAllGroupsByProject")!=null){
            getAllGroupsByProject(session, request, response);
        }
         else if (request.getParameter("getAllPeople")!=null){
             getAllPeople(session, request, response);
            
        }
        else if (request.getParameter("getPersonByID")!=null){
            getPersonByID(session,request, response);
        
          
        }
         else if (request.getParameter("getAllPeopleByGroup")!=null){
            getAllPeopleByGroup(session, request, response);
        }
         else if (request.getParameter("getAllPeopleByProject")!=null){
            getAllPeopleByProject(session, request, response);
        }
         else if (request.getParameter("getAllLocationsByProject")!=null){
            getAllLocationsByProject(session, request, response);
        }
         else if (request.getParameter("getAllLocationsByGroup")!=null){
             getAllLocationsByGroup(session, request, response);
         }
         else if (request.getParameter("getLocationByID")!=null){
             getLocationByID(session, request, response);
         }
         
         else if (request.getParameter("getAllShipments")!=null){
            getAllShipments(session, request, response);
        }
         else if (request.getParameter("getAllShipmentsByGroup")!=null){
            getAllShipmentsByGroup(session, request, response);
        }
         else if (request.getParameter("getShipmentByID")!=null){
             getShipmentByID(session, request, response);
         }
         else if (request.getParameter("getAllNotes")!=null){
            getAllNotes(session, request, response);
        }
         else if (request.getParameter("getAllNotesByGroup")!=null){
            getAllNotesByGroup(session, request, response);
        }
         else if (request.getParameter("getNoteByID")!=null){
             getNoteByID(session, request, response);
         }
         else if (request.getParameter("getAllMessagesThreads")!=null){
            getAllMessageThreads(session, request, response);
        }
         else if (request.getParameter("getAllMessageThreadsByGroup")!=null){
            getAllMessageThreadsByGroup(session, request, response);
        }
         else if (request.getParameter("getMessageThreadByID")!=null){
             getMessageThreadByID(session, request, response);
         }
         else if (request.getParameter("getAllChecklists")!=null){
            getAllChecklists(session, request, response);
        }
         else if (request.getParameter("getAllChecklistsByGroup")!=null){
            getAllChecklistsByGroup(session, request, response);
        }
        else if (request.getParameter("getChecklistByID")!=null){
             getChecklistByID(session, request, response);
         }
//        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            HttpSession session =request.getSession();
            
            UserREST userRest=new UserREST();
            System.out.println("Here");
            if (request.getParameter("login")!=null){
                //for (String s :)
                Person p=userRest.doLogin(request, response);
                if (p!=null){
                session.setAttribute("person", p);
                }
                 try {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/mainjsp.jsp");
                rd.forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
                
                //System.out.println(request.getParameterNames().toString());
            }
            
//        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
    
    private void getProjectByID(HttpSession session, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         ProjectREST prest=new ProjectREST();
            String projID;
            if (request.getParameter("getProjectByID").equals("Find Project")){
            projID=request.getParameter("projectID");
            
            }
            else{
                projID=request.getParameter("getProjectByID");
            }
            this.projectID=projID;
            session.setAttribute("projectID", projID);
            
           
           
            Project p=prest.getSingleProject(projID);
            
            if (p!=null){
                this.projectName=p.getName();
                session.setAttribute("project", p);
                }
                 try {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/projectjsp.jsp");
                rd.forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
    private void getAllProjects(HttpSession session, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
                    ProjectREST prest=new ProjectREST();
            List<Project> p=prest.getAllProjects();
            //System.out.println(p.size());
            response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Projects</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>All Projects</h1>");
            out.println("<TABLE id=\"projectsTable\">" +
"<TR>" +
"<TD>ID</TD>" +
                    "<TD WIDTH=\"15%\">  </TD>"+
"<TD>Name</TD>" +
"</TR>");
            for (Project proj: p){
                out.println("<TR>"+"<TD>");
                out.println("<form name=\"Group Chooser\" action=\""+request.getContextPath()+"/AdminServlet\" method=\"get\">");
                out.println("<input type=\"submit\" value=\""+proj.getId()+"\" name=\"getProjectByID\" /> </form>");
                        
                out.println("</td>");
                out.println("<TD></TD>");
                out.println("<td>"+proj.getName()+"</td>"+"</TR>");
            }
            out.println("</TABLE>");
            
            
            out.println("</body>");
            out.println("</html>");
       
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
    private void getGroupByID(HttpSession session, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        GroupREST grest=new GroupREST();
            String groupID;
            if (request.getParameter("getGroupByID").equals("Find Group")){
            groupID=request.getParameter("groupID");
            
            }
            else{
                groupID=request.getParameter("getGroupByID");
            }
            this.groupID=groupID;
            session.setAttribute("groupID", groupID);
            
           
            
          
            Group g=grest.getSingleGroup(groupID);
            
            if (g!=null){
                this.groupName=g.getName();
                session.setAttribute("group", g);
                }
                 try {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/groupjsp.jsp");
                rd.forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
     private void getAllGroups(HttpSession session, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
            GroupREST grest= new GroupREST();
            List<Group> g=grest.getAllGroups();
            response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Groups</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>All Groups</h1>");
            out.println("<TABLE id=\"groupsTable\">" +
"<TR>" +
"<TD>ID</TD>" +
                    "<TD WIDTH=\"15%\">  </TD>"+
"<TD>Name</TD>" +
"</TR>");
            for (Group grp: g){
                 out.println("<TR>"+"<TD>");
                out.println("<form name=\"Group Chooser\" action=\""+request.getContextPath()+"/AdminServlet\" method=\"get\">");
                out.println("<input type=\"submit\" value=\""+grp.getId()+"\" name=\"getGroupByID\" /> </form>");
                        
                out.println("</td>");
                out.println("<TD></TD>");
                out.println("<td>"+grp.getName()+"</td>"+"</TR>");
            }
            out.println("</TABLE>");
            
            
            out.println("</body>");
            out.println("</html>");
       
            } catch (Exception e) {
                e.printStackTrace();
            }
     }
     private void getAllGroupsByProject(HttpSession session, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
         GroupREST grest= new GroupREST();
            List<Group> g=grest.getAllGroupsByProject(this.projectID);
            response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Groups</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Groups in Project :"+this.projectName+"</h1>");
            out.println("<TABLE id=\"groupsTable\">" +
"<TR>" +
"<TD>ID</TD>" +
                    "<TD WIDTH=\"15%\">  </TD>"+
"<TD>Name</TD>" +
"</TR>");
            for (Group grp: g){
                 out.println("<TR>"+"<TD>");
                out.println("<form name=\"Group Chooser\" action=\""+request.getContextPath()+"/AdminServlet\" method=\"get\">");
                out.println("<input type=\"submit\" value=\""+grp.getId()+"\" name=\"getGroupByID\" /> </form>");
                        
                out.println("</td>");
                out.println("<TD></TD>");
                out.println("<td>"+grp.getName()+"</td>"+"</TR>");
            }
            out.println("</TABLE>");
            
            
            out.println("</body>");
            out.println("</html>");
       
            } catch (Exception e) {
                e.printStackTrace();
            }
     }
     private void getAllPeople(HttpSession session, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
         PersonREST prest=new PersonREST();
             List<Person> people=prest.getAllPersons();
             response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>People</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>All People</h1>");
            out.println("<TABLE id=\"groupsTable\">" +
"<TR>" +
"<TD>ID</TD>" +
                    "<TD WIDTH=\"15%\">  </TD>"+
"<TD>Name</TD>" +
                    "<TD WIDTH=\"15%\">  </TD>"+
                    "<TD>Email</TD>" +
"</TR>");
            for (Person p: people){
                out.println("<TR>"+"<TD>");
                out.println("<form name=\"Group Chooser\" action=\""+request.getContextPath()+"/AdminServlet\" method=\"get\">");
                out.println("<input type=\"submit\" value=\""+p.getId()+"\" name=\"getPersonByID\" /> </form>");
                        
                out.println("</td>");
                out.println("<TD></TD>");
                out.println("<td>"+p.getName()+"</td>");
                out.println("<TD></TD>");
                out.println("<td>"+p.getEmail()+"</td>"+"</TR>");
            }
            out.println("</TABLE>");
            
            
            out.println("</body>");
            out.println("</html>");
       
            } catch (Exception e) {
                e.printStackTrace();
            }
     }
     private void getPersonByID(HttpSession session, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
         PersonREST prest=new PersonREST();
            String personID;
            if (request.getParameter("getPersonByID").equals("Find Person")){
            personID=request.getParameter("personID");
            
            }
            else{
                personID=request.getParameter("getPersonByID");
            }
            session.setAttribute("personID", personID);
            Person p=prest.getSinglePerson(personID);
            if (p!=null){
                session.setAttribute("person", p);
                }
                 try {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/personjsp.jsp");
                rd.forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
     }
     private void getAllPeopleByGroup(HttpSession session, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
         PersonREST prest=new PersonREST();
            
             List<Person> people=prest.getAllPersonsByGroupID(this.groupID);
             response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>People</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>People in Group: "+this.groupName+"</h1>");
            
            
            out.println("<TABLE id=\"groupsTable\">" +
"<TR>" +
"<TD>ID</TD>" +
                    "<TD WIDTH=\"15%\">  </TD>"+
"<TD>Name</TD>" +
                    "<TD WIDTH=\"15%\">  </TD>"+
                    "<TD>Email</TD>" +
"</TR>");
            for (Person p: people){
                out.println("<TR>"+"<TD>");
                out.println("<form name=\"Group Chooser\" action=\""+request.getContextPath()+"/AdminServlet\" method=\"get\">");
                out.println("<input type=\"submit\" value=\""+p.getId()+"\" name=\"getPersonByID\" /> </form>");
                        
                out.println("</td>");
                out.println("<TD></TD>");
                out.println("<td>"+p.getName()+"</td>");
                out.println("<TD></TD>");
                out.println("<td>"+p.getEmail()+"</td>"+"</TR>");
            }
            out.println("</TABLE>");
            
            
            out.println("</body>");
            out.println("</html>");
       
            } catch (Exception e) {
                e.printStackTrace();
            }
     }
     private void getAllPeopleByProject(HttpSession session, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
         PersonREST prest=new PersonREST();
            
             List<Person> people=prest.getAllPersonsByProjectID(this.projectID);
             response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>People</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>People in Project: "+this.projectName+"</h1>");
            out.println("<TABLE id=\"groupsTable\">" +
"<TR>" +
"<TD>ID</TD>" +
                    "<TD WIDTH=\"15%\">  </TD>"+
"<TD>Name</TD>" +
                    "<TD WIDTH=\"15%\">  </TD>"+
                    "<TD>Email</TD>" +
"</TR>");
            for (Person p: people){
                out.println("<TR>"+"<TD>");
                out.println("<form name=\"Group Chooser\" action=\""+request.getContextPath()+"/AdminServlet\" method=\"get\">");
                out.println("<input type=\"submit\" value=\""+p.getId()+"\" name=\"getPersonByID\" /> </form>");
                        
                out.println("</td>");
                out.println("<TD></TD>");
                out.println("<td>"+p.getName()+"</td>");
                out.println("<TD></TD>");
                out.println("<td>"+p.getEmail()+"</td>"+"</TR>");
            }
            out.println("</TABLE>");
            
            
            out.println("</body>");
            out.println("</html>");
       
            } catch (Exception e) {
                e.printStackTrace();
            }
     }
     
     
     private void getAllLocationsByProject(HttpSession session, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
             LocationREST prest=new LocationREST();
            
             List<Location> locations=prest.getAllLocationsByProject(this.projectID);
             response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Locations</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Locations in Project: "+this.projectName+"</h1>");
            out.println("<TABLE id=\"groupsTable\">" +
"<TR>" +
"<TD>ID</TD>" +
                    "<TD WIDTH=\"15%\">  </TD>"+
"<TD>Name</TD>" +
                    "<TD WIDTH=\"15%\">  </TD>"+
                    "<TD>Lat, Lng</TD>" +
"</TR>");
            for (Location p: locations){
                out.println("<TR>"+"<TD>");
                out.println("<form name=\"Group Chooser\" action=\""+request.getContextPath()+"/AdminServlet\" method=\"get\">");
                out.println("<input type=\"submit\" value=\""+p.getId()+"\" name=\"getLocationByID\" /> </form>");
                        
                out.println("</td>");
                out.println("<TD></TD>");
                out.println("<td>"+p.getName()+"</td>");
                out.println("<TD></TD>");
                out.println("<td>"+p.getLat()+", "+p.getLng()+"</td>"+"</TR>");
            }
            out.println("</TABLE>");
            
            
            out.println("</body>");
            out.println("</html>");
       
            } catch (Exception e) {
                e.printStackTrace();
            }
     }
     private void getAllLocationsByGroup(HttpSession session, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
            LocationREST prest=new LocationREST();
            
             List<Location> locations=prest.getAllLocationsByGroup(this.groupID);
             response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Locations</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Locations in Group: "+this.groupName+"</h1>");
            out.println("<TABLE id=\"groupsTable\">" +
"<TR>" +
"<TD>ID</TD>" +
                    "<TD WIDTH=\"15%\">  </TD>"+
"<TD>Name</TD>" +
                    "<TD WIDTH=\"15%\">  </TD>"+
                    "<TD>Lat, Lng</TD>" +
"</TR>");
            for (Location p: locations){
                out.println("<TR>"+"<TD>");
                out.println("<form name=\"Group Chooser\" action=\""+request.getContextPath()+"/AdminServlet\" method=\"get\">");
                out.println("<input type=\"submit\" value=\""+p.getId()+"\" name=\"getLocationByID\" /> </form>");
                        
                out.println("</td>");
                out.println("<TD></TD>");
                out.println("<td>"+p.getName()+"</td>");
                out.println("<TD></TD>");
                out.println("<td>"+p.getLat()+", "+p.getLng()+"</td>"+"</TR>");
            }
            out.println("</TABLE>");
            
            
            out.println("</body>");
            out.println("</html>");
       
            } catch (Exception e) {
                e.printStackTrace();
            }
     }
     private void getLocationByID(HttpSession session, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
            LocationREST lrest=new LocationREST();
            String locationID= request.getParameter("getLocationByID");
            session.setAttribute("locationID", locationID);
            Location l= lrest.getSingleLocation(locationID);
            if (l!=null){
                session.setAttribute("location", l);
            }
            try{
                RequestDispatcher rd= getServletContext().getRequestDispatcher("/locationjsp.jsp");
                rd.forward(request, response);
            }catch(Exception e){
                e.printStackTrace();
            
            }
     }
     private void getAllShipments(HttpSession session, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
            ShipmentREST prest=new ShipmentREST();
            
             List<Shipment> shipments=prest.getAllShipments();
             response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Shipments</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Shipments</h1>");
            out.println("<TABLE id=\"groupsTable\">" +
"<TR>" +
"<TD>ID</TD>" +
                    "<TD WIDTH=\"15%\">  </TD>"+
"<TD>Name</TD>" +
                    "<TD WIDTH=\"15%\">  </TD>"+
                    "<TD>Contents</TD>" +
                     "<TD WIDTH=\"15%\">  </TD>"+
                    "<TD>To, From</TD>"+
"</TR>");
            for (Shipment p: shipments){
                out.println("<TR>"+"<TD>");
                out.println("<form name=\"Group Chooser\" action=\""+request.getContextPath()+"/AdminServlet\" method=\"get\">");
                out.println("<input type=\"submit\" value=\""+p.getId()+"\" name=\"getShipmentByID\" /> </form>");
                        
                out.println("</td>");
                out.println("<TD></TD>");
                out.println("<td>"+p.getName()+"</td>");
                out.println("<TD></TD>");
                out.println("<td>"+p.getContents()+"</td>");
                out.println("<TD></TD>");
                out.println("<td>"+p.getFrom()+"->"+p.getTo()+"</td>"+"</TR>");
            }
            out.println("</TABLE>");
            
            
            out.println("</body>");
            out.println("</html>");
       
            } catch (Exception e) {
                e.printStackTrace();
            }
     }
     private void getAllShipmentsByGroup(HttpSession session, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
            ShipmentREST prest=new ShipmentREST();
            
             List<Shipment> shipments=prest.getAllShipmentsByGroup(this.groupID);
             response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Shipments</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Shipments in Group: "+this.groupName+"</h1>");
            out.println("<TABLE id=\"groupsTable\">" +
"<TR>" +
"<TD>ID</TD>" +
                    "<TD WIDTH=\"15%\">  </TD>"+
"<TD>Name</TD>" +
                    "<TD WIDTH=\"15%\">  </TD>"+
                    "<TD>Contents</TD>" +
                     "<TD WIDTH=\"15%\">  </TD>"+
                    "<TD>To, From</TD>"+
"</TR>");
            for (Shipment p: shipments){
                out.println("<TR>"+"<TD>");
                out.println("<form name=\"Group Chooser\" action=\""+request.getContextPath()+"/AdminServlet\" method=\"get\">");
                out.println("<input type=\"submit\" value=\""+p.getId()+"\" name=\"getShipmentByID\" /> </form>");
                        
                out.println("</td>");
                out.println("<TD></TD>");
                out.println("<td>"+p.getName()+"</td>");
                out.println("<TD></TD>");
                out.println("<td>"+p.getContents()+"</td>");
                out.println("<TD></TD>");
                out.println("<td>"+p.getFrom()+"->"+p.getTo()+"</td>"+"</TR>");
            }
            out.println("</TABLE>");
            
            
            out.println("</body>");
            out.println("</html>");
       
            } catch (Exception e) {
                e.printStackTrace();
            }
     }
     private void getShipmentByID(HttpSession session, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
         ShipmentREST lrest=new ShipmentREST();
            String shipmentID= request.getParameter("getShipmentByID");
            session.setAttribute("shipmentID", shipmentID);
            Shipment l= lrest.getSingleShipment(shipmentID);
            if (l!=null){
                session.setAttribute("shipment", l);
            }
            try{
                RequestDispatcher rd= getServletContext().getRequestDispatcher("/shipmentjsp.jsp");
                rd.forward(request, response);
            }catch(Exception e){
                e.printStackTrace();
            
            }
     
     }
     private void getAllNotes(HttpSession session, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
     NoteREST prest=new NoteREST();
            
             List<Note> notes=prest.getAllNotes();
             response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Notes</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Notes</h1>");
            out.println("<TABLE id=\"groupsTable\">" +
"<TR>" +
"<TD>ID</TD>" +
                    "<TD WIDTH=\"15%\">  </TD>"+
"<TD>Name</TD>" +
                    "<TD WIDTH=\"15%\">  </TD>"+
                   
                    "<TD>Time</TD>"+
"</TR>");
            for (Note p: notes){
                out.println("<TR>"+"<TD>");
                out.println("<form name=\"Group Chooser\" action=\""+request.getContextPath()+"/AdminServlet\" method=\"get\">");
                out.println("<input type=\"submit\" value=\""+p.getId()+"\" name=\"getNoteByID\" /> </form>");
                        
                out.println("</td>");
                out.println("<TD></TD>");
                out.println("<td>"+p.getTitle()+"</td>");
                out.println("<TD></TD>");
                
                out.println("<td>"+p.getDate()+" "+p.getTime()+"</td>"+"</TR>");
            }
            out.println("</TABLE>");
            
            
            out.println("</body>");
            out.println("</html>");
       
            } catch (Exception e) {
                e.printStackTrace();
            }
     }
     private void getAllNotesByGroup(HttpSession session, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
            NoteREST prest=new NoteREST();
            
             List<Note> notes=prest.getAllNotesByGroup(this.groupID);
             response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Notes</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Notes in Group: "+this.groupName+"</h1>");
            out.println("<TABLE id=\"groupsTable\">" +
"<TR>" +
"<TD>ID</TD>" +
                    "<TD WIDTH=\"15%\">  </TD>"+
"<TD>Name</TD>" +
                    "<TD WIDTH=\"15%\">  </TD>"+
                    
                    "<TD>Time</TD>"+
"</TR>");
            for (Note p: notes){
                out.println("<TR>"+"<TD>");
                out.println("<form name=\"Group Chooser\" action=\""+request.getContextPath()+"/AdminServlet\" method=\"get\">");
                out.println("<input type=\"submit\" value=\""+p.getId()+"\" name=\"getNoteByID\" /> </form>");
                        
                out.println("</td>");
                out.println("<TD></TD>");
                out.println("<td>"+p.getTitle()+"</td>");
                out.println("<TD></TD>");
                
                out.println("<td>"+p.getDate()+" "+p.getTime()+"</td>"+"</TR>");
            }
            out.println("</TABLE>");
            
            
            out.println("</body>");
            out.println("</html>");
       
            } catch (Exception e) {
                e.printStackTrace();
            }
     }
     private void getNoteByID(HttpSession session, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
         
         NoteREST lrest=new NoteREST();
            String noteID= request.getParameter("getNoteByID");
            session.setAttribute("noteID", noteID);
            Note l= lrest.getSingleNote(noteID);
            if (l!=null){
                session.setAttribute("note", l);
            }
            try{
                RequestDispatcher rd= getServletContext().getRequestDispatcher("/notejsp.jsp");
                rd.forward(request, response);
            }catch(Exception e){
                e.printStackTrace();
            
            }
     
     }
     private void getAllMessageThreads(HttpSession session, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
                  MessageThreadREST prest=new MessageThreadREST();
            
             List<MessageThread> threads=prest.getAllMessageThreads();
           
             response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Message Thread</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>All Message Threads</h1>");
            out.println("<TABLE id=\"groupsTable\">" +
"<TR>" +
                    "<TD>ID</TD>"+
                    "<TD WIDTH=\"15%\"></TD>"+
                  
"<TD>Title</TD>" +
                    
                    "<TD WIDTH=\"15%\">  </TD>"+
                    "<TD>Time</TD>" +
                     
"</TR>");
            
            for (MessageThread p: threads){
               
                out.println("<TR>"+"<TD>");
                out.println("<form name=\"Group Chooser\" action=\""+request.getContextPath()+"/AdminServlet\" method=\"get\">");
                out.println("<input type=\"submit\" value=\""+p.getId()+"\" name=\"getMessageThreadByID\" /> </form>");
                        
                out.println("</td>");
                out.println("<TD></TD>");
                out.println("<td>"+p.getTitle()+"</td>");
                out.println("<TD></TD>");
                out.println("<td>"+p.getNewestTime()+"</td>"+"</TR>");
            }
            out.println("</TABLE>");
            
            
            out.println("</body>");
            out.println("</html>");
       
            } catch (Exception e) {
                e.printStackTrace();
            }
     
     }
     private void getAllMessageThreadsByGroup(HttpSession session, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
         
         MessageThreadREST prest=new MessageThreadREST();
            
             List<MessageThread> threads=prest.getAllMessageThreadsByGroup(this.groupID);
           
             response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Message Thread</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Message Threads in Group: "+this.groupName+"</h1>");
            out.println("<TABLE id=\"groupsTable\">" +
"<TR>" +
                    "<TD>ID</TD>"+
                    "<TD WIDTH=\"15%\"></TD>"+
                   
"<TD>Title</TD>" +
                    
                    "<TD WIDTH=\"15%\">  </TD>"+
                    "<TD>Time</TD>" +
                     
"</TR>");
            
            for (MessageThread p: threads){
               
                out.println("<TR>"+"<TD>");
                out.println("<form name=\"Group Chooser\" action=\""+request.getContextPath()+"/AdminServlet\" method=\"get\">");
                out.println("<input type=\"submit\" value=\""+p.getId()+"\" name=\"getMessageThreadByID\" /> </form>");
                        
                out.println("</td>");
                out.println("<TD></TD>");
                out.println("<td>"+p.getTitle()+"</td>");
                out.println("<TD></TD>");
                out.println("<td>"+p.getNewestTime()+"</td>"+"</TR>");
            }
            out.println("</TABLE>");
            
            
            out.println("</body>");
            out.println("</html>");
       
            } catch (Exception e) {
                e.printStackTrace();
            }
     
     }
     private void getMessageThreadByID(HttpSession session, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
            MessageThreadREST prest=new MessageThreadREST();
            
             MessageThread thread=prest.getSingleMessageThread(request.getParameter("getMessageThreadByID"));
             thread.setItemList(prest.getMessages(thread.getId()+""));
             if (thread==null){return;}
             response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Message Thread</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>"+thread.getTitle()+"</h1>");
            out.println("<TABLE id=\"groupsTable\">" +
"<TR>" +
"<TD>Sender</TD>" +
                    "<TD WIDTH=\"15%\">  </TD>"+
"<TD>Contents</TD>" +
                    "<TD WIDTH=\"15%\">  </TD>"+
                    "<TD>Time</TD>" +
                     
"</TR>");
            
            for (Message p: thread.getItemList()){
                PersonREST personREST=new PersonREST();
                Person person=personREST.getSinglePerson(p.getSender());
                String sender="";
                if (person!=null){
                    sender=person.getName();
                }
                out.println("<TR>");
//                out.println(p.getId());
////                out.println("<form name=\"Group Chooser\" action=\""+request.getContextPath()+"/AdminServlet\" method=\"get\">");
////                out.println("<input type=\"submit\" value=\""+p.getId()+"\" name=\"getNoteByID\" /> </form>");
//                        
//                out.println("</td>");
//                out.println("<TD></TD>");
                out.println("<td>"+sender+"</td>");
                out.println("<TD></TD>");
                out.println("<td>"+p.getItem()+"</td>");
                out.println("<TD></TD>");
                out.println("<td>"+p.getTime()+"</td>"+"</TR>");
            }
            out.println("</TABLE>");
            
            
            out.println("</body>");
            out.println("</html>");
       
            } catch (Exception e) {
                e.printStackTrace();
            }
     }
     private void getAllChecklists(HttpSession session, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
     ChecklistREST prest=new ChecklistREST();
            
             List<Checklist> lists=prest.getAllChecklists();
           
             response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Checklists</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>All Checklists</h1>");
            out.println("<TABLE id=\"groupsTable\">" +
"<TR>" +
                    "<TD>ID</TD>"+
                    "<TD WIDTH=\"15%\"></TD>"+
                    "<TD>Time</TD>"+
"<TD>Title</TD>" +
                    
                   
                     
"</TR>");
            
            for (Checklist p: lists){
               
                out.println("<TR>"+"<TD>");
                out.println("<form name=\"Group Chooser\" action=\""+request.getContextPath()+"/AdminServlet\" method=\"get\">");
                out.println("<input type=\"submit\" value=\""+p.getId()+"\" name=\"getChecklistByID\" /> </form>");
                        
                out.println("</td>");
                out.println("<TD></TD>");
                out.println("<td>"+p.getTitle()+"</td>"+"</TR>");
            }
            out.println("</TABLE>");
            
            
            out.println("</body>");
            out.println("</html>");
       
            } catch (Exception e) {
                e.printStackTrace();
            }
     }
     private void getAllChecklistsByGroup(HttpSession session, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
     ChecklistREST prest=new ChecklistREST();
            
             List<Checklist> lists=prest.getAllChecklistsByGroup(this.groupID);
           
             response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Checklists</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Checklists in Group: "+this.groupName+"</h1>");
            out.println("<TABLE id=\"groupsTable\">" +
"<TR>" +
                    "<TD>ID</TD>"+
                    "<TD WIDTH=\"15%\"></TD>"+
                    "<TD>Time</TD>"+
"<TD>Title</TD>" +
                    
                   
                     
"</TR>");
            
            for (Checklist p: lists){
               
                out.println("<TR>"+"<TD>");
                out.println("<form name=\"Group Chooser\" action=\""+request.getContextPath()+"/AdminServlet\" method=\"get\">");
                out.println("<input type=\"submit\" value=\""+p.getId()+"\" name=\"getChecklistByID\" /> </form>");
                        
                out.println("</td>");
                out.println("<TD></TD>");
                out.println("<td>"+p.getTitle()+"</td>"+"</TR>");
            }
            out.println("</TABLE>");
            
            
            out.println("</body>");
            out.println("</html>");
       
            } catch (Exception e) {
                e.printStackTrace();
            }
     
     }
     private void getChecklistByID(HttpSession session, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
     ChecklistREST prest=new ChecklistREST();
            
             Checklist list=prest.getSingleChecklist(request.getParameter("getChecklistByID"));
             if (list==null){return;}
             response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Checklist</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>"+list.getTitle()+"</h1>");
            out.println("<TABLE id=\"groupsTable\">" +
"<TR>" +
"<TD></TD>" +
             "<TD></TD>" +       
"<TD></TD>" +
             "<TD></TD>" +       
                   
                     
"</TR>");
            for (ChecklistItem item: list.getItemList()){
                System.out.println(item.getItem());
                out.println("<TR>"+"<TD>");
//                                out.println("<form name=\"Group Chooser\" action=\""+request.getContextPath()+"/AdminServlet\" method=\"get\">");
                out.println(" <input type=\"checkbox\" name=\""+item.getItem()+"\" value=\"");
                        if (item.isDone()){
                            out.println("ON");
                        }
                        else{
                            out.println("OFF");
                        }
                        
                        
                        out.println("\" />");
//                                </form>");
                       out.println("</td>");
                       out.println("<TD></TD>");
                       out.println("<TD>"+item.getItem()+"</TD>");
                       out.println("<TD></TD></TR>");
                        for(SublistItem subitem: item.getSublistItems()){
                            out.println("<TR>"+"<TD></TD><TD>");
//                                out.println("<form name=\"Group Chooser\" action=\""+request.getContextPath()+"/AdminServlet\" method=\"get\">");
                out.println(" <input type=\"checkbox\" name=\""+subitem.getItem()+"\" value=\"");
                        if (subitem.isDone()){
                            out.println("ON");
                        }
                        else{
                            out.println("OFF");
                        }
                        
                        
                        out.println("\" />");
//                                </form>");
                       out.println("</td>");
                       out.println("<TD></TD>");
                       out.println("<TD>"+subitem.getItem()+"</TD>");
                       out.println("</TR>");
                        }
                
            }
//            for (String key: thread.getItemList().keySet()){
//                Message p=thread.getItemList().get(key);
//                out.println("<TR>"+"<TD>");
//                out.println(p.getId());
////                out.println("<form name=\"Group Chooser\" action=\""+request.getContextPath()+"/AdminServlet\" method=\"get\">");
////                out.println("<input type=\"submit\" value=\""+p.getId()+"\" name=\"getNoteByID\" /> </form>");
//                        
//                out.println("</td>");
//                out.println("<TD></TD>");
//                out.println("<td>"+p.getSender()+"</td>");
//                out.println("<TD></TD>");
//                out.println("<td>"+p.getItem()+"</td>");
//                out.println("<TD></TD>");
//                out.println("<td>"+p.getTime()+"</td>"+"</TR>");
//            }
            out.println("</TABLE>");
            
            
            out.println("</body>");
            out.println("</html>");
       
            } catch (Exception e) {
                e.printStackTrace();
            }
     }
}
