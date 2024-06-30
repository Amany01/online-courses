package com.amany.onlinecourses_demo.dao;

import com.amany.onlinecourses_demo.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class StudentDaoImpl implements StudentDao{
    private EntityManager entityManager;

    @Autowired
    public StudentDaoImpl (EntityManager theEntityManager) {
        this.entityManager = theEntityManager;
    }
    @Override
    @Transactional
    public void saveStudent(Student theStudent) {
        entityManager.merge(theStudent);
    }

    @Override
    public Student findStudentByUsername(String userName) {
        TypedQuery<Student> theQuery = entityManager.createQuery("From Student WHERE userName=:theData", Student.class);
        theQuery.setParameter("theData", userName);
        Student theStudent = null;
        try {
            theStudent = theQuery.getSingleResult();
        } catch (Exception e) {
            theStudent = null;
        }
        return theStudent;
    }
}
