package com.amany.onlinecourses_demo.controller;

import com.amany.onlinecourses_demo.dao.CourseDao;
import com.amany.onlinecourses_demo.dao.StudentDao;
import com.amany.onlinecourses_demo.entity.Course;
import com.amany.onlinecourses_demo.entity.Review;
import com.amany.onlinecourses_demo.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
public class DemoController {
    private StudentDao studentDao;
    private CourseDao courseDao;
    @Autowired
    public DemoController (CourseDao courseDao, StudentDao studentDao) {

        this.courseDao = courseDao;
        this.studentDao = studentDao;
    }
    @Value("${developer.name}")
    private String developer;
    @GetMapping("/")
    public String home (Model theModel) {
        theModel.addAttribute("theDate", java.time.LocalDateTime.now());
        theModel.addAttribute( "theDeveloper", developer);
        List<Course> courses = courseDao.findAllCourses();
        theModel.addAttribute("courseList", courses);
        return "home";
    }

    @GetMapping("/dashboard")
    public String showAdminDashBoard (@AuthenticationPrincipal UserDetails userDetails, Model theModel) {
        String user="default";
        if (userDetails != null) {
            user = userDetails.getUsername();
        }
        theModel.addAttribute("username", user);
        return "dashboard";
    }

    @GetMapping("/courses/{courseId}")
    public String viewCourse (@PathVariable int courseId, Model theModel) {
        Course course = courseDao.findCourseById(courseId);
        theModel.addAttribute("course", course);

        Review review = new Review();
        theModel.addAttribute("review", review);
        return "course-details";
    }

    @GetMapping("/enroll/{courseId}")
    public String enrollment (@AuthenticationPrincipal UserDetails userDetails,@PathVariable int courseId) {
        if (userDetails == null) {
            return "redirect:/students/registration";
        }
        String role = userDetails.getAuthorities().toString();
        if (role.equals("[ROLE_STUDENT]")) {
            Student tempStudent = studentDao.findStudentByUsername(userDetails.getUsername());
            Course tempCourse = courseDao.findCourseById(courseId);
            // if course already exist in students course list don't add it again
            List<Course> tempStudentCourses = tempStudent.getCourses();
            if (tempStudentCourses.contains(tempCourse)) {
                return "confirmation";
            }
            tempStudent.addCourse(tempCourse);
            studentDao.saveStudent(tempStudent);
            return "confirmation";
        } else {
            return "redirect:/students/registration";
        }
    }
}
