/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ClassServletAPI;

import Database.ConnectToDB;
import Model.ClassAPIResponse;
import Model.ConnectToTrialDB;
import Model.StudentClass;
import Model.Teacher;
import ViewModel.StudentClassView;
import com.google.gson.Gson;
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
import java.util.stream.Collectors;

/**
 *
 * @author Nguyen Huu Thanh
 */
@WebServlet(name = "SearchClass", urlPatterns = {"/SearchClass"})
public class SearchClass extends HttpServlet {

    CommonFunctionClass comFuncClass = new CommonFunctionClass();
    ConnectToDB connectToDB = new ConnectToDB();

//    @Override
//    public void init() throws ServletException {
//        ServletContext context = getServletContext();
//        // Initialize cache
//        Map<String, Object> cache = new HashMap<>();
//        Map<String, Object> cacheteacher = new HashMap<>();
//
//        Map<String, Object> checkExistCache = (Map<String, Object>) context.getAttribute("cache_Class");
//        if (checkExistCache == null) {
//            cache.put("listClass", connectDB.ClassListing()); // set data from Database 
//            context.setAttribute("cache_Class", cache);
//        }
//        //lstTeacher
//        Map<String, Object> checkExistCacheTeacher = (Map<String, Object>) context.getAttribute("cache_Teacher");
//        if (checkExistCacheTeacher == null) {
//            cacheteacher.put("listTeacher", connectDB.TeacherListing()); // set data from Database 
//            context.setAttribute("cache_Teacher", cacheteacher);
//        }
//    }
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public List<StudentClass> findClass(String className, String schoolYear) {
//        List<StudentClass> result = new ArrayList<>();
//        //Caching data
//        ServletContext context = getServletContext();
//        Map<String, Object> cacheData = (Map<String, Object>) context.getAttribute("cache_Class");
//        // Get the cache
//        if (cacheData != null) {
//            result = (List<StudentClass>) cacheData.getOrDefault("listClass", "");
//        } else {
//            result = connectDB.ClassListing();
//        }
        //Get list class from Database
        List<StudentClass> result = connectToDB.getListClassFromDB();
        if (!className.equals("")) {
            result = result.stream().filter(item -> item.getClassName().contains(className.trim()))
                    .collect(Collectors.toList());
        }
        if (!schoolYear.equals("")) {
            result = result.stream().filter(item -> comFuncClass.checkSchoolYearByName(item.getSchoolyear(), schoolYear))
                    .collect(Collectors.toList());
        }
        return result;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setContentType("application/json;charset=UTF-8");
//        ServletContext context = getServletContext();
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            String json;
            String className = request.getParameter("className");
            String schyear = request.getParameter("shooclYear");
            if (className == null) {
                className = "";
            }
            if (schyear == null) {
                schyear = "";
            }
//        //get list Teacher
//        Map<String, Object> cacheClass = (Map<String, Object>) context.getAttribute("cache_Teacher");
//        List<Teacher> lstTeacher = cacheClass != null
//                ? (List<Teacher>) cacheClass.getOrDefault("listTeacher", new ArrayList<>()) : connectDB.TeacherListing();
            //Get list Teacher from Database
            List<Teacher> lstTeacher = connectToDB.getListTeacherFromDB();
            //find class
            List<StudentClass> classes = findClass(className, schyear);
            if (classes != null) {
                List<StudentClassView> studentClassView = classes.stream()
                        .map(studentClass -> new StudentClassView(
                        studentClass.getClassNameID(),
                        studentClass.getClassName(),
                        studentClass.getSchoolyear(),
                        studentClass.getTeacherID(),
                        comFuncClass.getTeacherNameIdByteacherID(lstTeacher, studentClass.getTeacherID())
                )).collect(Collectors.toList());
                json = gson.toJson(new ClassAPIResponse("Success", HttpServletResponse.SC_OK, studentClassView));
            } else {
                json = gson.toJson(new ClassAPIResponse("Failure", HttpServletResponse.SC_BAD_REQUEST, null));
            }
            out.print(json);
            out.flush();
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
        //processRequest(request, response);
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
