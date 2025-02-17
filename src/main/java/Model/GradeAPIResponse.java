/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import ViewModel.GradeView;
import java.util.List;

/**
 *
 * @author Nguyen Huu Thanh
 */
public class GradeAPIResponse {
    private String message;
    private int status;
    private List<GradeView> data;

    public GradeAPIResponse(String message, int status, List<GradeView> data) {
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

    public List<GradeView> getData() {
        return data;
    }

    public void setData(List<GradeView> data) {
        this.data = data;
    }

    
}
