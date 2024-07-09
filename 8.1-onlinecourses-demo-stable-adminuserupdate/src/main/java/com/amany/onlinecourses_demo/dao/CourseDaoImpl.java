package com.amany.onlinecourses_demo.dao;

import com.amany.onlinecourses_demo.entity.Course;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
}
