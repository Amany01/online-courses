package com.amany.onlinecourses_demo.service;

import com.amany.onlinecourses_demo.dao.InstructorDao;
import com.amany.onlinecourses_demo.entity.Instructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InstructorServiceImpl implements InstructorService {
    private InstructorDao instructorDao;
    @Autowired
    public InstructorServiceImpl(InstructorDao instructorDao) {
        this.instructorDao = instructorDao;
    }

    @Override
    @Transactional
    public void saveInstructor(Instructor theInstructor) {
        instructorDao.saveInstructor(theInstructor);
    }

    @Override
    public Instructor findInstructorByUsername(String userName) {
        return instructorDao.findInstructorByUsername(userName);
    }

    @Override
    @Transactional
    public void deleteInstructor(Instructor theInstructor) {
        instructorDao.deleteInstructor(theInstructor);
    }
}
