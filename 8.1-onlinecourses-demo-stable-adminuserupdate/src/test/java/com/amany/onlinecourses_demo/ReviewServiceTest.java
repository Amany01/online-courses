package com.amany.onlinecourses_demo;

import com.amany.onlinecourses_demo.dao.ReviewDao;
import com.amany.onlinecourses_demo.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.*;

@SpringBootTest
public class ReviewServiceTest {
    @Autowired
    private ReviewService reviewService;
    @MockBean
    private ReviewDao reviewDao;

    @Test
    public void testDeleteReviewById () {
        doNothing().when(reviewDao).deleteReviewById(1);

        reviewService.deleteReviewById(1);

        verify(reviewDao, times(1)).deleteReviewById(1);
    }
}
