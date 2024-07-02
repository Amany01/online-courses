package com.amany.onlinecourses_demo.dao;

import com.amany.onlinecourses_demo.entity.Instructor;

public interface InstructorDao {
    void saveInstructor (Instructor theInstructor);
    Instructor findInstructorByUsername (String userName);
    void deleteInstructor (Instructor theInstructor);
}
