/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.ArrayList;
import java.util.List;
import FileIO.WriteToFile;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

/**
 *
 * @author Nguyen Huu Thanh
 */
public class ConnectToTrialDB {

    public List<Student> StudentListing() {
        List<Student> listStudent = new ArrayList<>();
        listStudent.add(new Student(1, "Nguyễn Hữu Thành", "Male", 1, "Bình Định", "052204012792", "2004-12-27", "0346479987", "nghuuthanh004@gmail.com", "2024-11_21 13:00:00", null, "Active"));
        listStudent.add(new Student(2, "Nguyễn Hữu Thành1", "Male", 2, "Bình Định", "052206012792", "2004-12-27", "0346479987", "nghuuthanh004@gmail.com", "2024-11_21 13:00:00", null, "Active"));
        listStudent.add(new Student(3, "Nguyễn Hữu Thành2", "Male", 3, "Bình Định", "052205012792", "2004-12-27", "0346479987", "nghuuthanh004@gmail.com", "2024-11_21 13:00:00", null, "Active"));
        listStudent.add(new Student(4, "Nguyễn Hữu Thành3", "Male", 4, "Bình Định", "052203012792", "2004-12-27", "0346479987", "nghuuthanh004@gmail.com", "2024-11_21 13:00:00", null, "Active"));
        listStudent.add(new Student(5, "Nguyễn Hữu Thành4", "Male", 4, "Bình Định", "052207012792", "2004-12-27", "0346479987", "nghuuthanh004@gmail.com", "2024-11_21 13:00:00", null, "Active"));
        listStudent.add(new Student(6, "Nguyễn Hữu Thành5", "Male", 5, "Bình Định", "052208012792", "2004-12-27", "0346479987", "nghuuthanh004@gmail.com", "2024-11_21 13:00:00", null, "Active"));
        listStudent.add(new Student(7, "Nguyễn Hữu Thành6", "Male", 3, "Bình Định", "052209012792", "2004-12-27", "0346479987", "nghuuthanh004@gmail.com", "2024-11_21 13:00:00", null, "Active"));
        listStudent.add(new Student(8, "Nguyễn Hữu Thành7", "Male", 2, "Bình Định", "052201012792", "2004-12-27", "0346479987", "nghuuthanh004@gmail.com", "2024-11_21 13:00:00", null, "Active"));
        listStudent.add(new Student(9, "Nguyễn Hữu Thành8", "Male", 1, "Bình Định", "052202012792", "2004-12-27", "0346479987", "nghuuthanh004@gmail.com", "2024-11_21 13:00:00", null, "Active"));
        return listStudent;
    }

//    public List<StudentClass> ClassListing() {
//        List<StudentClass> listClass = new ArrayList<>();
//        listClass.add(new StudentClass(1, "10A1", 1, "2023-2024"));
//        listClass.add(new StudentClass(2, "10A2", 2, "2023-2024"));
//        listClass.add(new StudentClass(3, "10A3", 3, "2023-2024"));
//        listClass.add(new StudentClass(4, "10A4", 4, "2023-2024"));
//        listClass.add(new StudentClass(5, "10A5", 5, "2023-2024"));
//        listClass.add(new StudentClass(6, "10A1", 2, "2024-2025"));
//        listClass.add(new StudentClass(7, "10A2", 5, "2024-2025"));
//        listClass.add(new StudentClass(8, "10A3", 3, "2024-2025"));
//        return listClass;
//    }

//    public List<Teacher> TeacherListing() {
//        List<Teacher> listTeacher = new ArrayList<>();
//        listTeacher.add(new Teacher(1, "TE01", "Nguyễn Văn Dũng", "Male", "0745326423", "Bình Định", "2024-08-11 10:04:00.000", null));
//        listTeacher.add(new Teacher(2, "TE02", "Nguyễn Văn Ka", "Male", "0745326423", "Bình Định", "2024-08-11 10:04:00.000", null));
//        listTeacher.add(new Teacher(3, "TE03", "Nguyễn Thị Ngọc", "Female", "0745326423", "Bình Định", "2024-08-11 10:04:00.000", null));
//        listTeacher.add(new Teacher(4, "TE04", "Nguyễn Tiến Đạt", "Male", "0745326423", "Bình Định", "2024-08-11 10:04:00.000", null));
//        listTeacher.add(new Teacher(5, "TE05", "Nguyễn Ngọc Huyền", "Female", "0745326423", "Bình Định", "2024-08-11 10:04:00.000", null));
//        return listTeacher;
//    }

//    public List<Grade> GradeListing() {
//        List<Grade> listGrade = new ArrayList<>();
//        listGrade.add(new Grade(1, 1, 1, 9.75, "HK1", "2024-08-11 12:31:00.000", null));
//        listGrade.add(new Grade(2, 1, 2, 9.75, "HK1", "2024-08-11 12:31:00.000", null));
//        listGrade.add(new Grade(3, 1, 3, 9.75, "HK1", "2024-08-11 12:31:00.000", null));
//        listGrade.add(new Grade(4, 1, 4, 9.75, "HK1", "2024-08-11 12:31:00.000", null));
//        listGrade.add(new Grade(5, 1, 5, 9.75, "HK1", "2024-08-11 12:31:00.000", null));
//        listGrade.add(new Grade(6, 1, 6, 9.75, "HK1", "2024-08-11 12:31:00.000", null));
//        listGrade.add(new Grade(7, 1, 7, 9.75, "HK1", "2024-08-11 12:31:00.000", null));
//        listGrade.add(new Grade(8, 1, 8, 9.75, "HK1", "2024-08-11 12:31:00.000", null));
//        listGrade.add(new Grade(9, 1, 9, 9.75, "HK1", "2024-08-11 12:31:00.000", null));
//        return listGrade;
//    }

    public List<Subject> SubjectListing() {
        List<Subject> listSubject = new ArrayList<>();
        listSubject.add(new Subject(1, "Math"));
        listSubject.add(new Subject(2, "Biology"));
        listSubject.add(new Subject(3, "Chemistry"));
        listSubject.add(new Subject(4, "Computer science"));
        listSubject.add(new Subject(5, "Physics"));
        listSubject.add(new Subject(6, "Literature"));
        listSubject.add(new Subject(7, "History"));
        listSubject.add(new Subject(8, "Civic Education"));
        listSubject.add(new Subject(9, "Geology"));
        listSubject.add(new Subject(10, "National Defense Education"));
        listSubject.add(new Subject(11, "Physical education"));
        listSubject.add(new Subject(12, "English"));
        return listSubject;
    }
    
    public List<Semester> SemesterListing() {
        List<Semester> listsemester = new ArrayList<>();
        listsemester.add(new Semester(1, "First semester"));
        listsemester.add(new Semester(2, "Second semester"));
        listsemester.add(new Semester(3, "summer holiday"));
        return listsemester;
    }
}
