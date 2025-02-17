/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ClassServletAPI;

import Database.ClassDatabase;
import Database.ConnectToDB;
import Model.ConnectToTrialDB;
import Model.StudentAPIResponse;
import com.google.gson.Gson;
import jakarta.servlet.ServletContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import Model.StudentClass;
import Model.ClassAPIResponse;
import Model.Teacher;
import ViewModel.StudentClassView;
import java.util.stream.Collectors;

/**
 *
 * @author Nguyen Huu Thanh
 */
public class GetByClassID extends HttpServlet {

    ConnectToDB connectToDB = new ConnectToDB();
    CommonFunctionClass comFuncClass = new CommonFunctionClass();
    ClassDatabase classBD = new ClassDatabase();

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
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String classID = request.getParameter("classID").trim();
            Gson gson = new Gson();
            String json;
            //Get list Teacher from Database
            List<Teacher> lstTeacher = connectToDB.getListTeacherFromDB();
//        ServletContext context = getServletContext();
//        List<StudentClass> lstClass = new ArrayList<>();
//        Map<String, Object> cache = null;
//        if (context != null) {
//            cache = (Map<String, Object>) context.getAttribute("cache_Class");
//            // Get the cache
//            if (cache != null) {
//                lstClass = (List<StudentClass>) cache.getOrDefault("listClass", "");
//            } else {
//                lstClass = connectDB.ClassListing();
//                cache = new HashMap<>();
//                cache.put("listClass", lstClass); // set data from Database 
//                context.setAttribute("cache_Class", cache);
//            }
//        }
//       //get list Teacher
//        Map<String, Object> cacheClass = (Map<String, Object>) context.getAttribute("cache_Teacher");
//        List<Teacher> lstTeacher = cacheClass != null
//                ? (List<Teacher>) cacheClass.getOrDefault("listTeacher", new ArrayList<>()) : connectDB.TeacherListing();
            StudentClass stuClass = classBD.findClassByClassID(Integer.parseInt(classID));
            if (stuClass != null) {
                List<StudentClass> result = new ArrayList<>();
                result.add(stuClass);
                List<StudentClassView> studentClassView = result.stream()
                .map(studentClass -> new StudentClassView(
                        studentClass.getClassNameID(),
                        studentClass.getClassName(),
                        studentClass.getSchoolyear(),
                        studentClass.getTeacherID(),
                        comFuncClass.getTeacherNameIdByteacherID(lstTeacher, studentClass.getTeacherID())
                )).collect(Collectors.toList());
                json = gson.toJson(new ClassAPIResponse("Success", HttpServletResponse.SC_OK, studentClassView));
            } else {
                json = gson.toJson(new ClassAPIResponse("Failed to fetch Class data because Class does not exist", HttpServletResponse.SC_BAD_REQUEST, null));
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
        //   processRequest(request, response);
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
