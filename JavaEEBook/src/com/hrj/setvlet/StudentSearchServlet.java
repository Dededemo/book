package com.hrj.setvlet;
 
import com.hrj.bean.Student;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
 

public class StudentSearchServlet extends HttpServlet {
 
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
 

        List<Student> students = new ArrayList<Student>();
        for (int i = 0; i < 10; i++) {
            int t = i + 1;
            students.add(new Student(i,"小王","123456",i+10,"123456789"));
        }

        req.setAttribute("studentList",students);

        req.getRequestDispatcher("showStudent.jsp").forward(req,resp);

    }
}