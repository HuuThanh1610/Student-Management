/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Nguyen Huu Thanh
 */
public class DropDownListAPIResponse {
    private String message;
    private int status;
    private Map<String, List<?>> data;

    public DropDownListAPIResponse(String message, int status, Map<String, List<?>> data) {
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

    public Map<String, List<?>> getData() {
        return data;
    }

    public void setData(Map<String, List<?>> data) {
        this.data = data;
    }
    
}
