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
public class SubjectAPIResponse {
    private String message;
    private int status;
    private List<Subject> data;

    public SubjectAPIResponse(String message, int status, List<Subject> data) {
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

    public List<Subject> getData() {
        return data;
    }

    public void setData(List<Subject> data) {
        this.data = data;
    }
    
    
}
