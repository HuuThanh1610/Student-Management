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
public class SearchGrade extends HttpServlet {

    CommonFunctionGrade comFuncGrade = new CommonFunctionGrade();
    ConnectToDB connectToDB = new ConnectToDB();
    GradeDatabase gradeDB = new GradeDatabase();

//    @Override
//    public void init() throws ServletException {
//        ServletContext context = getServletContext();
//        // Initialize cache
//        Map<String, Object> cacheGrade = new HashMap<>();
//        Map<String, Object> cacheClass = new HashMap<>();
//        Map<String, Object> cacheStudent = new HashMap<>();
//        Map<String, Object> cacheSubject = new HashMap<>();
//
//        Map<String, Object> checkExistCacheGrade = (Map<String, Object>) context.getAttribute("cache_Grade");
//        if (checkExistCacheGrade == null) {
//            cacheGrade.put("listGrade", connectDB.GradeListing()); // set data from Database 
//            context.setAttribute("cache_Grade", cacheGrade);
//        }
//
//        Map<String, Object> checkExistCacheClass = (Map<String, Object>) context.getAttribute("cache_Class");
//        if (checkExistCacheClass == null) {
//            cacheClass.put("listClass", connectDB.ClassListing()); // set data from Database 
//            context.setAttribute("cache_Class", cacheClass);
//        }
//
//        Map<String, Object> checkExistCacheStudent = (Map<String, Object>) context.getAttribute("cache_Students");
//        if (checkExistCacheStudent == null) {
//            cacheStudent.put("listStudent", connectDB.StudentListing()); // set data from Database 
//            context.setAttribute("cache_Students", cacheStudent);
//        }
//
//        Map<String, Object> checkExistCacheSubject = (Map<String, Object>) context.getAttribute("cache_Subject");
//        if (checkExistCacheSubject == null) {
//            cacheSubject.put("listSubject", connectDB.SubjectListing()); // set data from Database 
//            context.setAttribute("cache_Subject", cacheSubject);
//        }
//
//    }
    //search grade
    public List<Grade> findGrade(String studentName, String className, String subjectID, String semester, String schoolYear) {
//        ServletContext context = getServletContext();
//        //get list Grade
//        Map<String, Object> cacheGrade = (Map<String, Object>) context.getAttribute("cache_Grade");
//        // Get the cache
//        List<Grade> lstGrade = cacheGrade != null
//                ? (List<Grade>) cacheGrade.getOrDefault("listGrade", new ArrayList<>()) : connectDB.GradeListing();
//
//        //get list class
//        Map<String, Object> cacheClass = (Map<String, Object>) context.getAttribute("cache_Class");
//        List<StudentClass> lstClass = cacheClass != null
//                ? (List<StudentClass>) cacheClass.getOrDefault("listClass", new ArrayList<>()) : connectDB.ClassListing();
//
//        //get listStudent
//        Map<String, Object> cacheStudent = (Map<String, Object>) context.getAttribute("cache_Students");
//        // Get the cache
//        List<Student> lstStudent = cacheStudent != null
//                ? (List<Student>) cacheStudent.getOrDefault("listStudent", new ArrayList<>()) : connectDB.StudentListing();
        List<Grade> lstGrade = connectToDB.getListGradeFromDB();
        List<StudentClass> lstClass = connectToDB.getListClassFromDB();
        List<Student> lstStudent = connectToDB.getListStudentFromDB();

        if (!studentName.trim().equals("")) {
            lstGrade = lstGrade.stream().filter(item -> comFuncGrade.findStudentByStudentID(lstStudent, item.getStudentID()).getName().contains(studentName.trim()))
                    .collect(Collectors.toList());
        }
        if (!className.trim().equals("")) {
            lstGrade = lstGrade.stream().filter(item -> comFuncGrade.findClassName(lstClass, comFuncGrade.findStudentByStudentID(lstStudent, item.getStudentID()).getClassID()).contains(className.trim()))
                    .collect(Collectors.toList());
        }
        if (!subjectID.trim().equals("")) {
            lstGrade = lstGrade.stream().filter(item -> item.getSubjectID() == Integer.parseInt(subjectID))
                    .collect(Collectors.toList());
        }
        if (!semester.trim().equals("")) {
            lstGrade = lstGrade.stream().filter(item -> item.getSemesterID() == Integer.parseInt(semester.trim()))
                    .collect(Collectors.toList());
        }
        if (!schoolYear.equals("")) {
            lstGrade = lstGrade.stream().filter(item -> comFuncGrade.checkSchoolYear(lstClass, comFuncGrade.findStudentByStudentID(lstStudent, item.getStudentID()).getClassID(), schoolYear))
                    .collect(Collectors.toList());
        }
        return lstGrade;
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
            String className = request.getParameter("className");
            String subjectID = request.getParameter("subjectID");
            String semester = request.getParameter("semester");
            String schyear = request.getParameter("schoolYear");
//            if (studentName == null) {
//                studentName = "";
//            }
//            if (className == null) {
//                className = "";
//            }
//            if (subjectID == null) {
//                subjectID = "";
//            }
//            if (semester == null) {
//                semester = "";
//            }
//            if (schyear == null) {
//                schyear = "";
//            }
//            ServletContext context = getServletContext();
//        //get list class
//        Map<String, Object> cacheClass = (Map<String, Object>) context.getAttribute("cache_Class");
//        List<StudentClass> lstClass = cacheClass != null
//                ? (List<StudentClass>) cacheClass.getOrDefault("listClass", new ArrayList<>()) : connectDB.ClassListing();
//
//        //get listStudent
//        Map<String, Object> cacheStudent = (Map<String, Object>) context.getAttribute("cache_Students");
//        // Get the cache
//        List<Student> lstStudent = cacheStudent != null
//                ? (List<Student>) cacheStudent.getOrDefault("listStudent", new ArrayList<>()) : connectDB.StudentListing();
//
//        //get listSubject
//        Map<String, Object> cacheSubject = (Map<String, Object>) context.getAttribute("cache_Subject");
//        // Get the cache
//        List<Subject> lstSubject = cacheSubject != null
//                ? (List<Subject>) cacheSubject.getOrDefault("listSubject", new ArrayList<>()) : connectDB.SubjectListing();
            //find grade
            List<Grade> grades = findGrade(studentName, className, subjectID, semester, schyear);
            if (!grades.isEmpty()) {
                List<StudentClass> lstClass = connectToDB.getListClassFromDB();
                List<Student> lstStudent = connectToDB.getListStudentFromDB();
                List<Subject> lstSubject = connectToDB.getListSubjectFromDB();
                List<Semester> lstSemester = connectToDB.getListSemesterFromDB();
                List<Grade> validGrades = grades.stream()
                        .filter(grade -> comFuncGrade.findStudentByStudentID(lstStudent, grade.getStudentID()) != null)
                        .collect(Collectors.toList());
                List<GradeView> gradeView = validGrades.stream()
                        .map(item -> new GradeView(
                        item.getMarkID(),
                        comFuncGrade.getClassByStudentID(lstClass, lstStudent, item.getStudentID()).getClassName(),
                        comFuncGrade.findStudentByStudentID(lstStudent, item.getStudentID()).getName(),
                        item.getSubjectID(),
                        comFuncGrade.getSubjectBySubjectID(lstSubject, item.getSubjectID()).getSubjectName(),
                        item.getMarkOfSubject(),
                        item.getSemesterID(),
                        comFuncGrade.getSemesterBySemesterID(lstSemester, item.getSemesterID()).getSemesterName(),
                        comFuncGrade.getClassByStudentID(lstClass, lstStudent, item.getStudentID()).getSchoolyear()
                )).collect(Collectors.toList());
                json = gson.toJson(new GradeAPIResponse("Success", HttpServletResponse.SC_OK, gradeView));
            } else {
                json = gson.toJson(new GradeAPIResponse("Failure", HttpServletResponse.SC_BAD_REQUEST, null));
            }
            out.print(json);
            out.flush();
        }
    }

    //                                    grade.getMarkID(),
    //                                    gradeDB.findClassByStudentID(grade.getStudentID()).getClassName(),
    //                                    gradeDB.findStudentbyStudentID(grade.getStudentID()).getName(),
    //                                    grade.getSubjectID(),
    //                                    gradeDB.findSubjectBySubjectID(grade.getSubjectID()).getSubjectName(),
    //                                    grade.getMarkOfSubject(),
    //                                    grade.getSemesterID(),
    //                                    gradeDB.findSemesterBySemesterID(grade.getSemesterID()).getSemesterName(),
    //                                    gradeDB.findClassByStudentID(grade.getStudentID()).getSchoolyear()
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
        // processRequest(request, response);
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
