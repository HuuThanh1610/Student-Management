/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FileIO;

import Model.Student;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 *
 * @author Nguyen Huu Thanh
 */
public class WriteToFile {

    private final String fileName = "D:\\Student_Managemet_Website\\StudentManagement\\src\\main\\java\\FileIO\\Student.dat";

    public void writeFile(List<Student> tempList) throws IOException {
        File file = new File(fileName);
        FileOutputStream fileOut = null;
        ObjectOutputStream objOut = null;
        try {
            fileOut = new FileOutputStream(file, true);
            if (file.exists() && file.length() > 0) {
                objOut = new AppendingObjectOutputStream(fileOut);
            } else {
                objOut = new ObjectOutputStream(fileOut);
            }
            for (Student stu : tempList) {
                objOut.writeObject(stu);
            }
            objOut.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
