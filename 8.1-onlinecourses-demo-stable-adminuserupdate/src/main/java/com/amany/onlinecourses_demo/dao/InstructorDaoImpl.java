package com.amany.onlinecourses_demo.dao;

import com.amany.onlinecourses_demo.entity.Course;
import com.amany.onlinecourses_demo.entity.Instructor;
import com.amany.onlinecourses_demo.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class InstructorDaoImpl implements InstructorDao{
    private EntityManager entityManager;

    @Autowired
    public InstructorDaoImpl (EntityManager theEntityManager) {
        this.entityManager = theEntityManager;
    }
    @Override
    public void saveInstructor(Instructor theInstructor) {
        entityManager.merge(theInstructor);
    }

    @Override
    public Instructor findInstructorByUsername(String userName) {
        TypedQuery<Instructor> theQuery = entityManager.createQuery("From Instructor WHERE userName=:theData", Instructor.class);
        theQuery.setParameter("theData", userName);
        Instructor theInstructor = null;
        try {
            theInstructor = theQuery.getSingleResult();
        } catch (Exception e) {
            theInstructor = null;
        }
        return theInstructor;
    }

    @Override
    public void deleteInstructor(Instructor theInstructor) {
        List<Course> courses = theInstructor.getCourses();
        // break association of all courses for the instructor
        for (Course tempCourse : courses) {
            tempCourse.setInstructor(null);
        }
        entityManager.remove(theInstructor);
    }
}
