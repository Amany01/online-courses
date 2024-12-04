package com.amany.onlinecourses_demo.service;

import com.amany.onlinecourses_demo.entity.Course;

import java.util.List;

public interface CourseService {
    List<Course> findAllCourses ();
    Course findCourseById (int id);
    void deleteCourse(Course course);
    Course findCourseByTitle (String title);
    void saveCourse (Course course);
}
