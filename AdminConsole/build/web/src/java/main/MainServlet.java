///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package main;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.List;
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import objects.Project;
//import rest.ProjectREST;
//
///**
// *
// * @author daveyle
// */
//public class MainServlet extends HttpServlet {
//
//    /**
//     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
//     * methods.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        PrintWriter out = response.getWriter();
//        try {
//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet MainServlet</title>");            
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet MainServlet at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
//        } finally {
//            out.close();
//        }
//    }
//
//    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
//    /**
//     * Handles the HTTP <code>GET</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//         HttpSession session =request.getSession();
//        if (request.getParameter("getProjectByID")!=null){
//            ProjectREST prest=new ProjectREST();
//            Project p=prest.getSingleProject(request, response);
//            if (p!=null){
//                session.setAttribute("project", p);
//                }
//                 try {
//                RequestDispatcher rd = getServletContext().getRequestDispatcher("/projectjsp.jsp");
//                rd.forward(request, response);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            
//            
//        }
//        else if (request.getParameter("showAllProjects")!=null){
//            ProjectREST prest=new ProjectREST();
//            List<Project> p=prest.getAllProjects(request, response);
//            if (p!=null){
//                session.setAttribute("projects", p);
//                }
//                 try {
//                RequestDispatcher rd = getServletContext().getRequestDispatcher("/projectslistjsp.jsp");
//                rd.forward(request, response);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            
//        }
////        else if (request.getParameter("person")!=null){
////            
////        }
////        processRequest(request, response);
//    }
//
//    /**
//     * Handles the HTTP <code>POST</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        
//        processRequest(request, response);
//    }
//
//    /**
//     * Returns a short description of the servlet.
//     *
//     * @return a String containing servlet description
//     */
//    @Override
//    public String getServletInfo() {
//        return "Short description";
//    }// </editor-fold>
//
//}
