/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StudentServletAPI;

import Model.Student;
import Model.StudentClass;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Nguyen Huu Thanh
 */
public class CommonFunctionStudent {

    //create Unique ID
    public long createUniqueID() {
        long uniqueID = System.currentTimeMillis();
        return uniqueID;
    }

    //Check value School year
    public boolean checkSchoolYear(String schYear, String schYear1) {
        String[] year = schYear.split("-");
        String[] year1 = schYear1.split("-");
        return (Integer.parseInt(year[0]) == Integer.parseInt(year1[0]) && Integer.parseInt(year1[1]) == Integer.parseInt(year1[1]));
    }

    //create createDate and updateDate
    public String createDateTime() {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = dateTime.format(formatter);
        return formattedDateTime;
    }

    //Find Stduent by ID
    public Student findStudentByID(List<Student> lstStudent, int studentID) {
        Student result = lstStudent.stream()
                .filter(item -> item.getStudentID() == studentID)
                .findFirst()
                .orElse(null);
        return result;
    }

    //Find Class by ID
    public StudentClass findClassByID(List<StudentClass> lstclass, int classID) {
        StudentClass result = lstclass.stream()
                .filter(item -> item.getClassNameID()== classID)
                .findFirst()
                .orElse(null);
        return result;
    }
    
    // Update Student
    public boolean updateStudent(List<Student> lstStudent, Student updatedStudent) {
        Student existingStudent = findStudentByID(lstStudent, updatedStudent.getStudentID());
        if (existingStudent != null) {
            existingStudent.setName(updatedStudent.getName());
            existingStudent.setIdentification(updatedStudent.getIdentification());
            existingStudent.setGender(updatedStudent.getGender());
            existingStudent.setClassID(updatedStudent.getClassID());
            existingStudent.setDateOfBirth(updatedStudent.getDateOfBirth());
            existingStudent.setAddress(updatedStudent.getAddress());
            existingStudent.setEmail(updatedStudent.getEmail());
            existingStudent.setPhoneNumber(updatedStudent.getPhoneNumber());
            existingStudent.setCreateDate(existingStudent.getCreateDate());
            existingStudent.setUpdateDate(updatedStudent.getUpdateDate());
            return true;
        }
        return false;
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

    //Find classID from class name and school year
    public int findClassID(List<StudentClass> lstClass, String className, String schoolYear) {
        int classID = 0;
        StudentClass result = lstClass.stream()
                .filter(item -> item.getClassName().equalsIgnoreCase(className) && checkSchoolYear(item.getSchoolyear(), schoolYear))
                .findFirst()
                .orElse(null);
        if (result != null) {
            classID = result.getClassNameID();
        }
        return classID;
    }
}
