/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package StudentServletAPI;

import Database.ConnectToDB;
import Database.StudentDatabase;
import Model.StudentAPIResponse;
import Model.Student;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import Model.ConnectToTrialDB;
import jakarta.servlet.ServletContext;
import java.util.HashMap;
import java.util.Map;
import Model.StudentClass;
import ViewModel.StudentView;
import java.sql.SQLException;

/**
 *
 * @author Nguyen Huu Thanh
 */
@WebServlet(name = "SearchStudent", urlPatterns = {"/SearchStudent"})
public class SearchStudent extends HttpServlet {

    CommonFunctionStudent ComFuncStudent = new CommonFunctionStudent();
    ConnectToDB conToDB = new ConnectToDB();
    StudentDatabase studentDB = new StudentDatabase();

//    @Override
//    public void init() throws ServletException {
//        ServletContext context = getServletContext();
//        // Initialize cache
//        Map<String, Object> cache = new HashMap<>();
//        Map<String, Object> cacheClass = new HashMap<>();
//
//        Map<String, Object> checkExistCache = (Map<String, Object>) context.getAttribute("cache_Students");
//        if (checkExistCache == null) {
//            cache.put("listStudent", connectTrialDB.StudentListing()); // set data from Database 
//            context.setAttribute("cache_Students", cache);
//        }
//
//        Map<String, Object> checkExistCacheClass = (Map<String, Object>) context.getAttribute("cache_Class");
//        if (checkExistCacheClass == null) {
//            cacheClass.put("listClass", connectTrialDB.ClassListing()); // set data from Database 
//            context.setAttribute("cache_Class", cacheClass);
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
    public List<Student> findStudent(String studentName, String studentIden, String className) {
//        ServletContext context = getServletContext();
//        //get listStudent by cache
//        Map<String, Object> cacheStudent = (Map<String, Object>) context.getAttribute("cache_Students");
//        List<Student> result = cacheStudent != null
//                ? (List<Student>) cacheStudent.getOrDefault("listStudent", new ArrayList<>()) : connectDB.StudentListing();
//
//        //get list class by cache
//        Map<String, Object> cacheClass = (Map<String, Object>) context.getAttribute("cache_Class");
//        List<StudentClass> lstClass = cacheClass != null
//                ? (List<StudentClass>) cacheClass.getOrDefault("listClass", new ArrayList<>()) : connectDB.ClassListing();
        //Get list Student from DB
        List<Student> lstStudent = conToDB.getListStudentFromDB();
        //Get list Class from DB
        List<StudentClass> lstClass = conToDB.getListClassFromDB();

        //Function find students
        if (!studentName.trim().equals("")) {
            lstStudent = lstStudent.stream().filter(item -> item.getName().contains(studentName.trim()) && item.getStatus().equalsIgnoreCase("active"))
                    .collect(Collectors.toList());
        }
        if (!studentIden.trim().equals("")) {
            lstStudent = lstStudent.stream().filter(item -> item.getIdentification().contains(studentIden.trim()) && item.getStatus().equalsIgnoreCase("active"))
                    .collect(Collectors.toList());
        }
        if (!className.trim().equals("")) {
            lstStudent = lstStudent.stream().filter(item -> ComFuncStudent.findClassName(lstClass, item.getClassID()).contains(className.trim())
                    && item.getStatus().equalsIgnoreCase("active"))
                    .collect(Collectors.toList());
        }
        return lstStudent;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            String json;
            String studentName = request.getParameter("studentName");
            String studentIden = request.getParameter("studentIden");
            String className = request.getParameter("className");
//        ServletContext context = getServletContext();
//        //get list class
//        Map<String, Object> cacheClass = (Map<String, Object>) context.getAttribute("cache_Class");
//        List<StudentClass> lstClass = cacheClass != null
//                ? (List<StudentClass>) cacheClass.getOrDefault("listClass", new ArrayList<>()) : connectDB.ClassListing();

            //find student
            List<Student> students = findStudent(studentName, studentIden, className);
            if (!students.isEmpty()) {
                List<StudentView> studentView = students.stream()
                        .map(student -> new StudentView(
                        student.getStudentID(),
                        student.getName(),
                        student.getGender(),
                        studentDB.findClassByClassID(student.getClassID()).getClassName(),
                        studentDB.findClassByClassID(student.getClassID()).getSchoolyear(),
                        student.getAddress(),
                        student.getIdentification(),
                        student.getDateOfBirth(),
                        student.getPhoneNumber(),
                        student.getEmail(),
                        student.getCreateDate(),
                        student.getUpdateDate(),
                        student.getStatus()
                )).collect(Collectors.toList());
                json = gson.toJson(new StudentAPIResponse("Success", HttpServletResponse.SC_OK, studentView));
            } else {
                json = gson.toJson(new StudentAPIResponse("Failure", HttpServletResponse.SC_BAD_REQUEST, null));
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
