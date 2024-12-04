package com.amany.onlinecourses_demo.service;

import com.amany.onlinecourses_demo.dao.StudentDao;
import com.amany.onlinecourses_demo.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentServiceImpl implements StudentService {
    private StudentDao studentDao;
    @Autowired
    public StudentServiceImpl(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Override
    @Transactional
    public void saveStudent(Student theStudent) {
        studentDao.saveStudent(theStudent);
    }

    @Override
    public Student findStudentByUsername(String userName) {
        return studentDao.findStudentByUsername(userName);
    }

    @Override
    @Transactional
    public void deleteStudent(Student theStudent) {
        studentDao.deleteStudent(theStudent);
    }
}
