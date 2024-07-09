package com.amany.onlinecourses_demo.dao;

import com.amany.onlinecourses_demo.entity.Course;

import java.util.List;

public interface CourseDao {
    List<Course> findAllCourses ();
}
