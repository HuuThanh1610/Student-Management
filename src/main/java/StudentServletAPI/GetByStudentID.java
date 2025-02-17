/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package StudentServletAPI;

import Database.ConnectToDB;
import Database.StudentDatabase;
import Model.StudentAPIResponse;
import Model.ConnectToTrialDB;
import Model.Student;
import Model.StudentClass;
import ViewModel.StudentView;
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
public class GetByStudentID extends HttpServlet {

    CommonFunctionStudent ComFuncStudent = new CommonFunctionStudent();
    ConnectToDB conToDB = new ConnectToDB();
    StudentDatabase studentDB = new StudentDatabase();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        try (PrintWriter out = response.getWriter()) {
            response.setContentType("application/json;charset=UTF-8");
            String studentID = request.getParameter("studentID").trim();
            Gson gson = new Gson();
            String json;
            //Get list Class from DB
            List<StudentClass> lstClass = conToDB.getListClassFromDB();
//        ServletContext context = getServletContext();
//        List<Student> lstStudent = new ArrayList<>();
//        Map<String, Object> cache = null;
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
//        //get list class
//        Map<String, Object> cacheClass = (Map<String, Object>) context.getAttribute("cache_Class");
//        List<StudentClass> lstClass = cacheClass != null
//                ? (List<StudentClass>) cacheClass.getOrDefault("listClass", new ArrayList<>()) : connectDB.ClassListing();

            Student findStudent = studentDB.findStudentbyStudentID(Integer.parseInt(studentID));
            if (findStudent != null) {
                List<Student> result = new ArrayList<>();
                result.add(findStudent);
                List<StudentView> studentView = result.stream()
                        .map(student -> new StudentView(
                        student.getStudentID(),
                        student.getName(),
                        student.getGender(),
                        ComFuncStudent.findClassName(lstClass, student.getClassID()),
                        ComFuncStudent.findClassByID(lstClass, student.getClassID()).getSchoolyear(),
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
