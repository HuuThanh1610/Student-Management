/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package StudentServletAPI;

import Model.ConnectToTrialDB;
import Model.Student;
import Model.StudentClass;
import jakarta.servlet.ServletContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Nguyen Huu Thanh
 */
@WebServlet(name = "Cache", urlPatterns = {"/Cache"})
public class Cache extends HttpServlet {
    ConnectToTrialDB connectDB = new ConnectToTrialDB();
  //  List<StudentClass> lstClass = connectDB.ClassListing();
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public List<Student> getListStudentFromCache(ServletContext context){
        List<Student> lstStudent = new ArrayList<>();
        Map<String, Object> cache = null;
        if (context != null) {
            cache = getCache(context);
            // Get the cache
            if (cache != null) {
                lstStudent = (List<Student>) cache.getOrDefault("listStudent", "");
            } else {
                lstStudent = connectDB.StudentListing();
                cache = new HashMap<>();
                cache.put("listStudent", lstStudent); // set data from Database 
                context.setAttribute("cache_Students", cache);
            } 
        }
        return lstStudent;
    }
    
    
     public Map<String, Object> getCache(ServletContext context){
        Map<String, Object> cache = null;
        if (context != null) {
            cache = (Map<String, Object>) context.getAttribute("cache_Students");
            // Get the cache
            if (cache == null) {
                cache = new HashMap<>();
                context.setAttribute("cache_Students", cache);
            } 
        }
        return cache;
    }
    
    
//    List<Student> lstStudent = new ArrayList<>();
//        if (context != null) {
//            cache = (Map<String, Object>) context.getAttribute("cache_Students");
//            // Get the cache
//            if (cache != null) {
//                lstStudent = (List<Student>) cache.getOrDefault("listStudent", "");
//            } else {
//                lstStudent = connectDB.StudentListing();
//                cache = new HashMap<>();
//                cache.put("listStudent", lstStudent); // set data from Database 
//                context.setAttribute("cache_Students", cache);
//            } 
//        }
//    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Cache</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Cache at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

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
        processRequest(request, response);
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
        processRequest(request, response);
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

}
