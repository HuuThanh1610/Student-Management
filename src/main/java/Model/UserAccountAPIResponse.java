/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Nguyen Huu Thanh
 */
public class UserAccountAPIResponse {
    private String message;
    private int status;
    private UserAccount data;

    public UserAccountAPIResponse(String message, int status, UserAccount data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }

    public UserAccountAPIResponse() {
    }
    
    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * @return the data
     */
    public UserAccount getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(UserAccount data) {
        this.data = data;
    }
    
    
}
