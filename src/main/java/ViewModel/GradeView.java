/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ViewModel;

/**
 *
 * @author Nguyen Huu Thanh
 */
public class GradeView {
    private int markID;
    private String className;
    private String studentName;
    private int subjectID;
    private String subjectName;
    private double markOfSubject;
    private int semesterID;
    private String semester;
    private String year;

    public GradeView(int markID, String className, String studentName, int subjectID, String subjectName, double markOfSubject, int semesterID, String semester, String year) {
        this.markID = markID;
        this.className = className;
        this.studentName = studentName;
        this.subjectID = subjectID;
        this.subjectName = subjectName;
        this.markOfSubject = markOfSubject;
        this.semesterID = semesterID;
        this.semester = semester;
        this.year = year;
    }

    public int getMarkID() {
        return markID;
    }

    public void setMarkID(int markID) {
        this.markID = markID;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(int subjectID) {
        this.subjectID = subjectID;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public double getMarkOfSubject() {
        return markOfSubject;
    }

    public void setMarkOfSubject(double markOfSubject) {
        this.markOfSubject = markOfSubject;
    }

    public int getSemesterID() {
        return semesterID;
    }

    public void setSemesterID(int semesterID) {
        this.semesterID = semesterID;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    
}
