/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Nguyen Huu Thanh
 */
public class Grade {

    private int markID;
    private int studentID;
    private int subjectID;
    private int semesterID;
    private double markOfSubject;
    private String createDate;
    private String updateDate;

    public Grade(int markID, int studentID, int subjectID, int semesterID, double markOfSubject, String createDate, String updateDate) {
        this.markID = markID;
        this.studentID = studentID;
        this.subjectID = subjectID;
        this.semesterID = semesterID;
        this.markOfSubject = markOfSubject;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

   

    public int getMarkID() {
        return markID;
    }

    public void setMarkID(int markID) {
        this.markID = markID;
    }

    public int getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(int subjectID) {
        this.subjectID = subjectID;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public int getSemesterID() {
        return semesterID;
    }

    public void setSemesterID(int semesterID) {
        this.semesterID = semesterID;
    }

    public double getMarkOfSubject() {
        return markOfSubject;
    }

    public void setMarkOfSubject(double markOfSubject) {
        this.markOfSubject = markOfSubject;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

}
