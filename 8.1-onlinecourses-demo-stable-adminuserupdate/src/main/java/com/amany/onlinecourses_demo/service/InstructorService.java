package com.amany.onlinecourses_demo.service;

import com.amany.onlinecourses_demo.entity.Instructor;

public interface InstructorService {
    void saveInstructor (Instructor theInstructor);
    Instructor findInstructorByUsername (String userName);
    void deleteInstructor (Instructor theInstructor);
}
