/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Nguyen Huu Thanh
 */
public class StudentClass {
    private int classNameID;
    private String className;
    private String schoolyear;
    private int teacherID;

    public StudentClass(int classNameID, String className, String schoolyear, int teacherID) {
        this.classNameID = classNameID;
        this.className = className;
        this.schoolyear = schoolyear;
        this.teacherID = teacherID;
    }

    public int getClassNameID() {
        return classNameID;
    }

    public void setClassNameID(int classNameID) {
        this.classNameID = classNameID;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSchoolyear() {
        return schoolyear;
    }

    public void setSchoolyear(String schoolyear) {
        this.schoolyear = schoolyear;
    }

    public int getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }
    

}
