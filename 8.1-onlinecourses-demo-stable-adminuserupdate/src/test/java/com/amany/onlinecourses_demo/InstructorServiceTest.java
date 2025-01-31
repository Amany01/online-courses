package com.amany.onlinecourses_demo;

import com.amany.onlinecourses_demo.dao.InstructorDao;
import com.amany.onlinecourses_demo.entity.Course;
import com.amany.onlinecourses_demo.entity.Instructor;
import com.amany.onlinecourses_demo.service.InstructorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class InstructorServiceTest {
    @MockBean
    private InstructorDao instructorDao;
    @Autowired
    private InstructorService instructorService;
    private Instructor sampleInstructor;
    private List<Course> sampleListCourses;

    @BeforeEach
    public void setUp () {
        sampleListCourses = null;
        sampleInstructor = new Instructor("userInstructor", "sample", "instructor", "sample@instructor.com","bio", sampleListCourses);
    }



    @Test
    public void testSaveInstructor () {
        doNothing().when(instructorDao).saveInstructor(sampleInstructor);

        instructorService.saveInstructor(sampleInstructor);

        verify(instructorDao, times(1)).saveInstructor(sampleInstructor);
    }

    @Test
    public void testFindInstructorByUsername () {
        when(instructorDao.findInstructorByUsername("userInstructor")).thenReturn(sampleInstructor);

        Instructor instructor = instructorService.findInstructorByUsername("userInstructor");

        assertNotNull(instructor);
        assertEquals("sample", instructor.getFirstName());
    }

    @Test
    public void testDeleteInstructor () {
        doNothing().when(instructorDao).deleteInstructor(sampleInstructor);

        instructorService.deleteInstructor(sampleInstructor);

        verify(instructorDao, times(1)).deleteInstructor(sampleInstructor);
    }

}
