package com.amany.onlinecourses_demo.service;

import com.amany.onlinecourses_demo.dao.ReviewDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewServiceImpl implements  ReviewService {
    private ReviewDao reviewDao;
    @Autowired
    public ReviewServiceImpl(ReviewDao reviewDao) {
        this.reviewDao = reviewDao;
    }

    @Override
    @Transactional
    public void deleteReviewById(int id) {
        reviewDao.deleteReviewById(id);
    }
}
