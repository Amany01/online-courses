package com.amany.onlinecourses_demo;

import com.amany.onlinecourses_demo.dao.StudentDao;
import com.amany.onlinecourses_demo.entity.Student;
import com.amany.onlinecourses_demo.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class StudentServiceTest {
    @Autowired
    private StudentService studentService;
    @MockBean
    private StudentDao studentDao;
    private Student sampleStudent;

    @BeforeEach
    public void setUp () {
        sampleStudent = new Student("Doug1", "Doug", "wolf", "doug@wolf.com");
    }

    @Test
    public void testSaveStudent () {
        doNothing().when(studentDao).saveStudent(sampleStudent);

        studentService.saveStudent(sampleStudent);

        verify(studentDao, times(1)).saveStudent(sampleStudent);
    }

    @Test
    public void teatFindStudentByUsername () {
        when(studentDao.findStudentByUsername("Doug1")).thenReturn(sampleStudent);

        Student student = studentService.findStudentByUsername("Doug1");

        assertNotNull(student);
        assertEquals("Doug1", student.getUserName());
    }

    @Test
    public void testDeleteStudent () {
        doNothing().when(studentDao).deleteStudent(sampleStudent);

        studentService.deleteStudent(sampleStudent);

        verify(studentDao, times(1)).deleteStudent(sampleStudent);
    }



}
