/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClassServletAPI;

import Database.ConnectToDB;
import Model.StudentClass;
import Model.Teacher;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author Nguyen Huu Thanh
 */
public class CommonFunctionClass {
    ConnectToDB connectToDB = new ConnectToDB();
    //create createDate and updateDate
    public String createDateTime(){
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = dateTime.format(formatter);
        return formattedDateTime;
    }

    public int getTeacherIdByName(String TeacherName, List<Teacher> lstTeacher) {
        int teacherID = 0;
        Teacher result = lstTeacher.stream()
                .filter(item -> item.getTeacherName().equalsIgnoreCase(TeacherName))
                .findFirst()
                .orElse(null);
        if (result != null) {
            teacherID = result.getTeacherID();
        }
        return teacherID;
    }
    
        //Check value School year
    public boolean checkSchoolYearByName(String schYear, String schYear1) {
        String[] year = schYear.split("-");
        String[] year1 = schYear1.split("-");
        return (Integer.parseInt(year[0]) == Integer.parseInt(year1[0]) && Integer.parseInt(year1[1]) == Integer.parseInt(year1[1]));
    }
    
    public StudentClass findClassByID(List<StudentClass> lstClass, int classID) {
        StudentClass result = lstClass.stream()
                .filter(item -> item.getClassNameID() == classID)
                .findFirst()
                .orElse(null);
        return result;
    }

    // Update Student
    public boolean updateClass(List<StudentClass> lstClass, StudentClass updatedClass) {
        StudentClass existingClass = findClassByID(lstClass, updatedClass.getClassNameID());
        if (existingClass != null) {
            existingClass.setClassName(updatedClass.getClassName());
            existingClass.setClassNameID(updatedClass.getClassNameID());
            existingClass.setTeacherID(updatedClass.getTeacherID());
            existingClass.setSchoolyear(updatedClass.getSchoolyear());
            return true;
        }
        return false;
    }
    
    //create Unique ID
    public long createUniqueID() {
        long uniqueID = System.currentTimeMillis();
        return uniqueID;
    }
    
    //find teacher by ID
    public Teacher getTeacherByID(List<Teacher> lstTeacher, int teacherID){
        Teacher result = lstTeacher.stream()
                .filter(item -> item.getTeacherID()== teacherID)
                .findFirst()
                .orElse(null);
        return result;
    }
    
    //find teacher name by teacherID
     public String getTeacherNameIdByteacherID(List<Teacher> lstTeacher, int teacherID) {
        String name = null;
        Teacher result = lstTeacher.stream()
                .filter(item -> item.getTeacherID( )== teacherID)
                .findFirst()
                .orElse(null);
        if (result != null) {
            name = result.getTeacherName();
        }
        return name;
    }

}
