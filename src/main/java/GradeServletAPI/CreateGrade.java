/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package GradeServletAPI;

import Database.ConnectToDB;
import Database.GradeDatabase;
import Model.ConnectToTrialDB;
import Model.Grade;
import Model.GradeAPIResponse;
import Model.Semester;
import Model.Student;
import Model.StudentAPIResponse;
import Model.StudentClass;
import Model.Subject;
import ViewModel.GradeView;
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
import java.util.stream.Collectors;

/**
 *
 * @author Nguyen Huu Thanh
 */
public class CreateGrade extends HttpServlet {

    CommonFunctionGrade comFuncGrade = new CommonFunctionGrade();
    ConnectToDB connectToDB = new ConnectToDB();
    GradeDatabase gradeDB = new GradeDatabase();

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
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        try (PrintWriter out = response.getWriter()) {
            String subjectID = request.getParameter("subjectID").trim();
            String studentID = request.getParameter("studentID").trim();
            String markOfSubject = request.getParameter("markOfSubject").trim();
            String semester = request.getParameter("semester").trim();
            Gson gson = new Gson();
            String json;
            if (gradeDB.checkExistGrade(Integer.parseInt(studentID), Integer.parseInt(subjectID), Integer.parseInt(semester))) {
                out.print(new Gson().toJson(new GradeAPIResponse("Grade already exists.", HttpServletResponse.SC_BAD_REQUEST, null)));
                out.flush();
                return;
            }
//            ServletContext context = getServletContext();
//            List<Grade> lstGrade = new ArrayList<>();
//            Map<String, Object> cache = null;
//            if (context != null) {
//                cache = (Map<String, Object>) context.getAttribute("cache_Grade");
//                // Get the cache
//                if (cache != null) {
//                    lstGrade = (List<Grade>) cache.getOrDefault("listGrade", "");
//                } else {
//                    lstGrade = connectDB.GradeListing();
//                    cache = new HashMap<>();
//                    cache.put("listGrade", lstGrade); // set data from Database 
//                    context.setAttribute("cache_Grade", cache);
//                }
//            }
//
//            //get list class
//            Map<String, Object> cacheClass = (Map<String, Object>) context.getAttribute("cache_Class");
//            List<StudentClass> lstClass = cacheClass != null
//                    ? (List<StudentClass>) cacheClass.getOrDefault("listClass", new ArrayList<>()) : connectDB.ClassListing();
//
//            //get listStudent
//            Map<String, Object> cacheStudent = (Map<String, Object>) context.getAttribute("cache_Students");
//            // Get the cache
//            List<Student> lstStudent = cacheStudent != null
//                    ? (List<Student>) cacheStudent.getOrDefault("listStudent", new ArrayList<>()) : connectDB.StudentListing();
//
//            //get listSubject
//            Map<String, Object> cacheSubject = (Map<String, Object>) context.getAttribute("cache_Subject");
//            // Get the cache
//            List<Subject> lstSubject = cacheSubject != null
//                    ? (List<Subject>) cacheSubject.getOrDefault("listSubject", new ArrayList<>()) : connectDB.SubjectListing();

            //new grade
            Grade newGrade = new Grade(1 , Integer.parseInt(studentID), Integer.parseInt(subjectID), Integer.parseInt(semester),
                    Double.parseDouble(markOfSubject), null, null);
            boolean commit = gradeDB.createGradeDatabase(newGrade);
            if (commit) {
                List<StudentClass> lstClass = connectToDB.getListClassFromDB();
                List<Student> lstStudent = connectToDB.getListStudentFromDB();
                List<Subject> lstSubject = connectToDB.getListSubjectFromDB();
                List<Semester> lstSemester = connectToDB.getListSemesterFromDB();
                List<GradeView> gradeView = connectToDB.getListGradeFromDB().stream()
                        .map(grade -> new GradeView(
                        grade.getMarkID(),
                        comFuncGrade.getClassByStudentID(lstClass, lstStudent, grade.getStudentID()).getClassName(),
                        comFuncGrade.findStudentByStudentID(lstStudent, grade.getStudentID()).getName(),
                        grade.getSubjectID(),
                        comFuncGrade.getSubjectBySubjectID(lstSubject, grade.getSubjectID()).getSubjectName(),
                        grade.getMarkOfSubject(),
                        grade.getSemesterID(),
                        comFuncGrade.getSemesterBySemesterID(lstSemester, grade.getSemesterID()).getSemesterName(),
                        comFuncGrade.getClassByStudentID(lstClass, lstStudent, grade.getStudentID()).getSchoolyear()
                )).collect(Collectors.toList());
                json = gson.toJson(new GradeAPIResponse("Grade created successfully.", HttpServletResponse.SC_OK, gradeView));
            } else {
                json = gson.toJson(new GradeAPIResponse("Failed to create grade.", HttpServletResponse.SC_BAD_REQUEST, null));
            }
            out.print(json);
            out.flush();
//                // clear data cache
//                cache.clear();
//                // Set new data to cache
//                cache.put("listGrade", lstGrade);
//                context.setAttribute("cache_Grade", cache);

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
        // processRequest(request, response);
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
