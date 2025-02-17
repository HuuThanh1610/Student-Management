/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ViewModel;

/**
 *
 * @author Nguyen Huu Thanh
 */
public class StudentClassView {
    private int classNameID;
    private String className;
    private String year;
    private int teacherID;
    private String teacherName;

    public StudentClassView(int classNameID, String className, String year, int teacherID, String teacherName) {
        this.classNameID = classNameID;
        this.className = className;
        this.year = year;
        this.teacherID = teacherID;
        this.teacherName = teacherName;
    }

    public int getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(int teacherID) {
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

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
