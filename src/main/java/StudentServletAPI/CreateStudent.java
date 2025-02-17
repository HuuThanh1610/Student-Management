/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package StudentServletAPI;

import Database.ConnectToDB;
import Database.StudentDatabase;
import Model.StudentAPIResponse;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import Model.Student;
import java.util.List;
import java.util.stream.Collectors;
import Model.StudentClass;
import ViewModel.StudentView;
import com.google.gson.Gson;

/**
 *
 * @author Nguyen Huu Thanh
 */
@WebServlet(name = "CreateStudent", urlPatterns = {"/CreateStudent"})
public class CreateStudent extends HttpServlet {

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
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        try (PrintWriter out = response.getWriter()) {
            String studentName = request.getParameter("studentName").trim();
            String gender = request.getParameter("genderStudent").trim();
            String className = request.getParameter("className").trim();
            String address = request.getParameter("address").trim();
            String identificationStudent = request.getParameter("identificationStudent").trim();
            String dateOfBirthStudent = request.getParameter("DateOfBirthStudent").trim();
            String phoneNumber = request.getParameter("phoneNumber").trim();
            String email = request.getParameter("email").trim();
            String schoolYear = request.getParameter("schoolYear").trim();
            String status = "Active";
            if (studentName.isEmpty() || gender.isEmpty() || className.isEmpty() || identificationStudent.isEmpty()
                    || address.isEmpty() || dateOfBirthStudent.isEmpty() || phoneNumber.isEmpty() || email.isEmpty() || schoolYear.isEmpty()) {
                out.print(new Gson().toJson(new StudentAPIResponse("Invalid input data", HttpServletResponse.SC_BAD_REQUEST, null)));
                return;
            }
            //Cache List Student
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
            //Get list Student from DB
            //Get list Class from DB
            List<StudentClass> lstClass = conToDB.getListClassFromDB();
            int classID = ComFuncStudent.findClassID(lstClass, className, schoolYear);
            if (classID == 0) {
                out.print(new Gson().toJson(new StudentAPIResponse("Class of new stduent does not exist.", HttpServletResponse.SC_BAD_REQUEST, null)));
                out.flush();
                return;
            }
            //new student
            Student newStudent = new Student(1, studentName, gender, classID, address,
                    identificationStudent, dateOfBirthStudent, phoneNumber, email, null, null, status);
            Gson gson = new Gson();
            String json;

            if (studentDB.checkExistStudent(identificationStudent)) {
                out.print(new Gson().toJson(new StudentAPIResponse("Duplicate student identification", HttpServletResponse.SC_CONFLICT, null)));
                out.flush();
                return;
            }
            boolean commit = studentDB.createStudentDatabase(newStudent);
            if (commit) {
                // List<Student> newLstStudent = conToDB.getListStudentFromDB();
                List<StudentView> studentView = conToDB.getListStudentFromDB().stream()
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
                json = gson.toJson(new StudentAPIResponse("Student created successfully.", HttpServletResponse.SC_OK, studentView));
            } else {
                // json = gson.toJson(new StudentAPIResponse("Create student failure.", HttpServletResponse.SC_BAD_REQUEST, null));
                json = gson.toJson(newStudent);
            }
            out.print(json);
            out.flush();
//                // clear data cache
//                cache.clear();
//                // Set new data to cache
//                cache.put("listStudent", lstStudent);
//                context.setAttribute("cache_Students", cache);    
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
        //processRequest(request, response);
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
