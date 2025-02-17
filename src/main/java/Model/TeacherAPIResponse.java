/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.List;

/**
 *
 * @author Nguyen Huu Thanh
 */
public class TeacherAPIResponse {
    private String message;
    private int status;
    private List<Teacher> data;

    public TeacherAPIResponse(String message, int status, List<Teacher> data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Teacher> getData() {
        return data;
    }

    public void setData(List<Teacher> data) {
        this.data = data;
    }

    
    
}
