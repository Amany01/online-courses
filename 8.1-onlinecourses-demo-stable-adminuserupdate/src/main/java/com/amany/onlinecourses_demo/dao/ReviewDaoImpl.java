package com.amany.onlinecourses_demo.dao;

import com.amany.onlinecourses_demo.entity.Review;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ReviewDaoImpl implements ReviewDao{
    private EntityManager entityManager;
    @Autowired
    public ReviewDaoImpl (EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public void deleteReviewById(int id) {
        TypedQuery<Review> theQuery = entityManager.createQuery("FROM Review WHERE id =: theData", Review.class);
        theQuery.setParameter("theData", id);
        Review tempReview = theQuery.getSingleResult();
        entityManager.remove(tempReview);
    }
}
