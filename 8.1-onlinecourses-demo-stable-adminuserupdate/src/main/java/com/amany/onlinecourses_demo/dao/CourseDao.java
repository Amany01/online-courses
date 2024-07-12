package com.amany.onlinecourses_demo.dao;

import com.amany.onlinecourses_demo.entity.Course;

import java.util.List;

public interface CourseDao {
    List<Course> findAllCourses ();
    Course findCourseById (int id);
    void deleteCourse(Course course);
    Course findCourseByTitle (String title);
}
