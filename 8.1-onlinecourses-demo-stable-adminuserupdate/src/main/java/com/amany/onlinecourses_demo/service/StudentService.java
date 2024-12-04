package com.amany.onlinecourses_demo.service;

import com.amany.onlinecourses_demo.entity.Student;

public interface StudentService {
    void saveStudent (Student theStudent);
    Student findStudentByUsername (String userName);
    void deleteStudent (Student theStudent);
}
