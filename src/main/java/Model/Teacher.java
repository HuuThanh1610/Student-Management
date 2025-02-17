/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Nguyen Huu Thanh
 */
public class Teacher {

    private int TeacherID;
    private String TeacherCode;
    private String TeacherName;
    private String Gender;
    private String PhoneNumber;
    private String Address;
    private String createDate;
    private String updateDate;
    private String status;

    public Teacher(int TeacherID, String TeacherCode, String TeacherName, String Gender, String PhoneNumber, String Address, String createDate, String updateDate, String status) {
        this.TeacherID = TeacherID;
        this.TeacherCode = TeacherCode;
        this.TeacherName = TeacherName;
        this.Gender = Gender;
        this.PhoneNumber = PhoneNumber;
        this.Address = Address;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.status = status;
    }

    public int getTeacherID() {
        return TeacherID;
    }

    public void setTeacherID(int TeacherID) {
        this.TeacherID = TeacherID;
    }

    public String getTeacherCode() {
        return TeacherCode;
    }

    public void setTeacherCode(String TeacherCode) {
        this.TeacherCode = TeacherCode;
    }

    public String getTeacherName() {
        return TeacherName;
    }

    public void setTeacherName(String TeacherName) {
        this.TeacherName = TeacherName;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String PhoneNumber) {
        this.PhoneNumber = PhoneNumber;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
