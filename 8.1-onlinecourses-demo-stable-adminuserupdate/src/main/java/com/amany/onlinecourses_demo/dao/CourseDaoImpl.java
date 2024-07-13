package com.amany.onlinecourses_demo.dao;

import com.amany.onlinecourses_demo.entity.Course;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public class CourseDaoImpl implements CourseDao{
    private EntityManager entityManager;
    @Autowired
    public CourseDaoImpl (EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public List<Course> findAllCourses() {
        TypedQuery<Course> theQuery = entityManager.createQuery("FROM Course", Course.class);
        return theQuery.getResultList();
    }

    @Override
    public Course findCourseById(int id) {
        TypedQuery<Course> theQuery = entityManager.createQuery("FROM Course WHERE id =:theData", Course.class);
        theQuery.setParameter ("theData", id);
        Course course = null;
        try {
            course = theQuery.getSingleResult();
        } catch (Exception e) {
            course = null;
        }
        return course;
    }

    @Override
    @Transactional
    public void deleteCourse(Course course) {
        entityManager.remove(course);
    }

    @Override
    public Course findCourseByTitle(String title) {
        TypedQuery<Course> theQuery = entityManager.createQuery("FROM Course WHERE title =:theData", Course.class);
        theQuery.setParameter ("theData", title);
        Course course = null;
        try {
            course = theQuery.getSingleResult();
        } catch (Exception e) {
            course = null;
        }
        return course;
    }

    @Override
    @Transactional
    public void saveCourse(Course course) {
        entityManager.merge(course);
    }
}
