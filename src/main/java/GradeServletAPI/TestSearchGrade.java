/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package GradeServletAPI;

import Database.ConnectToDB;
import Database.GradeDatabase;
import Model.Grade;
import Model.GradeAPIResponse;
import Model.Semester;
import Model.Student;
import Model.StudentClass;
import Model.Subject;
import ViewModel.GradeView;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Nguyen Huu Thanh
 */
@WebServlet(name = "TestSearchGrade", urlPatterns = {"/TestSearchGrade"})
public class TestSearchGrade extends HttpServlet {

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
    public List<Grade> findGrade(String studentName, String className, String subjectID, String semester, String schoolYear) {
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
            //find grade
            List<Grade> grades = findGrade(studentName, className, subjectID, semester, schyear);
            List<Grade> lstGrade = connectToDB.getListGradeFromDB();
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
               // json = gson.toJson(grades);
            } else {
                json = gson.toJson(new GradeAPIResponse("Failure", HttpServletResponse.SC_BAD_REQUEST, null));
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
