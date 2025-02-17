/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GradeServletAPI;

import Model.Grade;
import Model.Semester;
import Model.Student;
import Model.StudentClass;
import Model.Subject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Nguyen Huu Thanh
 */
public class CommonFunctionGrade {

    //create createDate and updateDate
    public String createDateTime() {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = dateTime.format(formatter);
        return formattedDateTime;
    }

    public List<Integer> getStudentIDbyStudentName(List<Student> lstStudent, String studentName) {
        List<Student> result1 = new ArrayList<>();
        List<Integer> result2 = new ArrayList<>();
        result1 = lstStudent.stream()
                .filter(item -> item.getName().contains(studentName))
                .collect(Collectors.toList());
        result2 = result1.stream()
                .map(Student::getStudentID)
                .collect(Collectors.toList());
        return result2;
    }

    public List<StudentClass> getClassIDbyClassName(List<StudentClass> lstClass, String ClassName) {
        List<StudentClass> result1 = new ArrayList<>();
//        List<Integer> result2 = new ArrayList<>();
        result1 = lstClass.stream()
                .filter(item -> item.getClassName().contains(ClassName))
                .collect(Collectors.toList());
//        result2 = result1.stream()
//                .map(StudentClass::getClassNameID) 
//                .collect(Collectors.toList());
        return result1;
    }

    //Find class name
    public String findClassName(List<StudentClass> lstClass, int classID) {
        String className = null;
        StudentClass result = lstClass.stream()
                .filter(item -> item.getClassNameID() == classID)
                .findFirst()
                .orElse(null);
        if (result != null) {
            className = result.getClassName();
        }
        return className;
    }

    //find StudentName
    public Student findStudentByStudentID(List<Student> lstStudent, int studentID) {
        Student result = lstStudent.stream()
                .filter(item -> item.getStudentID() == studentID)
                .findFirst()
                .orElse(null);
        return result;
    }

    //Check school year
    public boolean checkSchoolYear(List<StudentClass> lstClass, int classID, String schYear1) {

        List<StudentClass> result = lstClass.stream()
                .filter(item -> item.getClassNameID() == classID && item.getSchoolyear().compareTo(schYear1) == 0)
                .collect(Collectors.toList());
        if (result.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public StudentClass getClassByStudentID(List<StudentClass> lstClass, List<Student> lstStudent, int studentID) {
        Student student = findStudentByStudentID(lstStudent, studentID);
        if (student == null) {
            return null;
        }
        int classID = student.getClassID();
        StudentClass result = lstClass.stream()
                .filter(item -> item.getClassNameID() == classID)
                .findFirst()
                .orElse(null);
        return result;
    }

    public Subject getSubjectBySubjectID(List<Subject> lstSubject, int subjectID) {
        Subject result = lstSubject.stream()
                .filter(item -> item.getSubjectID() == subjectID)
                .findFirst()
                .orElse(null);
        return result;
    }

    public Semester getSemesterBySemesterID(List<Semester> lstSemester, int semesterID) {
        Semester result = lstSemester.stream()
                .filter(item -> item.getSemesterID() == semesterID)
                .findFirst()
                .orElse(null);
        return result;
    }

    //create Unique ID
    public long createUniqueID() {
        long uniqueID = System.currentTimeMillis();
        return uniqueID;
    }

    public Grade findGradeByID(List<Grade> lstGrade, String markID) {
        Grade result = lstGrade.stream()
                .filter(item -> item.getMarkID() == Integer.parseInt(markID))
                .findFirst()
                .orElse(null);
        return result;
    }

}
