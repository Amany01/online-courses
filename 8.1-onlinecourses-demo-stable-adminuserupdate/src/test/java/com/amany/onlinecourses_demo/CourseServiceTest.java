package com.amany.onlinecourses_demo;

import com.amany.onlinecourses_demo.dao.CourseDao;
import com.amany.onlinecourses_demo.entity.Course;
import com.amany.onlinecourses_demo.entity.Instructor;
import com.amany.onlinecourses_demo.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CourseServiceTest {
    @Autowired
    private CourseService courseService;
    @MockBean
    private CourseDao courseDao;
    private Course sampleCourse;
    private Instructor sampleInstructor;
    private List<Course> sampleListCourses;

    @BeforeEach
    public void setUp() {
        sampleInstructor = new Instructor("userInstructor", "sample", "instructor", "sample@instructor.com","bio", sampleListCourses);
        sampleCourse = new Course("test course", sampleInstructor);
    }

    @Test
    public void testFindAllCoursesService () {

        List<Course> courseList = Arrays.asList(sampleCourse);
        when(courseDao.findAllCourses()).thenReturn(courseList);

        List<Course> courses = courseService.findAllCourses();

        assertNotNull(courses);
        assertEquals(1, courses.size());
        assertEquals("test course", courses.get(0).getTitle());
    }

    @Test
    public void testFindCourseById () {
        when(courseDao.findCourseById(0)).thenReturn(sampleCourse);

        Course course = courseService.findCourseById(0);

        assertNotNull(course);
        assertEquals(0, course.getId());
        assertEquals("test course", course.getTitle());
    }

    @Test
    public void testFindCourseByTitle () {
        when(courseDao.findCourseByTitle("test course")).thenReturn(sampleCourse);

        Course course = courseService.findCourseByTitle("test course");

        assertNotNull(course);
        assertEquals("test course", course.getTitle());
    }

    @Test
    public void testSaveCourse () {
        // Here, we don't expect a return value from saveCourse
        doNothing().when(courseDao).saveCourse(sampleCourse);

        courseService.saveCourse(sampleCourse);

        // Verify that the save method was called once
        verify(courseDao, times(1)).saveCourse(sampleCourse);
    }

    @Test
    public void testDeleteCourse () {
        doNothing().when(courseDao).deleteCourse(sampleCourse);

        courseService.deleteCourse(sampleCourse);

        // Verify that the delete method was called once
        verify(courseDao, times(1)).deleteCourse(sampleCourse);
    }

    @Test
    public void testFindCourseByIdNotFound() {
        when(courseDao.findCourseById(999)).thenReturn(null);

        Course course = courseService.findCourseById(999);

        assertNull(course);
    }

    @Test
    public void testFindCourseByTitleNotFound () {
        when(courseDao.findCourseByTitle("Non-existing course")).thenReturn(null);

        Course course = courseService.findCourseByTitle("Non-existing course");

        assertNull(course);
    }



}
