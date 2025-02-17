/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FileIO;

import Model.Student;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Nguyen Huu Thanh
 */
public class ReadFile {

    private final String fileName = "D:\\Student_Managemet_Website\\StudentManagement\\src\\main\\java\\FileIO\\Student.dat";

    public List<Student> readToFileDat() throws Exception {
        List<Student> tempList = new ArrayList<>();
        File file = new File(fileName);
        try {
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream objIn = new ObjectInputStream(fileIn);
            while (true) {
                try {
                    Student stu = (Student) objIn.readObject();
                    tempList.add(stu);
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return tempList;
    }

}
