package com.amany.onlinecourses_demo.service;

import com.amany.onlinecourses_demo.dao.CourseDao;
import com.amany.onlinecourses_demo.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class CourseServiceImpl implements CourseService{

    private CourseDao courseDao;
    @Autowired
    public CourseServiceImpl(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    @Override
    public List<Course> findAllCourses() {
        return courseDao.findAllCourses();
    }

    @Override
    public Course findCourseById(int id) {
        return courseDao.findCourseById(id);
    }

    @Override
    @Transactional
    public void deleteCourse(Course course) {
        courseDao.deleteCourse(course);
    }

    @Override
    public Course findCourseByTitle(String title) {
        return courseDao.findCourseByTitle(title);
    }

    @Override
    @Transactional
    public void saveCourse(Course course) {
        courseDao.saveCourse(course);
    }
}
