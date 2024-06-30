package com.amany.onlinecourses_demo.dao;

import com.amany.onlinecourses_demo.entity.Student;

public interface StudentDao {
    void saveStudent (Student theStudent);
    Student findStudentByUsername (String userName);
}
